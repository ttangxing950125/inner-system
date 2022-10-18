package com.deloitte.crm.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.cell.CellUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.bean.BeanUtils;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.constants.EntityUtils;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.domain.dto.*;
import com.deloitte.crm.dto.*;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.*;
import com.deloitte.crm.utils.TimeFormatUtil;
import com.deloitte.crm.vo.*;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@AllArgsConstructor
public class EntityInfoServiceImpl extends ServiceImpl<EntityInfoMapper, EntityInfo> implements IEntityInfoService {

    private IEntityNameHisService iEntityNameHisService;

    @Autowired
    private EntityStockCnRelMapper cnRelMapper;

    @Autowired
    private EntityStockThkRelMapper thkRelMapper;

    @Autowired
    private EntityFinancialMapper financialMapper;

    @Autowired
    private ICrmSupplyTaskService crmSupplyTaskService;

    @Autowired
    private StockCnInfoMapper stockCnMapper;

    @Autowired
    private StockThkInfoMapper stockThkMapper;

    private EntityInfoMapper entityInfoMapper;

    private EntityNameHisMapper nameHisMapper;

    private EntityAttrValueMapper entityAttrValueMapper;

    private EntityBondRelMapper entityBondRelMapper;

    private GovInfoMapper govInfoMapper;

    private BondInfoMapper bondInfoMapper;

    private EntityInfoManager entityInfoManager;

    private EntityNameHisMapper entityNameHisMapper;

    private ICrmEntityTaskService iCrmEntityTaskService;

    private RedisService redisService;

    private EntityInfoLogsUpdatedService entityInfoLogsUpdatedService;

    /**
     * 主体
     */
    public static final String ENTITY = "ENTITY";
    /**
     * 债券
     */
    public static final String BOND = "BOND";
    /**
     * 未输入页码
     */
    private static final String NO_PAGENUM_ERROR = "未输入页码";
    /**
     * 默认页面size
     */
    private static final Integer DEFAULT_PAGESIZE = 9;
    /**
     * 默认页码
     */
    private static final Integer DEFAULT_PAGENUM = 1;
    /**
     * 曾用名重复
     */
    private static final String REPET_OLD_NAME = "曾用名重复，请重新输入";
    /**
     * 记录新增来源  2-政府
     */
    private static final Integer ENTITY_INFO_TYPE = 2;
    /**
     * 记录新增来源  2-曾用名管理中操作
     */
    private static final Integer OLD_NAME_FROM_MAN = 2;

    private EntityInfoLogsService entityInfoLogsService;

    /**
     * 统计企业主体信息
     *
     * @return List<EntityInfoDto>
     * @author penTang
     * @date 2022/9/22 22:40
     */
    @Override
    public EntityInfoDto getEntityInfo() {

        EntityInfoDto entityInfoDto = new EntityInfoDto();
        List<EntityInfo> list = this.list();

        //issue_bonds 是否发债 0-未发债 1-已发债
        List<EntityInfo> bonds = list.stream()
                .filter(row -> row.getIssueBonds() != null && row.getIssueBonds() == 1)
                .collect(Collectors.toList());

        //finance 是否金融机构
        List<EntityInfo> finance = list.stream()
                .filter(row -> row.getFinance() != null && row.getFinance() == 1)
                .collect(Collectors.toList());

        //list 是否上市 0-未上市 1-已上市
        List<EntityInfo> entityInfoList = list.stream()
                .filter(row -> row.getList() != null && row.getList() == 1)
                .collect(Collectors.toList());

        //即是上市又是发债
        List<EntityInfo> listAndBonds = list.stream()
                .filter(row -> row.getList() != null && row.getList() == 1)
                .filter(row -> (row.getFinance() != null && row.getIssueBonds() != null) && row.getIssueBonds() == 1)
                .collect(Collectors.toList());

        //!即是上市又是发债
        List<EntityInfo> notListAndBonds = list.stream()
                .filter(row -> row.getList() != null && row.getList() == 0)
                .filter(row -> (row.getFinance() != null && row.getIssueBonds() != null) && row.getIssueBonds() == 0)
                .collect(Collectors.toList());
        entityInfoDto.setIssueBonds(bonds.size());
        entityInfoDto.setEntitySum(list.size());
        entityInfoDto.setNotBondsAndList(notListAndBonds.size());
        entityInfoDto.setList(entityInfoList.size());
        entityInfoDto.setBondsAndList(listAndBonds.size());
        entityInfoDto.setFinance(finance.size());

        return entityInfoDto;

    }

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityInfo selectEntityInfoById(Long id) {
        return entityInfoMapper.selectEntityInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param entityInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityInfo> selectEntityInfoList(EntityInfo entityInfo) {
        return entityInfoMapper.selectEntityInfoList(entityInfo);
    }

    /**
     * @param entityDto
     * @return
     * @author 正杰
     * @date 2022/9/22
     * 新增【确定该主体是新增后,填写具体要新增主体的信息】
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R insertEntityInfo(EntityDto entityDto) {
        //此处的entityDto的id为 task的id
        EntityInfo entityInfo = new EntityInfo();
        Integer taskId = entityDto.getId();
        entityDto.setId(null);
        BeanUtils.copyBeanProp(entityInfo, entityDto);
        // 插入后获取id;
        baseMapper.insert(entityInfo);
        Integer id = entityInfo.getId();

        // 生成entity_code 那么将该值的 用 IB+0..+id  例 IB000001
        String entityCode = "IB" + this.appendPrefix(6, id);
        entityInfo.setEntityCode(entityCode);

        // 判断社会信用代码是否适用 => 适用为 空 并为其赋值 5 否则有数字
        Integer creditErrorType = entityDto.getCreditErrorType();
        if (creditErrorType == null) {
            creditErrorType = 5;
            entityInfo.setCreditError(0);
            entityInfo.setCreditErrorType(creditErrorType);
        }
        switch (creditErrorType) {
            case 1:
                //注销企业:ZX+企业德勤代码+R+自0000001开始排序。
                entityInfo.setCreditCode(this.appendCreditCode("ZX", entityCode));
                break;
            case 2:
                //吊销企业:DX+企业德勤代码+R+自0000001开始排序。
                entityInfo.setCreditCode(this.appendCreditCode("DX", entityCode));
                break;
            case 3:
                //非注册机构:OC+企业德勤代码+R+自0000001开始排序。
                entityInfo.setCreditCode(this.appendCreditCode("OC", entityCode));
                break;
            case 4:
                //其他未知原因:CU+企业德勤代码+R+自0000001开始排序。
                entityInfo.setCreditCode(this.appendCreditCode("CU", entityCode));
                break;
            case 5:
                entityInfo.setCreditCode(entityDto.getCreditCode());
                break;
            default:
                return R.fail(BadInfo.COULD_NOT_FIND_SOURCE.getInfo());
        }

        //添加当前用户
        String username = SecurityUtils.getUsername();
        entityInfo.setUpdater(username);
        //默认生效 代码为 1
        entityInfo.setStatus(1);
        //再次修改当条信息
        baseMapper.updateById(entityInfo);

        //TODO 将新增的信息保存至 entity_info_logs
        EntityInfoLogs entityInfoLogs = new EntityInfoLogs();
        //数据装配新增基础信息
        entityInfoLogs.setEntityCode(entityCode)
                .setEntityName(entityInfo.getEntityName())
                .setOperName(username);
        entityInfoLogsService.getBaseMapper().insert(entityInfoLogs);

        //修改当日任务 新增主体状态码为 2
        return iCrmEntityTaskService.finishTask(taskId, 2);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityInfo(EntityInfo entityInfo) {
        return entityInfoMapper.updateEntityInfo(entityInfo);
    }


    /**
     * 修改【请填写功能名称】
     *
     * @param entityInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateOrInsertEntityInfoByEntityCode(EntityInfo entityInfo) {

        //设置主键为空，防止修改主键
        entityInfo.setId(null);
        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();
        int update = entityInfoMapper.update(entityInfo, queryWrapper.lambda().eq(EntityInfo::getEntityCode, entityInfo.getEntityCode()));
        if (update < 1) {
            return entityInfoMapper.insert(entityInfo);
        }
        return update;
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityInfoByIds(Long[] ids) {
        return entityInfoMapper.deleteEntityInfoByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityInfoById(Long id) {
        return entityInfoMapper.deleteEntityInfoById(id);
    }

    /**
     * => 修改主体信息中的主体名称 & 汇总曾用名
     * => 新增主体曾用名
     *
     * @param creditCode    统一社会信用代码
     * @param entityNewName 主体新名称
     * @param remarks       备注
     * @return 修改返回信息
     * @author 正杰
     * @date 2022/9/22
     */
    @Override
    public R editEntityNameHis(String creditCode, String entityNewName, String remarks) {
        EntityInfo entity = baseMapper.selectOne(new QueryWrapper<EntityInfo>()
                .lambda().eq(EntityInfo::getCreditCode, creditCode));
        return R.ok(entityInfoManager.updateEntityName(entity, entityNewName, remarks));
    }

    /**
     * 根据名称查询主体
     *
     * @param entityName
     * @return
     */
    @Override
    public List<EntityInfo> findByName(String entityName) {
        return baseMapper.findByName(entityName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R addOldName(EntityInfo entity) {
        EntityInfo entityInfo = entityInfoMapper.selectById(entity.getId());
        //校验曾用名是否存在
        String entityCode = entityInfo.getEntityCode();
        QueryWrapper<EntityNameHis> queryWrapper = new QueryWrapper<>();
        String nameHis = entity.getEntityNameHis();
        Long aLong = nameHisMapper.selectCount(queryWrapper.lambda()
                .eq(EntityNameHis::getDqCode, entityCode)
                .eq(EntityNameHis::getOldName, nameHis));
        if (aLong > 0) {
            return R.fail(REPET_OLD_NAME);
        }
        //获取操作用户
        String remoter = SecurityUtils.getUsername();
        //修改曾用名记录
        String entityNameHis = entityInfo.getEntityNameHis();
        if (ObjectUtils.isEmpty(entityNameHis)) {
            entityInfo.setEntityNameHis(entity.getEntityNameHis());
        } else {
            entityInfo.setEntityNameHis(entityNameHis + EntityUtils.NAME_USED_SIGN + entity.getEntityNameHis());
        }
        String nameHisRemarks = entity.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(nameHisRemarks)) {
            entityInfo.setEntityNameHisRemarks(entity.getUpdated() + remoter + entity.getEntityNameHisRemarks());
        } else {
            entityInfo.setEntityNameHisRemarks(entityNameHis + EntityUtils.NAME_USED_REMARK_SIGN + entity.getUpdated() + remoter + entity.getEntityNameHisRemarks());
        }
        entityInfoMapper.updateById(entityInfo);

        //插入曾用名记录表
        EntityNameHis newNameHis = new EntityNameHis();
        newNameHis.setDqCode(entityInfo.getEntityCode());
        newNameHis.setOldName(entity.getEntityNameHis());
        newNameHis.setEntityType(ENTITY_INFO_TYPE);
        newNameHis.setHappenDate(entity.getUpdated());
        newNameHis.setRemarks(entity.getEntityNameHisRemarks());
        newNameHis.setSource(OLD_NAME_FROM_MAN);
        nameHisMapper.insert(newNameHis);
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateOldName(String dqCode, String oldName, String newOldName, String status) {
        //根据dqCode查询主体表
        QueryWrapper<EntityInfo> infoQuery = new QueryWrapper<>();
        EntityInfo entityInfo = entityInfoMapper.selectOne(infoQuery.lambda().eq(EntityInfo::getEntityCode, dqCode));
        //根据dqCode查询曾用名列表
        QueryWrapper<EntityNameHis> hisQuery = new QueryWrapper<>();
        EntityNameHis nameHis = nameHisMapper.selectOne(hisQuery.lambda()
                .eq(EntityNameHis::getDqCode, dqCode)
                .eq(EntityNameHis::getOldName, oldName));

        if (ObjectUtils.isEmpty(status)) {
            //校验修改后的曾用名是否已经存在
            Long aLong = nameHisMapper.selectCount(hisQuery.lambda()
                    .eq(EntityNameHis::getDqCode, dqCode)
                    .eq(EntityNameHis::getOldName, newOldName));
            if (aLong > 0) {
                return R.ok(REPET_OLD_NAME);
            }
            //修改主体表中的数据
            entityInfo.setEntityNameHis(entityInfo.getEntityNameHis().replaceAll(oldName, newOldName));
            entityInfo.setEntityNameHisRemarks(entityInfo.getEntityNameHisRemarks().replaceAll(oldName, newOldName));
            entityInfoMapper.updateById(entityInfo);
            //修改曾用名表中的数据
            nameHis.setOldName(newOldName);
            nameHisMapper.updateById(nameHis);
            return R.ok();
        }

        //修改主体表中的数据
        entityInfo.setEntityNameHis(entityInfo.getEntityNameHis().replaceAll(oldName, ""));
        entityInfo.setEntityNameHisRemarks(entityInfo.getEntityNameHisRemarks().replaceAll(oldName, newOldName));
        String remarks = entityInfo.getEntityNameHisRemarks();
        String[] split = remarks.split(EntityUtils.NAME_USED_REMARK_SIGN);
        String newRemarks = "";
        //拆分曾用名
        for (String value : split) {
            if (value.contains(oldName)) {
                continue;
            }
            if (ObjectUtils.isEmpty(newRemarks)) {
                newRemarks = value;
            } else {
                newRemarks = newRemarks + EntityUtils.NAME_USED_REMARK_SIGN + value;
            }
        }
        entityInfo.setEntityNameHisRemarks(newRemarks);
        entityInfoMapper.updateById(entityInfo);

        //修改曾用名表中的数据
        nameHis.setStatus(EntityUtils.INVALID);
        nameHisMapper.updateById(nameHis);
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R getInfoDetailByEntityCode(String entityCode) {
        EntityInfoDetails entityInfoDetails = new EntityInfoDetails();

        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(queryWrapper.lambda().eq(EntityInfo::getEntityCode, entityCode));

        if (CollectionUtils.isEmpty(entityInfos)) {
            return R.fail("异常查询，数据为空");
        }
        if (entityInfos.size() > 1) {
            return R.fail("异常查询，唯一识别码查出多条数据");
        }
        //TODO 查询基础数据
        EntityInfo entityInfo = entityInfos.get(0);
        //TODO 查询上市情况  --  A股曾用证券简称  A股证券简称变更日期
        entityInfoDetails.setEntityInfo(entityInfo);
        //查询 A股 证券信息
        entityInfoDetails = getStockCnInfo(entityInfoDetails, entityCode);

        //查询 港股 证券信息
        entityInfoDetails = getStockThkInfo(entityInfoDetails, entityCode);

        //TODO 查询发债情况  --  是否可以收数
        entityInfoDetails = getEntityBondInfo(entityInfoDetails, entityCode);

        //TODO 查询金融机构  --  是否发行同业存单（银行）
        entityInfoDetails = getEntityFinancials(entityInfoDetails, entityCode, entityInfo);

        //TODO 查询敞口划分  --  客户敞口行业划分汇集  产业链CICS行业划分明细  旧辖口行业划分
        //从主表获取数据

        //TODO 查询产品覆盖情况  --  全部


        //TODO 其他一般工商信息  --  全部
        EntityBaseBusiInfo baseBusiInfo = entityBaseBusiInfoService.getInfoByEntityCode(entityCode);

        entityInfoDetails.setEntityBaseBusiInfo(baseBusiInfo);
        return R.ok(entityInfoDetails);
    }

    private EntityInfoDetails getEntityFinancials(EntityInfoDetails entityInfoDetails, String entityCode, EntityInfo entityInfo) {

        if (ObjectUtil.isEmpty(entityInfo.getFinance()) || entityInfo.getFinance() == 0) {
            return entityInfoDetails;
        }
        //查询金融机构表
        EntityFinancial entityFinancial = financialMapper.selectList(new QueryWrapper<EntityFinancial>().lambda().eq(EntityFinancial::getEntityCode, entityCode)).get(0);
        entityInfoDetails.setEntityFinancial(entityFinancial);
        return entityInfoDetails;
    }

    private EntityInfoDetails getEntityBondInfo(EntityInfoDetails entityInfoDetails, String entityCode) {
        BondInfoDetail bondInfoDetail = new BondInfoDetail();
        //查询关联表
        List<EntityBondRel> entityBondRels = entityBondRelMapper.selectList(new QueryWrapper<EntityBondRel>().lambda().eq(EntityBondRel::getEntityCode, entityCode));
        //是否发债（含历史） -- 有过就发债过
        if (CollectionUtils.isEmpty(entityBondRels)) {
            bondInfoDetail.setIsBond(false);
            return null;
        }
        bondInfoDetail.setIsBond(true);
        //查询债券数据
        List<String> bdCodes = new ArrayList<>();
        entityBondRels.stream().forEach(o -> bdCodes.add(o.getBdCode()));
        List<BondInfo> bondInfos = bondInfoMapper.selectList(new QueryWrapper<BondInfo>().lambda().in(BondInfo::getBondCode, bdCodes));
        // 是否可以收数  TODO 校验收数规则
        bondInfoDetail.setIsColl(true);
        //首次发债时间
        List<BondInfo> liveBonds = bondInfos.stream().filter(o -> !ObjectUtil.isEmpty(o.getValueDate())).collect(Collectors.toList());
        liveBonds.sort(Comparator.comparing(BondInfo::getValueDate));
        bondInfoDetail.setFirstBond(liveBonds.get(0).getValueDate());

        //获取集合债信息
        List<BondInfo> collList = bondInfos.stream().filter(o -> !ObjectUtil.isEmpty(o.getColl())).collect(Collectors.toList());
        // 是否发行集合债
        // 发行集合债详情
        if (CollectionUtils.isEmpty(collList)) {
            bondInfoDetail.setIsCollBond(false).setCollBondsNum(0).setCollBondsLiveNum(0);
        } else {
            //存续集合债数量
            bondInfoDetail.setIsCollBond(true)
                    .setCollBondsNum(collList.size())
                    .setCollBonds(collList)
                    .setCollBondsLiveNum(collList.stream().filter(o -> TimeFormatUtil.between_days("yyyy-MM-dd", TimeFormatUtil.getFormartDate(new Date()), o.getDueDate()) < 0).collect(Collectors.toList()).size());
        }

        //获取ABS信息
        List<BondInfo> absList = bondInfos.stream().filter(o -> !ObjectUtil.isEmpty(o.getAbs())).collect(Collectors.toList());
        // 是否发行ABS
        // 发行ABS详情
        if (CollectionUtils.isEmpty(absList)) {
            bondInfoDetail.setIsAbsBond(false).setAbsBondsNum(0).setAbsBondsLiveNum(0);
        } else {
            //存续ABS数量
            bondInfoDetail.setIsAbsBond(true)
                    .setAbsBondsNum(absList.size())
                    .setAbsBonds(absList)
                    .setAbsBondsLiveNum(absList.stream().filter(o -> TimeFormatUtil.between_days("yyyy-MM-dd", TimeFormatUtil.getFormartDate(new Date()), o.getDueDate()) < 0).collect(Collectors.toList()).size());
        }

        //获取公募债信息
        List<BondInfo> publicList = bondInfos.stream().filter(o -> !ObjectUtils.isEmpty(o.getRaiseType())&&o.getRaiseType() == 0).collect(Collectors.toList());
        // 是否发行公募债
        // 发行公募债详情
        if (CollectionUtils.isEmpty(publicList)) {
            bondInfoDetail.setIsPublicBond(false).setPublicBondsNum(0).setPublicBondsLiveNum(0);
        } else {
            //存续公募债数量
            bondInfoDetail.setIsPublicBond(true)
                    .setPublicBondsNum(publicList.size())
                    .setPublicBonds(publicList)
                    .setPublicBondsLiveNum(publicList.stream().filter(o -> TimeFormatUtil.between_days("yyyy-MM-dd", TimeFormatUtil.getFormartDate(new Date()), o.getDueDate()) < 0).collect(Collectors.toList()).size());
        }

        //获取私募债信息
        List<BondInfo> privateList = bondInfos.stream().filter(o ->!ObjectUtils.isEmpty(o.getRaiseType())&& o.getRaiseType() == 1).collect(Collectors.toList());
        // 是否发行私募债
        // 发行私募债详情
        if (CollectionUtils.isEmpty(privateList)) {
            bondInfoDetail.setIsPrivateBond(false).setPrivateBondsNum(0).setPrivateBondsLiveNum(0);
        } else {
            //存续私募债数量
            bondInfoDetail.setIsPrivateBond(true)
                    .setPrivateBondsNum(privateList.size())
                    .setPrivateBonds(privateList)
                    .setPrivateBondsLiveNum(privateList.stream().filter(o -> TimeFormatUtil.between_days("yyyy-MM-dd", TimeFormatUtil.getFormartDate(new Date()), o.getDueDate()) < 0).collect(Collectors.toList()).size());
        }

        entityInfoDetails.setBondInfoDetail(bondInfoDetail);
        return entityInfoDetails;

    }


    private EntityInfoDetails getStockThkInfo(EntityInfoDetails entityInfoDetails, String entityCode) {
        //查询关联表
        List<EntityStockThkRel> entityStockThkRels = thkRelMapper.selectList(new QueryWrapper<EntityStockThkRel>().lambda().eq(EntityStockThkRel::getEntityCode, entityCode));
        if (CollectionUtils.isEmpty(entityStockThkRels)) {
            return null;
        }
        //查询港股上市数据
        List<String> thkRelCodes = new ArrayList<>();
        entityStockThkRels.stream().forEach(o -> thkRelCodes.add(o.getStockDqCode()));
        List<StockThkInfo> stockThkInfos = stockThkMapper.selectList(new QueryWrapper<StockThkInfo>().lambda().in(StockThkInfo::getStockDqCode, thkRelCodes));
        //上市数据排序  TODO 验证排序是否是升序排序
        stockThkInfos.stream().filter(o -> !ObjectUtil.isEmpty(o.getListDate())).collect(Collectors.toList()).sort(Comparator.comparing(o -> o.getListDate()));
        StockThkInfo stockThkInfo = stockThkInfos.get(stockThkInfos.size() - 1);
        //退市日期
        String delistingDate = stockThkInfo.getDelistingDate();
        if (ObjectUtil.isEmpty(delistingDate)) {
            entityInfoDetails.setListType(entityInfoDetails.getListType() + "G股");
            entityInfoDetails.setListTypeG("存续");
        } else {
            int i = TimeFormatUtil.between_days("yyyy-MM-dd", TimeFormatUtil.getFormartDate(new Date()), delistingDate);
            if (i >= 0) {
                String listType = entityInfoDetails.getListType();
                if (ObjectUtil.isEmpty(listType)) {
                    entityInfoDetails.setListType("G股");
                } else {
                    entityInfoDetails.setListType(entityInfoDetails.getListType() + "G股");
                }
                entityInfoDetails.setListTypeG("存续");
            } else {
                entityInfoDetails.setListTypeA("已退市");
            }
        }
        entityInfoDetails.setStockThkInfo(stockThkInfo);
        return entityInfoDetails;
    }

    private EntityInfoDetails getStockCnInfo(EntityInfoDetails entityInfoDetails, String entityCode) {
        //查询关联表
        List<EntityStockCnRel> entityStockCnRels = cnRelMapper.selectList(new QueryWrapper<EntityStockCnRel>().lambda().eq(EntityStockCnRel::getEntityCode, entityCode));
        if (CollectionUtils.isEmpty(entityStockCnRels)) {
            return entityInfoDetails;
        }
        //查询A股上市数据
        List<String> cnRelCodes = new ArrayList<>();
        entityStockCnRels.stream().forEach(o -> cnRelCodes.add(o.getStockDqCode()));
        List<StockCnInfo> stockCnInfos = stockCnMapper.selectList(new QueryWrapper<StockCnInfo>().lambda().in(StockCnInfo::getStockDqCode, cnRelCodes));
        //上市数据排序  TODO 验证排序是否是升序排序
        stockCnInfos.stream().filter(o -> !ObjectUtil.isEmpty(o.getListDate())).collect(Collectors.toList()).sort(Comparator.comparing(o -> o.getListDate()));
        StockCnInfo stockCnInfo = stockCnInfos.get(stockCnInfos.size() - 1);
        //退市日期
        String delistingDate = stockCnInfo.getDelistingDate();
        if (ObjectUtil.isEmpty(delistingDate)) {
            entityInfoDetails.setListType("A股");
            entityInfoDetails.setListTypeA("存续");
        } else {
            int i = TimeFormatUtil.between_days("yyyy-MM-dd", TimeFormatUtil.getFormartDate(new Date()), delistingDate);
            if (i >= 0) {
                entityInfoDetails.setListType("A股");
                entityInfoDetails.setListTypeA("存续");
            } else {
                entityInfoDetails.setListTypeA("已退市");
            }
        }
        entityInfoDetails.setStockCnInfo(stockCnInfo);
        return entityInfoDetails;
    }

    @Autowired
    private EntityBaseBusiInfoService entityBaseBusiInfoService;

    /**
     * 根据多个主体code查询
     *
     * @param entityCodes
     * @return
     */
    @Override
    public List<EntityInfo> findListByEntityCodes(List<String> entityCodes) {
        Wrapper<EntityInfo> wrapper = Wrappers.<EntityInfo>lambdaQuery()
                .in(EntityInfo::getEntityCode, entityCodes);

        return this.list(wrapper);
    }

    @Override
    public R getInfoList(Integer type, String param, Integer pageNum, Integer pageSize) {
//        EntityInfo entityInfo = new EntityInfo();
//        企业主体类型 1、上市 2、发债 3、非上市，非发债 4、金融机构
        if (ObjectUtil.isEmpty(pageNum)) {
            pageNum = DEFAULT_PAGENUM;
        }
        if (ObjectUtil.isEmpty(pageSize)) {
            pageSize = DEFAULT_PAGESIZE;
        }
        if (ObjectUtil.isEmpty(param)) {
            param = "";
        }
        Page<EntityInfo> pageInfo = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<EntityInfo> infoQuery = new LambdaQueryWrapper<>();
        if (ObjectUtils.isEmpty(type)) {
            type = 1;
        }
        if (type == 1) {
            infoQuery.eq(EntityInfo::getList, 1);
        } else if (type == 2) {
            infoQuery.eq(EntityInfo::getIssueBonds, 1);
        } else if (type == 3) {
            infoQuery.eq(EntityInfo::getList, 0).eq(EntityInfo::getIssueBonds, 0);
        } else if (type == 4) {
            infoQuery.eq(EntityInfo::getFinance, 1);
        }
        infoQuery.like(EntityInfo::getEntityCode, param);
        infoQuery.or().like(EntityInfo::getEntityName, param);

//        List<EntityInfo> entityInfoList = entityInfoMapper.selectGovInfoListByTypeAndParam( type,  param, pageNum, pageSize);
        Page<EntityInfo> page = entityInfoMapper.selectPage(pageInfo, infoQuery);
        List<EntityInfo> entityInfoList = page.getRecords();

        if (CollectionUtils.isEmpty(entityInfoList)) {
            return R.ok();
        }
        //封装结果集
        List<EntityInfoList> records = new ArrayList<>();
        //相响分页结果
//        Page<Map<String, Object>> resultPage = new Page<>(pageNum, pageSize);
        Page<EntityInfoList> resultPage = new Page<>(pageNum, pageSize);

        resultPage.setTotal(page.getTotal()).setCurrent(page.getCurrent());

        List<EntityInfoList> finalRecords = records;
        //查出所有的曾用名
        QueryWrapper<EntityNameHis> hisQuery = new QueryWrapper<>();
        List<EntityNameHis> nameHisList = nameHisMapper.selectList(hisQuery.lambda().eq(EntityNameHis::getEntityType, 1));
        Map<String, List<EntityNameHis>> hisNameListMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(nameHisList)) {
            hisNameListMap = nameHisList.stream().collect(Collectors.groupingBy(EntityNameHis::getDqCode));
        }
        Map<String, List<EntityNameHis>> finalHisNameListMap = hisNameListMap;
        Integer finalType = type;
        List<String> codeList = new ArrayList<>();
        entityInfoList.stream().forEach(o -> codeList.add(o.getEntityCode()));

        entityInfoList.stream().forEach(o -> {
            finalRecords.add(getResultMap(o, finalHisNameListMap, finalType));
        });
        records = finalRecords;
        //查询上市特定列----证券代码，上市日期，退市日期，更新记录  1
        if (type == 1) {
            records = getListSpecial(records, codeList);
        }
        //查询发债特定列----存续债数量 存续债明细  2
        else if (type == 2) {
            records = getIssSpecial(records, codeList);
        }
        //查询金融机构特定列----细分行业  4  TODO
        else if (type == 4) {
            records = getFinSpecial(records, codeList);
        }
        resultPage.setRecords(records);
        return R.ok(resultPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateInfoList(List<EntityInfo> list) {
        list.stream().forEach(o -> {
            EntityInfo entityInfo = entityInfoMapper.selectById(o.getId());
            entityInfoMapper.updateById(o);
            //修改政府主体名称时，需要添加曾用名
            if (!ObjectUtils.isEmpty(o.getEntityName())) {
                String oldName = entityInfo.getEntityNameHis();
                EntityInfo addOldName = new EntityInfo();
                addOldName.setId(o.getId());
                addOldName.setEntityNameHis(oldName);
                addOldName.setEntityNameHisRemarks(o.getEntityNameHisRemarks());
                addOldName(addOldName);
            }
            //修改政府主体代码时，需要修改主体历史表中的政府主体代码
            if (!ObjectUtils.isEmpty(o.getEntityCode())) {
                String oldDqCode = entityInfo.getEntityCode();
                QueryWrapper<EntityNameHis> wrapper = new QueryWrapper<>();
                EntityNameHis nameHis = new EntityNameHis();
                nameHis.setDqCode(o.getEntityCode());
                nameHisMapper.update(nameHis, wrapper.lambda().eq(EntityNameHis::getDqCode, oldDqCode));
            }
        });
        return list.size();
    }

    @Override
    public List<EntityInfo> checkEntity(EntityInfo entityInfo) {
        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper(entityInfo);
        return entityInfoMapper.selectList(queryWrapper);
    }

    @Override
    public Object getListEntityByPage(EntityAttrByDto entityAttrDto) {
        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();
        if (ObjectUtils.isEmpty(pageNum) && ObjectUtils.isEmpty(pageSize)) {
            return getListEntityAll(entityAttrDto);
        } else {
            return getListEntityPage(entityAttrDto);
        }
    }

    /**
     * 根据统一社会信用代码 查询主体信息
     *
     * @param creditCode
     * @return
     */
    @Override
    public EntityInfo getEntityInfoByCreditCode(String creditCode) {
        return baseMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getCreditCode, creditCode));
    }

    /**
     * 全量导出
     *
     * @param entityAttrDto
     * @return List<EntityInfoResult>
     * @author 冉浩岑
     * @date 2022/9/25 17:04
     */
    @Override
    public List<EntityInfoResult> getListEntityAll(EntityAttrByDto entityAttrDto) {
        //获取参数信息
        List<MoreIndex> mapList = entityAttrDto.getMapList();
        //0.公募债券 1.私募债券
        Integer raiseType = null;

        Integer privateType = entityAttrDto.getPrivateType();
        Integer publicType = entityAttrDto.getPublicType();
        if (!ObjectUtils.isEmpty(publicType) && !ObjectUtils.isEmpty(privateType)) {
            raiseType = null;
        } else if (!ObjectUtils.isEmpty(publicType)) {
            raiseType = 0;
        } else if (!ObjectUtils.isEmpty(privateType)) {
            raiseType = 1;
        }

        //获取是否是abs
        Integer abs = entityAttrDto.getAbs();
        //获取是否是集合债
        Integer coll = entityAttrDto.getColl();

        //获取是否是港股
        Integer stockThk = entityAttrDto.getStockThk();
        //获取是否是A股
        Integer stockCn = entityAttrDto.getStockCn();

        List<EntityInfo> entityInfos = entityInfoMapper.getEntityByBondType(raiseType, abs, coll, stockThk, stockCn);
        //封装新的结果集
        List<EntityInfoResult> resultRecords = new ArrayList<>();

        entityInfos.stream().forEach(o -> {
            EntityInfoResult entityInfoResult = getEntityInfoResult(o, mapList);
            resultRecords.add(entityInfoResult);
        });
        return resultRecords;
    }

    /**
     * 导出企业主体数据
     *
     * @return void
     * @author penTang
     * @date 2022/9/25 21:19
     */
    @Override
    public void ExportEntityInFor(EntityAttrByDto entityAttrDto) {
        List<EntityInfoResult> listEntityAll = this.getListEntityAll(entityAttrDto);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        AtomicInteger serialNumber = new AtomicInteger();
        listEntityAll.forEach(vo -> {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            EntityInfo info = vo.getEntityInfo();
            map.put("序号", serialNumber.incrementAndGet());
            map.put("德勤主体代码", info.getEntityCode());
            map.put("主体名称", info.getEntityName());
            map.put("续存状态", info.getStatus());
            map.put("统一社会性代码", info.getCreditCode());
            map.put("创建日期", DateUtil.parseDateToStr("yyyy/MM/dd", info.getCreated()));
            map.put("创建人", info.getCreater());
            if (!CollectionUtils.isEmpty(vo.getMore())) {
                vo.getMore().forEach(entryMap -> map.put(entryMap.getKey(), map.get("value")));
            }
            rows.add(map);
        });
        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        // 设置自适应
        Sheet sheet = writer.getSheet();
        // 循环设置列宽
        for (int columnIndex = 0; columnIndex < writer.getColumnCount(); columnIndex++) {
            int width = sheet.getColumnWidth(columnIndex) / 256;
            // 获取最大行宽
            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);
                if (Objects.isNull(currentRow)) {
                    currentRow = sheet.createRow(rowIndex);
                }
                Cell currentCell = currentRow.getCell(columnIndex);
                if (Objects.isNull(currentCell)) {
                    continue;
                } else if (currentCell.getCellType() == CellType.STRING) {
                    int length = currentCell.getStringCellValue().getBytes().length;
                    width = width < length ? length : width;
                }
            }
            sheet.setColumnWidth(columnIndex, width * 256);
        }
        // response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + (URLEncoder.encode("企业主体", "UTF-8")) + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        try (ServletOutputStream out = response.getOutputStream()) {
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();

        }
        // 关闭writer，释放内存
        writer.close();
        IoUtil.close(out);

    }


    /**
     * 分页查询
     *
     * @param entityAttrDto
     * @return List<EntityInfoResult>
     * @author 冉浩岑
     * @date 2022/9/25 17:04
     */
    public Page<EntityInfoResult> getListEntityPage(EntityAttrByDto entityAttrDto) {
        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();
        Integer raiseType = null;

        Integer privateType = entityAttrDto.getPrivateType();
        Integer publicType = entityAttrDto.getPublicType();
        if (!ObjectUtils.isEmpty(publicType) && !ObjectUtils.isEmpty(privateType)) {
            raiseType = null;
        } else if (!ObjectUtils.isEmpty(publicType)) {
            raiseType = 0;
        } else if (!ObjectUtils.isEmpty(privateType)) {
            raiseType = 1;
        }
        //获取是否是abs
        Integer abs = entityAttrDto.getAbs();
        //获取是否是集合债
        Integer coll = entityAttrDto.getColl();

        //获取是否是港股
        Integer stockThk = entityAttrDto.getStockThk();
        //获取是否是A股
        Integer stockCn = entityAttrDto.getStockCn();

        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 9;
        }
        Page<EntityInfoResult> pageResult = new Page<>(pageNum, pageSize);
        //封装新的结果集
        List<EntityInfoResult> resultRecords = new ArrayList<>();
        List<MoreIndex> mapList = entityAttrDto.getMapList();

        Integer count = entityInfoMapper.getEntityCountByBondType(raiseType, abs, coll, stockThk, stockCn);
        pageNum = (pageNum - 1) * pageSize;
        List<EntityInfo> records = entityInfoMapper.getEntityByBondTypeByPage(raiseType, abs, coll, pageNum, pageSize, stockThk, stockCn);

        pageResult.setTotal(count);

        records.stream().forEach(o -> {
            EntityInfoResult entityInfoResult = getEntityInfoResult(o, mapList);
            resultRecords.add(entityInfoResult);
        });
        pageResult.setRecords(resultRecords);
        return pageResult;
    }

    private EntityInfoResult getEntityInfoResult(EntityInfo o, List<MoreIndex> mapList) {
        EntityInfoResult entityInfoResult = new EntityInfoResult();
        entityInfoResult.setEntityInfo(o);
        List<String> header = new ArrayList<>();
        List<String> values = new ArrayList<>();
        if (!CollectionUtils.isEmpty(mapList)) {
            List<MoreIndex> more = new ArrayList<>();
            for (MoreIndex moreIndex : mapList) {
                QueryWrapper<EntityAttrValue> valueQuer = new QueryWrapper<>();
                EntityAttrValue attrValue = entityAttrValueMapper.selectOne(valueQuer.lambda()
                        .eq(EntityAttrValue::getAttrId, moreIndex.getId())
                        .eq(EntityAttrValue::getEntityCode, o.getEntityCode()));
                //新增指标栏

                moreIndex.setKey(moreIndex.getName());
                header.add(moreIndex.getName());

                if (ObjectUtils.isEmpty(attrValue)) {
                    values.add(null);
                } else {
                    String value = attrValue.getValue();
                    values.add(value);
                    moreIndex.setValue(value);
                }
                more.add(moreIndex);
            }
            entityInfoResult.setMore(more).setHeader(header).setValues(values);
        }
        return entityInfoResult;
    }

    /**
     * 获取额外的数据列信息
     *
     * @param mapList
     * @return List<Map < String, Object>>
     * @author 冉浩岑
     * @date 2022/9/26 8:37
     */
    public List<Map<String, Object>> getEntityAttrValue(List<Map<String, String>> mapList, EntityInfo o) {
        List<Map<String, Object>> more = new ArrayList<>();
        for (Map<String, String> map : mapList) {
            QueryWrapper<EntityAttrValue> valueQuer = new QueryWrapper<>();
            EntityAttrValue attrValue = entityAttrValueMapper.selectOne(valueQuer.lambda()
                    .eq(EntityAttrValue::getAttrId, map.get(EntityUtils.MORE_ENTITY_KPI_ID))
                    .eq(EntityAttrValue::getEntityCode, o.getEntityCode()));

            Map<String, Object> moreMap = new HashMap<>();
            //新增指标栏
            moreMap.put(EntityUtils.MORE_ENTITY_KPI_KEY, map.get(EntityUtils.MORE_ENTITY_KPI_NAME));
            if (ObjectUtils.isEmpty(attrValue)) {
                moreMap.put(EntityUtils.MORE_ENTITY_KPI_VALUE, null);
            } else {
                moreMap.put(EntityUtils.MORE_ENTITY_KPI_VALUE, attrValue.getValue());
            }
            more.add(moreMap);
        }
        return more;
    }

    /**
     * EntityInfo 对象转 map,并查询 曾用名条数
     *
     * @param entityInfo
     * @return Map<String, Object>
     * @author 冉浩岑
     * @date 2022/9/22 23:45
     */
    public EntityInfoList getResultMap(EntityInfo entityInfo, Map<String, List<EntityNameHis>> map, Integer type) {
        EntityInfoList entityInfoList = new EntityInfoList();
        if (null != entityInfo) {
            entityInfoList.setEntityInfo(entityInfo);
            try {
                Integer count = 0;
                if (!map.isEmpty()) {
                    List<EntityNameHis> nameHisList = map.get(entityInfo.getEntityCode());
                    if (!CollectionUtils.isEmpty(nameHisList)) {
                        count = nameHisList.size();
                    }
                }
                entityInfoList.setNameUsedNum(count);
            } catch (Exception e) {
                return entityInfoList;
            }
        }
        return entityInfoList;
    }

    private List<EntityInfoList> getFinSpecial(List<EntityInfoList> record, List<String> codeList) {
        QueryWrapper<EntityAttrValue> valueQueryWrapper = new QueryWrapper<>();

        List<EntityAttrValue> attrValueList = entityAttrValueMapper.selectList(valueQueryWrapper.lambda()
                .eq(EntityAttrValue::getAttrId, 656)
                .in(EntityAttrValue::getEntityCode, codeList)
        );
        if (CollectionUtils.isEmpty(attrValueList)) {
            return record;
        }
        for (int i = 0; i < record.size(); i++) {

            EntityInfoList result = record.get(i);
            String entityCode = result.getEntityCode();
            List<EntityAttrValue> valueList = attrValueList.stream().collect(Collectors.groupingBy(EntityAttrValue::getEntityCode)).get(entityCode);
            if (CollectionUtils.isEmpty(valueList)) {
                record.set(i, result);
                continue;
            }
            //存续债明细 TODO
            List<String> liveBondDetail = new ArrayList<>();
            valueList.stream().forEach(o -> liveBondDetail.add(o.getValue()));
            //细分行业
            result.setIndustry(liveBondDetail);
            record.set(i, result);
        }
        return record;
    }

    /**
     * 查询发债特定列----存续债数量 存续债明细  2
     */
    private List<EntityInfoList> getIssSpecial(List<EntityInfoList> record, List<String> codeList) {
        QueryWrapper<EntityBondRel> relQueryWrapper = new QueryWrapper<>();

        List<EntityBondRel> entityBondRels = entityBondRelMapper.selectList(relQueryWrapper.lambda()
                .eq(EntityBondRel::getStatus, 1)
                .in(EntityBondRel::getEntityCode, codeList)
        );
        if (CollectionUtils.isEmpty(entityBondRels)) {
            return record;
        }
        for (int i = 0; i < record.size(); i++) {
            Integer liveBond = 0;
            EntityInfoList result = record.get(i);
            String entityCode = result.getEntityCode();
            List<EntityBondRel> bondRels = entityBondRels.stream().collect(Collectors.groupingBy(EntityBondRel::getEntityCode)).get(entityCode);

            //债券存续数量
            result.setLiveBond(liveBond);
            if (CollectionUtils.isEmpty(bondRels)) {
                record.set(i, result);
                continue;
            }
            liveBond = bondRels.size();
            //存续债明细 TODO
            List<String> liveBondDetail = new ArrayList<>();
            bondRels.stream().forEach(o -> liveBondDetail.add(o.getBdCode()));
            //债券存续数量
            result.setLiveBond(liveBond);
            result.setLiveBondDetail(liveBondDetail);
            record.set(i, result);
        }
        return record;
    }

    /**
     * 查询上市特定列----证券代码，上市日期，退市日期，更新记录  1
     */
    private List<EntityInfoList> getListSpecial(List<EntityInfoList> record, List<String> codeList) {

        //封装退市日期   A股退市 584  港股退市 602
        QueryWrapper<EntityAttrValue> valueQueryWrapper = new QueryWrapper<>();
        List<EntityAttrValue> attrValueList = entityAttrValueMapper.selectList(valueQueryWrapper.lambda()
                .in(EntityAttrValue::getEntityCode, codeList)
                .in(EntityAttrValue::getAttrId, 584, 602)
        );

        QueryWrapper<EntityStockCnRel> cnRelQueryWrapper = new QueryWrapper<>();
        List<EntityStockCnRel> entityStockCnRels = cnRelMapper.selectList(cnRelQueryWrapper.lambda().in(EntityStockCnRel::getEntityCode, codeList));

        QueryWrapper<EntityStockThkRel> thkRelQueryWrapper = new QueryWrapper<>();
        List<EntityStockThkRel> entityStockthkRels = thkRelMapper.selectList(thkRelQueryWrapper.lambda().in(EntityStockThkRel::getEntityCode, codeList));
        //封装证券代码
        for (int i = 0; i < record.size(); i++) {
            EntityInfoList entityInfoList = record.get(i);
            String o = entityInfoList.getEntityCode();
            //证券代码
            List<String> stockCodeList = new ArrayList<>();
            //上市日期
            List<String> stockDateList = new ArrayList<>();
            //退市日期
            List<String> stockdownDateList = new ArrayList<>();

            if (!CollectionUtils.isEmpty(entityStockCnRels)) {
                List<EntityStockCnRel> cnRels = entityStockCnRels.stream().collect(Collectors.groupingBy(EntityStockCnRel::getEntityCode)).get(o);
                if (!CollectionUtils.isEmpty(cnRels)) {
                    cnRels.stream().forEach(x -> {
                        //证券代码
                        stockCodeList.add(x.getStockDqCode());
                        //上市日期
                        stockDateList.add(TimeFormatUtil.getFormartDate(x.getCreated()));
                    });
                }
            }
            if (!CollectionUtils.isEmpty(entityStockthkRels)) {
                List<EntityStockThkRel> thkRels = entityStockthkRels.stream().collect(Collectors.groupingBy(EntityStockThkRel::getEntityCode)).get(o);
                if (!CollectionUtils.isEmpty(thkRels)) {
                    thkRels.stream().forEach(x -> {
                        //证券代码
                        stockCodeList.add(x.getStockDqCode());
                        //上市日期
                        stockDateList.add(TimeFormatUtil.getFormartDate(x.getCreated()));
                    });
                }
            }
            //退市日期
            if (!CollectionUtils.isEmpty(attrValueList)) {
                List<EntityAttrValue> attrValues = attrValueList.stream().collect(Collectors.groupingBy(EntityAttrValue::getEntityCode)).get(o);
                if (!CollectionUtils.isEmpty(attrValues)) {
                    attrValues.stream().forEach(x -> {
                        stockdownDateList.add(x.getValue());
                    });
                }
            }
            entityInfoList.setStockCode(stockCodeList);
            entityInfoList.setListDate(stockDateList);
            entityInfoList.setDownDate(stockdownDateList);
            record.set(i, entityInfoList);
        }
        return record;
    }


    /**
     * 传入 bondInfo 查询返回 bondVo
     *
     * @param bondInfo
     * @return bondVo
     */
    public TargetEntityBondsVo matchingBondInfo(BondInfo bondInfo) {
        TargetEntityBondsVo result = new TargetEntityBondsVo();
        BondVo bondVo = new BondVo();
        //债券id => bond_info
        bondVo.setId(bondInfo.getId());
        //债券代码 => bond_code
        bondVo.setBondCode(bondInfo.getBondCode());
        //债券交易代码
//        bondVo.setTransactionCode(
//                entityAttrValueMapper.selectOne(new QueryWrapper<EntityAttrValue>()
//                        .lambda().eq(EntityAttrValue::getEntityCode, bondInfo.getBondCode())
//                        .eq(EntityAttrValue::getAttrId, Common.TRANSACTION_CODE_ID)).getValue()
//        );
        bondVo.setTransactionCode(bondInfo.getOriCode());
        //债券全称
//        bondVo.setFullName(
//                entityAttrValueMapper.selectOne(new QueryWrapper<EntityAttrValue>()
//                        .lambda().eq(EntityAttrValue::getEntityCode, bondInfo.getBondCode())
//                        .eq(EntityAttrValue::getAttrId, Common.BOND_NAME_ID)).getValue()
//        );
        bondVo.setFullName(bondInfo.getBondName());
        //债券简称
        bondVo.setShortName(bondInfo.getBondShortName());
        //存续状态
        bondVo.setDebtRaisingType(bondInfo.getBondState() == null ? null : bondInfo.getBondState().toString());
        //TODO 债募类型
        //公私募类型
        bondVo.setRaiseType(bondInfo.getRaiseType());
        //是否违约
        bondVo.setBondState(bondInfo.getBondState());
        result.setBondVo(bondVo);
        return result;
    }

    /**
     * 传入entityInfo 查询返回 entityVo
     *
     * @param entityInfo
     * @return
     */
    public TargetEntityBondsVo matchingEntityInfo(EntityInfo entityInfo) {
        TargetEntityBondsVo result = new TargetEntityBondsVo();
        if (entityInfo.getEntityCode() != null) {
            //取任意一个 bd_code都相等
            List<EntityBondRel> entityBondRels = entityBondRelMapper.selectList(new QueryWrapper<EntityBondRel>()
                    .lambda().eq(EntityBondRel::getEntityCode, entityInfo.getEntityCode()));
            if (entityBondRels.size() != 0) {
                EntityBondRel entityBondRel = entityBondRels.get(0);
                EntityAttrValue byAttrCode = entityAttrValueMapper.findTradCode(entityBondRel.getBdCode());
                result.setEntityVo((new EntityVo()
                                .setId(entityInfo.getId())
                                .setEntityName(entityInfo.getEntityName())
                                .setEntityCode(entityInfo.getEntityCode())
                                .setCreditCode(entityInfo.getCreditCode())
                                .setBondCode(byAttrCode.getValue())
                        )
                );
            } else {
                result.setEntityVo((new EntityVo()
                        .setId(entityInfo.getId())
                        .setEntityName(entityInfo.getEntityName())
                        .setEntityCode(entityInfo.getEntityCode())
                        .setCreditCode(entityInfo.getCreditCode())
                ));
            }
        }
        return result;
    }

    /**
     * 查询债卷信息 模糊匹配
     *
     * @param name    entity_name || bond_short_name
     * @param keyword 请传入常量 ENTITY || BOND
     * @return R<List < TargetEntityBondsVo>>
     * @author 正杰
     * @date 2022/9/25
     */
    @Override
    public R<Page<TargetEntityBondsVo>> findBondOrEntity(String name, String keyword, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 20 : pageSize;
        //模糊匹配 查询主体||债券信息
        switch (keyword) {
            //模糊匹配主体名
            case ENTITY:
                List<TargetEntityBondsVo> res = new ArrayList<>();
                //模糊匹配后的主体list
                Page<EntityInfo> entityInfoPage = baseMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<EntityInfo>()
                        .lambda().like(EntityInfo::getEntityName, name));
                List<EntityInfo> entityInfos = entityInfoPage.getRecords();
                if (entityInfos.size() == 0) {
                    return R.ok(null, BadInfo.VALID_EMPTY_TARGET.getInfo());
                }
                //找到对应bd_code
                entityInfos.forEach(row -> {
                    res.add(this.matchingEntityInfo(row));
                });
                Page<TargetEntityBondsVo> targetEntityBondsVoPage = new Page<>();
                targetEntityBondsVoPage.setRecords(res)
                        .setTotal(entityInfoPage.getTotal())
                        .setPages(entityInfoPage.getPages())
                        .setCurrent(entityInfoPage.getCurrent())
                        .setSize(entityInfoPage.getSize());
                return R.ok(targetEntityBondsVoPage, SuccessInfo.SUCCESS.getInfo());
            // 模糊匹配债券名
            case BOND:
                List<TargetEntityBondsVo> rest = new ArrayList<>();
                List<EntityAttrValue> entityAttrs;
                //模糊匹配全名 债券list
                //模糊匹配短名 债券list
                Page<BondInfo> bondInfoPage = bondInfoMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<BondInfo>().lambda()
                        .like(BondInfo::getBondName, name).like(BondInfo::getBondShortName, name));
                List<BondInfo> bondInfos = bondInfoPage.getRecords();
                if (bondInfos.size() == 0) {
                    return R.fail(BadInfo.VALID_EMPTY_TARGET.getInfo());
                }

                //查找属性数据 组装
                bondInfos.forEach(row -> {
                    rest.add(this.matchingBondInfo(row));
                });
                Page<TargetEntityBondsVo> targetEntityBondsVoPageBond = new Page<>();
                targetEntityBondsVoPageBond.setRecords(rest)
                        .setTotal(bondInfoPage.getTotal())
                        .setPages(bondInfoPage.getPages())
                        .setCurrent(bondInfoPage.getCurrent())
                        .setSize(bondInfoPage.getSize());
                return R.ok(targetEntityBondsVoPageBond, SuccessInfo.SUCCESS.getInfo());
            default:
                return R.fail(BadInfo.VALID_PARAM.getInfo());
        }
    }

    /**
     * 查询债券或是主体下相关的主体或是债券信息 by正杰
     *
     * @param id
     * @param keyword
     * @return
     * @author 正杰
     * @date 2022/9/25
     */
    @Override
    public R<List<TargetEntityBondsVo>> findRelationEntityOrBond(Integer id, String keyword) {
        List<TargetEntityBondsVo> result = new ArrayList<>();
        switch (keyword) {
            case ENTITY:
                EntityInfo entity = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getId, id));
                List<EntityBondRel> entityBondRels = entityBondRelMapper.selectList(new QueryWrapper<EntityBondRel>()
                        .lambda().eq(EntityBondRel::getEntityCode, entity.getEntityCode()));
                entityBondRels.forEach(row -> {
                    BondInfo bondInfo = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda()
                            .eq(BondInfo::getBondCode, row.getBdCode()));
                    result.add(this.matchingBondInfo(bondInfo));
                });
                return R.ok(result);
            case BOND:
                BondInfo bondInfo = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getId, id));
                List<EntityBondRel> entityBondRels1 = entityBondRelMapper.selectList(new QueryWrapper<EntityBondRel>().lambda()
                        .eq(EntityBondRel::getBdCode, bondInfo.getBondCode()));
                entityBondRels1.forEach(item -> {
                    EntityInfo entityInfo = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda()
                            .eq(EntityInfo::getEntityCode, item.getEntityCode()));
                    result.add(this.matchingEntityInfo(entityInfo));
                });
                return R.ok(result);
            default:
                return R.ok(result);
        }
    }

    @Override
    public Map<String, Object> getOverview() {
        QueryWrapper<EntityInfo> query = new QueryWrapper<>();
//        list	是否上市 0-未上市 1-已上市
//        finance		0	是否金融机构
//        issue_bonds		是否发债 0-未发债 1-已发债
        Long count = entityInfoMapper.selectCount(query);
        //上市主体
        Long list = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getList, 1));
        query.clear();
        //发债主体
        Long bonds = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getIssueBonds, 1));
        query.clear();
        //既上市主体又发债主体
        Long unListBonds = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getList, 1).eq(EntityInfo::getIssueBonds, 1));
        query.clear();
        //既没上市主体又不发债主体
        Long listBonds = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getList, 0).eq(EntityInfo::getIssueBonds, 0));
        query.clear();
        //金融
        Long finance = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getFinance, 1));

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("list", list);
        result.put("bonds", bonds);
        result.put("listBonds", listBonds);
        result.put("unListBonds", unListBonds);
        result.put("finance", finance);
        return result;
    }

    @Override
    public Map<String, Object> getOverviewByGroup() {
        QueryWrapper<EntityInfo> query = new QueryWrapper<>();
        //全部主体
        Long count = entityInfoMapper.selectCount(query);
        //上市主体
        Long list = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getList, 1));
        query.clear();
        //发债主体
        Long bonds = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getIssueBonds, 1));
        query.clear();
        //既上市主体又发债主体
        Long unListBonds = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getList, 1).eq(EntityInfo::getIssueBonds, 1));
        query.clear();
        //既没上市主体又不发债主体
        Long listBonds = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getList, 0).eq(EntityInfo::getIssueBonds, 0));
        query.clear();
        //金融
        Long finance = entityInfoMapper.selectCount(query.lambda().eq(EntityInfo::getFinance, 1));

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("list", list);
        result.put("bonds", bonds);
        result.put("listBonds", listBonds);
        result.put("unListBonds", unListBonds);
        result.put("finance", finance);
        return result;
    }

    @Override
    public Map<String, Object> getOverviewByAll() {
        //查询整体概览
        Long entityCount = entityInfoMapper.selectCount(new QueryWrapper<>());
        Long govCount = govInfoMapper.selectCount(new QueryWrapper<>());

        //封装政府主体概览
        Map<String, Object> govOverview = getGovOverview(govCount);
        //封装企业主体概览
        Map<String, Object> entityOverview = getOverview();
        //封装总体概览信息
        Map<String, Object> overviewAll = new HashMap<>();
        overviewAll.put("entityCount", entityCount);
        overviewAll.put("govCount", govCount);

        //封装结果集
        Map<String, Object> result = new HashMap<>();

        result.put("overviewAll", overviewAll);
        result.put("govOverview", govOverview);
        result.put("entityOverview", entityOverview);

        return result;
    }

    private Map<String, Object> getGovOverview(Long govCount) {
        QueryWrapper<GovInfo> govQuery = new QueryWrapper<>();
        //查询政府主体信息
//        @Excel(name = "sys_dict_data  gov_type1、地方政府2、地方主管部门3、其他")
//        政府
        Long govLocal = govInfoMapper.selectCount(govQuery.lambda().eq(GovInfo::getGovType, 1));
        govQuery.clear();
//        地方主管部门
        Long govMan = govInfoMapper.selectCount(govQuery.lambda().eq(GovInfo::getGovType, 2));
        govQuery.clear();

//        地级政府为“GV+官方行政代码”
        List<GovInfo> govInfosList = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_PRIVINCE_CODE);
//        县级政府为“GV+官方行政代码”
        AtomicReference<Integer> province = new AtomicReference<>(0);
        AtomicReference<Integer> city = new AtomicReference<>(0);
        AtomicReference<Integer> area = new AtomicReference<>(0);
        govInfosList.stream().forEach(o -> {
            String govName = o.getGovName();
            if (!ObjectUtils.isEmpty(govName)) {
                if (govName.contains(Common.DOV_INFO_TYPE_PRIVINCE_NAME)) {
                    province.getAndSet(province.get() + 1);
                } else if (govName.contains(Common.DOV_INFO_TYPE_CITY_NAME)) {
                    city.getAndSet(city.get() + 1);
                } else {
                    area.getAndSet(area.get() + 1);
                }
            }
        });
//        京开高新
//        经开区为“GVA”+000001开始排序
        Integer JK = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_JK_CODE).size();
//        高新区为“GVB”+000001开始排序
        Integer GX = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_GX_CODE).size();
//        新区为“GVC”+000001开始排序
        Integer XQ = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_XQ_CODE).size();
        Integer JKGX = JK + GX + XQ;
//        其他
        Long other = govCount - govLocal - govMan - province.get() - city.get() - area.get() - JKGX;

        //封装企业主体信息
        Map<String, Object> govOverview = new HashMap<>();
        govOverview.put("govLocal", govLocal);
        govOverview.put("govMan", govMan);
        govOverview.put("province", province);
        govOverview.put("city", city);
        govOverview.put("area", area);
        govOverview.put("JKGX", JKGX);
        govOverview.put("other", other);
        return govOverview;
    }

    /**
     * 校验统一社会信用代码是否存在 by正杰
     *
     * @param creditCode
     * @return
     * @author 正杰
     * @date 2022/9/28
     */
    @Override
    public R<EntityInfoVo> checkCreditCode(String creditCode) {
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(new QueryWrapper<EntityInfo>().lambda()
                .eq(EntityInfo::getCreditCode, creditCode));
        if (entityInfos.size() == 0) {
            return R.ok(new EntityInfoVo().setBo(true)
                    .setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
        }
        return R.ok(new EntityInfoVo().setBo(false)
                .setMsg(BadInfo.EXITS_ENTITY_CODE.getInfo())
                .setEntityInfo(entityInfos.get(0)));
    }

    /**
     * 校验主体名称是否存在
     *
     * @param entityName
     * @return R
     * @author 正杰
     * @date 2022/9/28
     */
    @Override
    public R<EntityInfoVo> checkEntityName(String entityName) {
        List<EntityInfo> entName = entityInfoMapper.selectList(new QueryWrapper<EntityInfo>().lambda()
                .eq(EntityInfo::getEntityName, entityName));
        if (entName.size() != 0) {
            return R.ok(new EntityInfoVo()
                    .setBo(false).setEntityInfo(entName.get(0)));
        }
        return R.ok(new EntityInfoVo()
                .setBo(true).setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
    }

    /**
     * 快速查询
     *
     * @param param
     * @param pageNum
     * @param pageSize
     * @return R
     * @author 冉浩岑
     * @date 2022/10/8 15:53
     */

    @Override
    public R getQuickOfCoverage(String param, Integer pageNum, Integer pageSize) {
        if (ObjectUtil.isEmpty(pageNum)) {
            return R.fail(NO_PAGENUM_ERROR);
        }
        if (ObjectUtil.isEmpty(pageSize)) {
            pageSize = DEFAULT_PAGESIZE;
        }
        //创建分页对象
        Page<EntityInfo> pageInfo = new Page<>(pageNum, pageSize);

        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();

        //创建分页结果集
        List<EntityInfoValueResult> resultList = new ArrayList<>();
        Page<EntityInfoValueResult> pageResult = new Page<>(pageNum, pageSize);

        Page<EntityInfo> page = entityInfoMapper.selectPage(pageInfo, queryWrapper.lambda()
                .like(EntityInfo::getEntityCode, param)
                .or().like(EntityInfo::getEntityName, param)
                .or().like(EntityInfo::getCreditCode, param).orderByAsc(EntityInfo::getEntityCode)
        );
        //新的分页结果赋值
        pageResult.setTotal(page.getTotal());

        List<EntityInfo> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            pageResult.setRecords(null);
        } else {
            records.stream().forEach(o -> {
                //封装后的新结果
                EntityInfoValueResult result = new EntityInfoValueResult();
                result.setEntityInfo(o);
                //获取上市情况
                List<String> listList = getListDetail(o);
                //上市详情
                String listDetail = listList.get(0);
                //证券代码
                String stockCode = listList.get(1);
                //获取发债情况
                String bondDetail = getBondDetail(o);

                //设置属性值
                result.setListDetail(listDetail).setBondDetail(bondDetail).setStockCode(stockCode);

                resultList.add(result);
            });
            pageResult.setRecords(resultList).setCurrent(page.getCurrent());
        }
        return R.ok(pageResult);
    }

    private String getBondDetail(EntityInfo o) {
        String bondDetail = "";

        //查询债券主体关联表
        QueryWrapper<EntityBondRel> relQuery = new QueryWrapper<>();
        List<EntityBondRel> entityBondRels = entityBondRelMapper.selectList(relQuery.lambda()
                .eq(EntityBondRel::getEntityCode, o.getEntityCode())
        );
        //没有关联关系  返回 null
        if (CollectionUtils.isEmpty(entityBondRels)) {
            return bondDetail;
        }
        //有关联关系    查询债券表 获取债券信息 识别并去重汇总每一条债券信息
        List<String> bondCodes = new ArrayList<>();
        entityBondRels.stream().forEach(x -> bondCodes.add(x.getBdCode()));
        QueryWrapper<BondInfo> bondQuery = new QueryWrapper<>();
        List<BondInfo> bondInfos = bondInfoMapper.selectList(bondQuery.lambda().in(BondInfo::getBondCode, bondCodes));
        //创建所有债券类型的字段  公(私)募债 集合债 abs
        AtomicReference<String> privateMsg = new AtomicReference<>("");
        AtomicReference<String> publicMsg = new AtomicReference<>("");
        AtomicReference<String> collMsg = new AtomicReference<>("");
        AtomicReference<String> absMsg = new AtomicReference<>("");
        //遍历所有债券信息
        bondInfos.stream().forEach(x -> {
            String status = "";
            // 债卷状态 0_存续 1_违约 2_已兑付
            Integer bondState = x.getBondState();
            if (!ObjectUtils.isEmpty(bondState) && bondState == 0) {
                status = "(存续)";
            } else if (!ObjectUtils.isEmpty(bondState) && bondState == 1) {
                status = "(违约)";
            }
            //公私募类型 0_公募 1_私募
            Integer raiseType = x.getRaiseType();

            if (!ObjectUtils.isEmpty(raiseType) && raiseType == 0 && !"公募债(存续)".equals(publicMsg.get())) {
                publicMsg.set("公募债" + status);
            } else if (!ObjectUtils.isEmpty(raiseType) && raiseType == 1 && !"私募债(存续)".equals(privateMsg.get())) {
                privateMsg.set("私募债" + status);
            }
            Boolean abs = x.getAbs();
            if (!ObjectUtils.isEmpty(abs) && abs && !"ABS(存续)".equals(absMsg.get())) {
                absMsg.set("ABS" + status);
            }
            Boolean coll = x.getColl();
            if (!ObjectUtils.isEmpty(coll) && coll && !"集合债(存续)".equals(collMsg.get())) {
                collMsg.set("集合债" + status);
            }
        });
        if (!ObjectUtil.isEmpty(privateMsg.get())) {
            bondDetail = privateMsg.get();
        }
        if (!ObjectUtil.isEmpty(publicMsg.get())) {
            if (ObjectUtil.isEmpty(bondDetail)) {
                bondDetail = publicMsg.get();
            } else {
                bondDetail = bondDetail + "," + publicMsg.get();
            }
        }
        if (!ObjectUtil.isEmpty(absMsg.get())) {
            if (ObjectUtil.isEmpty(bondDetail)) {
                bondDetail = absMsg.get();
            } else {
                bondDetail = bondDetail + "," + absMsg.get();
            }
        }
        if (!ObjectUtil.isEmpty(collMsg.get())) {
            if (ObjectUtil.isEmpty(bondDetail)) {
                bondDetail = collMsg.get();
            } else {
                bondDetail = bondDetail + "," + collMsg.get();
            }
        }
        return bondDetail;
    }

    //获取上市情况
    public List<String> getListDetail(EntityInfo o) {
        //上市详情
        String listDetail = "";
        //证券代码
        String stockCode = "";
        //查询是否存在 A股  上市情况
        QueryWrapper<EntityStockCnRel> cnRelQuery = new QueryWrapper<>();
        List<EntityStockCnRel> cnRels = cnRelMapper.selectList(cnRelQuery.lambda().eq(EntityStockCnRel::getEntityCode, o.getEntityCode()));
        //创建 A股 上市描述
        String ADetail = "";
        //不存在则直接返回
        //存在，则分别识别是否退市
        //A股 判断退市时间和当前时间
        if (!CollectionUtils.isEmpty(cnRels)) {
            //保存全部的上市 code
            List<String> cnCodes = new ArrayList<>();
            cnRels.stream().forEach(x -> cnCodes.add(x.getStockDqCode()));
            QueryWrapper<StockCnInfo> stockCnInfoQuery = new QueryWrapper<>();

            //查询 entityCode 下所有的A股信息
            List<StockCnInfo> stockCnInfos = stockCnMapper.selectList(stockCnInfoQuery.lambda().in(StockCnInfo::getStockDqCode, cnCodes));
            if (!CollectionUtils.isEmpty(stockCnInfos)) {
                for (int i = 0; i < stockCnInfos.size(); i++) {
                    if ("A股(存续)".equals(ADetail)) {
                        break;
                    }
                    StockCnInfo stockCnInfo = stockCnInfos.get(i);
                    //退市日期
                    String delistingDate = stockCnInfo.getDelistingDate();
                    // TODO 需要验证是否是这样判断
                    int days = TimeFormatUtil.between_days("yyyy-MM-dd", delistingDate, TimeFormatUtil.getFormartDate(new Date()));
                    if (days < 0) {
                        //当前时间小于退市时间------未退市
                        ADetail = "A股(存续)";
                    } else {
                        //当前时间大于退市时间------退市
                        ADetail = "A股(已退市)";
                    }
                }
            }
            List<StockCnInfo> cnInfos = stockCnInfos.stream().filter(x -> !ObjectUtil.isEmpty(x.getListDate())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(cnInfos)) {
                cnInfos.sort(Comparator.comparing(StockCnInfo::getListDate));
                stockCode = cnInfos.get(cnInfos.size() - 1).getStockCode();
            }
        }
        //查询是否存在 G股  上市情况
        QueryWrapper<EntityStockThkRel> thkRelQuery = new QueryWrapper<>();
        List<EntityStockThkRel> thkRels = thkRelMapper.selectList(thkRelQuery.lambda().eq(EntityStockThkRel::getEntityCode, o.getEntityCode()));
        //创建 G股 上市描述
        String GDetail = "";
        //不存在则直接返回
        //存在，则分别识别是否退市
        //A股 判断退市时间和当前时间
        if (!CollectionUtils.isEmpty(thkRels)) {
            //保存全部的上市 code
            List<String> thkCodes = new ArrayList<>();
            thkRels.stream().forEach(x -> thkCodes.add(x.getStockDqCode()));
            QueryWrapper<StockThkInfo> stockThkInfoQuery = new QueryWrapper<>();

            //查询 entityCode 下所有的G股信息
            List<StockThkInfo> stockThkInfos = stockThkMapper.selectList(stockThkInfoQuery.lambda().in(StockThkInfo::getStockDqCode, thkCodes));
            if (!CollectionUtils.isEmpty(stockThkInfos)) {
                for (int i = 0; i < stockThkInfos.size(); i++) {
                    if ("港股(存续)".equals(GDetail)) {
                        break;
                    }
                    StockThkInfo stockThkInfo = stockThkInfos.get(i);
                    //退市日期
                    String delistingDate = stockThkInfo.getDelistingDate();
                    // TODO 需要验证是否是这样判断
                    int days = TimeFormatUtil.between_days("yyyy-MM-dd", delistingDate, TimeFormatUtil.getFormartDate(new Date()));
                    if (days < 0) {
                        //当前时间小于退市时间------未退市
                        GDetail = "港股(存续)";
                    } else {
                        //当前时间大于退市时间------退市
                        GDetail = "港股(已退市)";
                    }
                }
            }
            List<StockThkInfo> thkInfos = stockThkInfos.stream().filter(x -> !ObjectUtil.isEmpty(x.getListDate())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(thkInfos)) {
                thkInfos.sort(Comparator.comparing(StockThkInfo::getListDate));
                if (ObjectUtil.isEmpty(stockCode)) {
                    stockCode = thkInfos.get(thkInfos.size() - 1).getStockCode();
                } else {
                    stockCode = stockCode + "," + thkInfos.get(thkInfos.size() - 1).getStockCode();
                }
            }
        }
        if (ObjectUtil.isEmpty(ADetail)) {
            listDetail = GDetail;
        } else {
            if (ObjectUtil.isEmpty(ADetail)) {
                listDetail = ADetail;
            } else {
                listDetail = ADetail + "," + GDetail;
            }
        }
        List<String> result = new ArrayList<>();
        result.add(listDetail);
        result.add(stockCode);
        return result;
    }

    /**
     * ****************
     * *    通用方法   *
     * ****************
     * <p>
     * 拿到后缀数字
     *
     * @param suffixLength 后缀长度
     * @param target       字符
     */
    @Override
    public Integer getSuffixNumber(Integer suffixLength, String target) {
        return Integer.parseInt(target.substring(target.length() - suffixLength));
    }

    /**
     * ****************
     * *    通用方法   *
     * ****************
     * <p>
     * 拼接 0
     *
     * @param prefixLength 前缀长度
     * @param target       字符
     * @return
     */
    @Override
    public String appendPrefix(Integer prefixLength, Integer target) {
        return String.format("%0" + prefixLength + "d", target);
    }

    /**
     * ****************
     * *    通用方法   *
     * ****************
     * <p>
     * 拼接 0
     *
     * @param prefixWord   前缀 拼接的字符
     * @param prefixLength 前缀长度
     * @param target       目标字符
     */
    @Override
    public String appendPrefixDiy(String prefixWord, Integer prefixLength, Integer target) {
        return prefixWord + String.format("%0" + prefixLength + "d", target);
    }

    /**
     * 统一社会信用代码不适用是 进行字符拼接
     * 例如 注销企业:ZX+企业德勤代码+R+自0000001开始排序。
     *
     * @param prefix
     * @param entityCode
     * @return
     */
    public String appendCreditCode(String prefix, String entityCode) {
        EntityInfo lastOneByPrefixCredit = baseMapper.findLastOneByPrefixCredit(prefix);
        String suffixString = null;
        if (lastOneByPrefixCredit != null) {
            Integer suffixNumber = this.getSuffixNumber(7, lastOneByPrefixCredit.getCreditCode());
            suffixString = this.appendPrefix(7, suffixNumber + 1);
        } else {
            suffixString = "0000001";
        }
        return prefix + entityCode + "R" + suffixString;
    }


    /**
     * 财报收数根据entityCode补充录入信息--主表
     *
     * @param entityInfo
     * @return void
     * @author 冉浩岑
     * @date 2022/10/12 9:51
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addEntityeMsg(EntityInfo entityInfo) {
        Integer id = entityInfo.getId();
        crmSupplyTaskService.completeTaskById(id);
        updateOrInsertEntityInfoByEntityCode(entityInfo);

        QueryWrapper<EntityInfo> entityQuery = new QueryWrapper<>();
        int update = entityInfoMapper.update(entityInfo, entityQuery.lambda().eq(EntityInfo::getEntityCode, entityInfo.getEntityCode()));
        if (update < 1) {
            entityInfoMapper.insert(entityInfo);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfoDetail(EntityInfoDetails entityInfoDetails) {
        //获取修改的信息 分别修改各自的信息

        //基础属性
        EntityInfo entityInfo = entityInfoDetails.getEntityInfo();
        //A股信息
        StockCnInfo stockCnInfo = entityInfoDetails.getStockCnInfo();
        //港股信息
        StockThkInfo stockThkInfo = entityInfoDetails.getStockThkInfo();
        if (!ObjectUtil.isEmpty(entityInfo) && !ObjectUtil.isEmpty(entityInfo.getEntityCode())) {
            //修改基础属性
            EntityInfo old = entityInfoMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityInfo.getEntityCode()));
            entityInfoLogsUpdatedService.insert(old.getEntityCode(),old.getEntityName(),old,entityInfo,1);
            entityInfoMapper.update(entityInfo, new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityInfo.getEntityCode()));
        }
        if (!ObjectUtil.isEmpty(stockCnInfo) && !ObjectUtil.isEmpty(stockCnInfo.getStockDqCode())) {
            //修改A故信息 -- TODO 是否修改最新的一条信息，还是都修改
            StockCnInfo old = stockCnMapper.selectOne(new QueryWrapper<StockCnInfo>().lambda().eq(StockCnInfo::getStockDqCode, stockCnInfo.getStockDqCode()));
            entityInfoLogsUpdatedService.insert(old.getStockDqCode(),old.getStockShortName(),old,entityInfo,1);
            stockCnMapper.update(stockCnInfo, new QueryWrapper<StockCnInfo>().lambda().eq(StockCnInfo::getStockDqCode, stockCnInfo.getStockDqCode()));
        }
        if (!ObjectUtil.isEmpty(stockThkInfo) && !ObjectUtil.isEmpty(stockThkInfo.getStockDqCode())) {
            //修改港股信息 -- TODO 是否修改最新的一条信息，还是都修改
            StockThkInfo old = stockThkMapper.selectOne(new QueryWrapper<StockThkInfo>().lambda().eq(StockThkInfo::getStockDqCode, stockThkInfo.getStockDqCode()));
            entityInfoLogsUpdatedService.insert(old.getStockDqCode(),old.getStockName(),old,entityInfo,1);
            stockThkMapper.update(stockThkInfo, new QueryWrapper<StockThkInfo>().lambda().eq(StockThkInfo::getStockDqCode, stockThkInfo.getStockDqCode()));
        }
    }

    @Override
    public EntityListView getListView(Integer type) {
        EntityListView entityListView = new EntityListView();
        switch (type) {
            //查询上市概览
            case 1:
                entityListView = getListViews();
                break;
            //查询发债概览
            case 2:
                entityListView = getBondsViews();
                break;
            //查询既没上市，也没发债概览
            case 3:
                entityListView = getUnBondsListViews();
                break;
            //查询金融机构概览
            case 4:
                entityListView = getFinViews();
        }
        return entityListView;
    }

    private EntityListView getFinViews() {
        EntityListView entityListView = new EntityListView();

        Long listTotle = entityInfoMapper.selectCount(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getFinance, 1));
        entityListView.setTotle(listTotle);

        return entityListView;

    }

    private EntityListView getUnBondsListViews() {
        EntityListView entityListView = new EntityListView();

        Long listTotle = entityInfoMapper.selectCount(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getList, 0).eq(EntityInfo::getIssueBonds, 0));
        entityListView.setTotle(listTotle);
        return entityListView;

    }

    private EntityListView getBondsViews() {
        EntityListView entityListView = new EntityListView();

        Long listTotle = entityInfoMapper.selectCount(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getIssueBonds, 1));
        entityListView.setTotle(listTotle);
        if (listTotle < 1) {
            return entityListView.setLive(0L).setDead(0L);
        }
        //根据时间查询发债存续企业
        Long bondLive = bondInfoMapper.selectCount(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getBondState,0));
        return entityListView.setLive(bondLive).setDead(listTotle - bondLive);
    }

    private EntityListView getListViews() {
        EntityListView entityListView = new EntityListView();

        Long listTotle = entityInfoMapper.selectCount(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getList, 1));
        entityListView.setTotle(listTotle);
        if (listTotle < 1) {
            return entityListView.setLive(0L).setDead(0L);
        }
        Date now = new Date();
        //根据时间查询A股上市存续企业
        List<String> listCnLive = entityInfoMapper.selectListCnLive(TimeFormatUtil.getFormartDate(now));
        //根据时间查询港股上市存续企业
        List<String> listThkLive = entityInfoMapper.selectListThkLive(TimeFormatUtil.getFormartDate(now));
        if (!CollectionUtils.isEmpty(listThkLive)) {
            List<String> newThkList = listThkLive.stream().filter(o -> !listCnLive.contains(o)).collect(Collectors.toList());
            listCnLive.addAll(newThkList);
        }
        Long listLive = (long) listCnLive.size();
        return entityListView.setLive(listLive).setDead(listTotle - listLive);

    }

    /**
     * 批量查询并导出excel结果
     *
     * @param file
     * @return R
     * @author penTang
     * @date 2022/10/9 16:12
     */
    @Override
    public List<ExportEntityCheckDto> checkBatchEntity(MultipartFile file, String uuid) {
        try {
            //读取excel
            List<EntityByBatchDto> entityByBatchDtos = this.getEntityAndBondInfoV(file);
            ArrayList<ExportEntityCheckDto> entityByBatchList = new ArrayList<ExportEntityCheckDto>();
            for (int i = 0; i < entityByBatchDtos.size(); i++) {
                ExportEntityCheckDto exportEntityCheckDto = new ExportEntityCheckDto();
                //添加原始数据
                exportEntityCheckDto.setEntityName(entityByBatchList.get(i).getEntityName());
                exportEntityCheckDto.setCreditCode(entityByBatchList.get(i).getCreditCode());
                //统计社会性代码进行检查
                if (Objects.equals(entityByBatchList.get(i).getCreditCode(), null) || Objects.equals(entityByBatchList.get(i).getCreditCode(), "")) {
                    exportEntityCheckDto.setCreditCodeByRecord("无法识别");
                } else {
                    //校验统一社会性代码
                    String code = entityByBatchList.get(i).getCreditCode();
                    String regx = "\\w{18}";
                    boolean matches = code.matches(regx);
                    if (!matches) {
                        exportEntityCheckDto.setCreditCodeByRecord("无法识别");
                    } else {
                        //根据统一查询数据库是否已覆盖
                        EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getCreditCode, entityByBatchList.get(i).getCreditCode()));
                        if (entityInfo != null) {
                            exportEntityCheckDto.setCreditCodeByRecord("识别成功,已覆盖主体");
                            exportEntityCheckDto.setCreditCodeByEntityName(entityInfo.getEntityName());
                            exportEntityCheckDto.setCreditCodeByEntityCode(entityInfo.getEntityCode());
                            exportEntityCheckDto.setCreditCodeByCreditCode(entityInfo.getCreditCode());
                        } else {
                            exportEntityCheckDto.setCreditCodeByRecord("识别成功,未覆盖主体");
                        }
                    }
                }
                //主体全称进行检查
                if (Objects.equals(entityByBatchList.get(i).getEntityName(), null) || Objects.equals(entityByBatchList.get(i).getEntityName(), "")) {
                    exportEntityCheckDto.setEntityNameByRecord("无法识别");
                } else {
                    //根据主体全称查询是否未覆盖
                    EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityName, entityByBatchList.get(i).getEntityName()));
                    if (entityInfo != null) {
                        exportEntityCheckDto.setEntityNameByRecord("识别成功,已覆盖主体");
                        exportEntityCheckDto.setEntityNameByEntityName(entityInfo.getEntityName());
                        exportEntityCheckDto.setEntityNameByEntityCode(entityInfo.getEntityCode());
                        exportEntityCheckDto.setEntityNameByCreditCode(entityInfo.getCreditCode());
                    } else {
                        exportEntityCheckDto.setEntityNameByRecord("识别成功,未覆盖主体");
                    }
                }
                //冲突检查(不适用情况)
                if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE1) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE1)) {
                    exportEntityCheckDto.setCreditCodeIsEntityName("不适用");
                    exportEntityCheckDto.setEndByResult("识别失败");
                } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE1) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE3)) {
                    exportEntityCheckDto.setCreditCodeIsEntityName("不适用");
                    exportEntityCheckDto.setEndByResult("未覆盖");
                } else if (exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE1) && exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE3)) {
                    exportEntityCheckDto.setCreditCodeIsEntityName("不适用");
                    exportEntityCheckDto.setEndByResult("未覆盖");
                } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE3) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE3)) {
                    exportEntityCheckDto.setCreditCodeIsEntityName("不适用");
                    exportEntityCheckDto.setEndByResult("未覆盖");
                } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE2) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE1)) {
                    exportEntityCheckDto.setCreditCodeIsEntityName("不适用");
                    exportEntityCheckDto.setEndByResult("已覆盖");
                    exportEntityCheckDto.setCreditCodeByResult(exportEntityCheckDto.getCreditCodeByCreditCode());
                    exportEntityCheckDto.setEntityCodeByResult(exportEntityCheckDto.getCreditCodeByEntityCode());
                    exportEntityCheckDto.setEntityNameByResult(exportEntityCheckDto.getCreditCodeByEntityName());
                } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE1) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE2)) {
                    exportEntityCheckDto.setCreditCodeIsEntityName("不适用");
                    exportEntityCheckDto.setEndByResult("已覆盖");
                    exportEntityCheckDto.setCreditCodeByResult(exportEntityCheckDto.getEntityNameByCreditCode());
                    exportEntityCheckDto.setEntityCodeByResult(exportEntityCheckDto.getEntityNameByEntityCode());
                    exportEntityCheckDto.setEntityNameByResult(exportEntityCheckDto.getEntityNameByEntityName());
                }
                //冲突检查(不一致或者一致无冲突)
                if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE2) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE2)) {
                    if (exportEntityCheckDto.getCreditCodeByEntityCode().equals(exportEntityCheckDto.getEntityNameByEntityCode())) {
                        exportEntityCheckDto.setCreditCodeIsEntityName("一致无冲突");
                        exportEntityCheckDto.setEndByResult("已覆盖");
                        exportEntityCheckDto.setCreditCodeByResult(exportEntityCheckDto.getEntityNameByCreditCode());
                        exportEntityCheckDto.setEntityCodeByResult(exportEntityCheckDto.getEntityNameByEntityCode());
                        exportEntityCheckDto.setEntityNameByResult(exportEntityCheckDto.getEntityNameByEntityName());
                    } else {
                        exportEntityCheckDto.setCreditCodeIsEntityName("不一致");
                        exportEntityCheckDto.setEndByResult("匹配冲突,需人工介入");
                    }
                } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE3) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE2)) {
                    exportEntityCheckDto.setCreditCodeIsEntityName("不一致");
                    exportEntityCheckDto.setEndByResult("匹配冲突,需人工介入");
                } else if (exportEntityCheckDto.getCreditCodeByRecord().equals(Common.CHECK_TYPE2) && exportEntityCheckDto.getEntityNameByRecord().equals(Common.CHECK_TYPE3)) {
                    exportEntityCheckDto.setCreditCodeIsEntityName("不一致");
                    exportEntityCheckDto.setEndByResult("匹配冲突,需人工介入");
                }
                //数据匹配结束入容器
                entityByBatchList.add(exportEntityCheckDto);
                //将当前进度入redis-并设置过期时间为一天
                double index = (i + 1) * 1.0;
                double sum = entityByBatchDtos.size() * 1.0;
                NumberFormat percentInstance = NumberFormat.getPercentInstance();
                // 设置保留几位小数，这里设置的是保留1位小数
                percentInstance.setMinimumFractionDigits(1);
                String format = percentInstance.format(index / sum);
                redisService.setCacheObject(uuid, format, 1L, TimeUnit.DAYS);
            }


            return entityByBatchList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 通过uuid查询当前的进度
     *
     * @param uuid
     * @return R
     * @author penTang
     * @date 2022/10/10 20:54
     */
    @Override
    public R getIng(String uuid) {
        return R.ok(redisService.getCacheObject(uuid));
    }

    /**
     * 读取excel文件数据
     *
     * @param file
     * @return List<EntityAndBondInfoVo>
     * @author penTang
     * @date 2022/10/9 17:32
     */
    public List<EntityByBatchDto> getEntityAndBondInfoV(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        //读取导入的excel
        if (Strings.isNullOrEmpty(fileName)) {
            throw new GlobalException("导入文件不能为空");
        }

        if (fileName.lastIndexOf(".") != -1 && !".xlsx".equals(fileName.substring(fileName.lastIndexOf(".")))) {
            throw new GlobalException("文件名格式不正确, 请使用后缀名为.XLSX的文件");
        }

        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("主体统一社会信用代码", "creditCode");
        reader.addHeaderAlias("主体全称", "entityName");


        List<EntityByBatchDto> entityByBatchDtos = reader.readAll(EntityByBatchDto.class);
        return entityByBatchDtos;
    }

    /**
     * 导出匹配的结果(excel)
     *
     * @return ExcelWriter
     * @author penTang
     * @date 2022/10/10 9:46
     */
    @Override
    public R getExcelWriter(List<ExportEntityCheckDto> entityByBatchList) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //合并一级表头

        writer.merge(0, 0, 0, 1, "原始数据", true)
                .merge(0, 0, 2, 5, "判别1-统一社会信用代码", true)
                .merge(0, 0, 6, 9, "判别二：企业全称", true)
                .merge(0, 0, 11, 14, "最终结果", true);
        writer.passCurrentRow();// 跳过当前行
        CellUtil.setCellValue(writer.getOrCreateCell(10, 0), "冲突检查", writer.getStyleSet(), true);

        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        entityByBatchList.forEach(o -> {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("主体统一社会代码", o.getCreditCode());
            map.put("主体全称", o.getEntityName());
            map.put("根据统一社会性代码识别结果", o.getCreditCodeByRecord());
            map.put("根据统一社会信用代码识别主体代码", o.getCreditCodeByEntityCode());
            map.put("根据统一社会信用代码识别主体的最新名称", o.getCreditCodeByEntityName());
            map.put("根据统一社会信用代码识别主体的根据统一社会信用代码", o.getCreditCodeByCreditCode());
            map.put("根据主体全称识别结果", o.getEntityNameByRecord());
            map.put("根据主体全称识别主体代码", o.getEntityNameByEntityCode());
            map.put("根据主体全称识别主体名称", o.getEntityNameByEntityName());
            map.put("根据主体全称识别统一社会信用代码", o.getEntityNameByCreditCode());
            map.put("根据统一社会信用代码识别主体全称结果是否一致", o.getCreditCodeIsEntityName());
            map.put("最终结果", o.getEndByResult());
            map.put("最终匹配主体代码结果", o.getEntityCodeByResult());
            map.put("最终匹配主体全称结果", o.getEntityNameByResult());
            map.put("最终统一社会信用代码结果", o.getCreditCodeByResult());
            rows.add(map);
        });
        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        // 设置自适应
        Sheet sheet = writer.getSheet();
        // 循环设置列宽
        for (int columnIndex = 0; columnIndex < writer.getColumnCount(); columnIndex++) {
            int width = sheet.getColumnWidth(columnIndex) / 256;
            // 获取最大行宽
            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);
                if (Objects.isNull(currentRow)) {
                    currentRow = sheet.createRow(rowIndex);
                }
                Cell currentCell = currentRow.getCell(columnIndex);
                if (Objects.isNull(currentCell)) {
                    continue;
                } else if (currentCell.getCellType() == CellType.STRING) {
                    int length = currentCell.getStringCellValue().getBytes().length;
                    width = width < length ? length : width;
                }
            }
            sheet.setColumnWidth(columnIndex, width * 256);
        }
        // response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //test.xlsx是弹出下载对话框的文件名，不能为中文，中文请自行编码
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + (URLEncoder.encode("最终匹配结果", "UTF-8")) + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        try (ServletOutputStream out = response.getOutputStream()) {
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();

        }
        // 关闭writer，释放内存
        writer.close();
        IoUtil.close(out);
        return R.ok("导出成功");
    }
}