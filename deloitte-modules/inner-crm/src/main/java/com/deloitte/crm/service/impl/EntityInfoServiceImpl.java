package com.deloitte.crm.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.cell.CellUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.EntityInfoResult;
import com.deloitte.crm.dto.EntityDto;
import com.deloitte.crm.dto.EntityInfoDto;
import com.deloitte.crm.dto.*;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.EntityInfoManager;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.service.IEntityNameHisService;
import com.deloitte.crm.utils.HttpUtils;
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
import java.text.SimpleDateFormat;
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

    public static final String ENTITY = "ENTITY";

    public static final String BOND = "BOND";

    private EntityInfoMapper entityInfoMapper;

    private EntityNameHisMapper nameHisMapper;

    private EntityAttrValueMapper entityAttrValueMapper;

    private EntityBondRelMapper entityBondRelMapper;

    private GovInfoMapper govInfoMapper;

    private BondInfoMapper bondInfoMapper;

    private EntityInfoManager entityInfoManager;

    private HttpUtils httpUtils;

    private EntityNameHisMapper entityNameHisMapper;

    private ICrmEntityTaskService iCrmEntityTaskService;

    private RedisService redisService;

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

        //TODO issue_bonds 是否发债 0-未发债 1-已发债
        List<EntityInfo> bonds = list().stream()
                .filter(row -> row.getIssueBonds() != null && row.getIssueBonds() == 1)
                .collect(Collectors.toList());

        //TODO finance 是否金融机构
        List<EntityInfo> finance = list().stream()
                .filter(row -> row.getFinance() != null && row.getFinance() == 1)
                .collect(Collectors.toList());

        //TODO list 是否上市 0-未上市 1-已上市
        List<EntityInfo> entityInfoList = list().stream()
                .filter(row -> row.getList() != null && row.getList() == 1)
                .collect(Collectors.toList());

        //TODO 即是上市又是发债
        List<EntityInfo> listAndBonds = list().stream()
                .filter(row -> row.getList() != null && row.getList() == 1)
                .filter(row -> row.getFinance() != null && row.getIssueBonds() == 1)
                .collect(Collectors.toList());

        //TODO !即是上市又是发债
        List<EntityInfo> notListAndBonds = list().stream()
                .filter(row -> row.getList() != null && row.getList() == 0)
                .filter(row -> row.getFinance() != null && row.getIssueBonds() == 0)
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
        EntityInfoLogs temp = new EntityInfoLogs();
        //数据装配新增基础信息
        temp.setEntityCode(entityCode)
                .setEntityName(entityInfo.getEntityName())
                .setOperName(username)
                .setRemarks("");


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

    @Transactional
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
            return R.fail("曾用名重复，请重新输入");
        }
        //获取操作用户
        String remoter = HttpUtils.getRemoter();
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
        newNameHis.setEntityType(2);
        newNameHis.setHappenDate(entity.getUpdated());
        newNameHis.setRemarks(entity.getEntityNameHisRemarks());
        newNameHis.setSource(2);
        nameHisMapper.insert(newNameHis);
        return R.ok();
    }

    @Override
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
                return R.ok("曾用名已经存在，请重新输入");
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
    public R getInfoDetail(EntityInfo entityInfo) {
        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>(entityInfo);
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(entityInfos)) {
            return R.fail("异常查询，数据为空");
        }
        if (entityInfos.size() > 1) {
            return R.fail("异常查询，唯一识别码查出多条数据");
        }
        //TODO  添加主体其余详细信息

        return R.ok(entityInfos.get(0));
    }

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
    public R getInfoList(Integer type, String param) {
        EntityInfo entityInfo = new EntityInfo();
//        企业主体类型 1、上市 2、发债 3、非上市，非发债 4、金融机构
        if (ObjectUtils.isEmpty(type)) {
            type = 1;
        }
        if (type == 1) {
            entityInfo.setList(1);
        } else if (type == 2) {
            entityInfo.setIssueBonds(1);
        } else if (type == 3) {
            entityInfo.setList(0);
            entityInfo.setIssueBonds(0);
        } else if (type == 4) {
            entityInfo.setFinance(1);
        }
        entityInfo.setEntityCode(param);
        entityInfo.setEntityName(param);
        List<EntityInfo> entityInfoList = entityInfoMapper.selectGovInfoListByTypeAndParam(entityInfo);
        if (CollectionUtils.isEmpty(entityInfoList)) {
            return R.ok();
        }
        //封装结果集
        List<Map<String, Object>> records = new ArrayList<>();
        List<Map<String, Object>> finalRecords = records;
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
        return R.ok(records);
    }

    @Transactional
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

    @Override
    public R<EntityInfoVo> validEntity(String creditCode, String entityName) {

        return null;
    }

    /**
     * 根据统一社会信用代码 查询主体信息
     * @param creditCode
     * @return
     */
    @Override
    public EntityInfo getEntityInfoByCreditCode(String creditCode) {
        return baseMapper.selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getCreditCode,creditCode));
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
        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        Integer raiseType = entityAttrDto.getRaiseType();
        Integer abs = entityAttrDto.getAbs();
        Integer coll = entityAttrDto.getColl();

        List<EntityInfo> entityInfos = entityInfoMapper.getEntityByBondType(raiseType, abs, coll);
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
                vo.getMore().forEach(entryMap -> map.put(entryMap.get("key").toString(), map.get("value")));
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
        Integer raiseType = entityAttrDto.getRaiseType();
        Integer abs = entityAttrDto.getAbs();
        Integer coll = entityAttrDto.getColl();
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 9;
        }
        Page<EntityInfoResult> pageResult = new Page<>(pageNum, pageSize);
        //封装新的结果集
        List<EntityInfoResult> resultRecords = new ArrayList<>();

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        Integer count = entityInfoMapper.getEntityCountByBondType(raiseType, abs, coll);
        pageNum = (pageNum-1) * pageSize;
        List<EntityInfo> records = entityInfoMapper.getEntityByBondTypeByPage(raiseType, abs, coll, pageNum, pageSize);
        pageResult.setTotal(count);

        records.stream().forEach(o -> {
            EntityInfoResult entityInfoResult = getEntityInfoResult(o, mapList);
            resultRecords.add(entityInfoResult);
        });
        pageResult.setRecords(resultRecords);
        return pageResult;
    }

    private EntityInfoResult getEntityInfoResult(EntityInfo o, List<Map<String, String>> mapList) {
        EntityInfoResult entityInfoResult = new EntityInfoResult();
        entityInfoResult.setEntityInfo(o);
        List<String> header = new ArrayList<>();
        List<String> values = new ArrayList<>();
        if (!CollectionUtils.isEmpty(mapList)) {
            List<Map<String, Object>> more = new ArrayList<>();
            for (Map<String, String> map : mapList) {
                QueryWrapper<EntityAttrValue> valueQuer = new QueryWrapper<>();
                EntityAttrValue attrValue = entityAttrValueMapper.selectOne(valueQuer.lambda()
                        .eq(EntityAttrValue::getAttrId, map.get(EntityUtils.MORE_ENTITY_KPI_ID))
                        .eq(EntityAttrValue::getEntityCode, o.getEntityCode()));
                //新增指标栏
                Map<String, Object> moreMap = new HashMap<>();
                moreMap.put(EntityUtils.MORE_ENTITY_KPI_KEY, map.get(EntityUtils.MORE_ENTITY_KPI_NAME));
                header.add(map.get(EntityUtils.MORE_ENTITY_KPI_NAME));
                if (ObjectUtils.isEmpty(attrValue)) {
                    values.add(null);
                    moreMap.put(EntityUtils.MORE_ENTITY_KPI_VALUE, null);
                } else {
                    values.add(attrValue.getValue());
                    moreMap.put(EntityUtils.MORE_ENTITY_KPI_VALUE, attrValue.getValue());
                }
                more.add(moreMap);
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
    public Map<String, Object> getResultMap(EntityInfo entityInfo, Map<String, List<EntityNameHis>> map, Integer type) {
        Map<String, Object> resultMap = new HashMap();
        if (null != entityInfo) {
            resultMap = JSON.parseObject(JSON.toJSONString(entityInfo), new TypeReference<Map<String, String>>() {
            });
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            resultMap.put("updated", sdf2.format(entityInfo.getUpdated()));
            resultMap.put("created", sdf2.format(entityInfo.getCreated()));
            try {
                Integer count = 0;
                if (!map.isEmpty()) {
                    List<EntityNameHis> nameHisList = map.get(entityInfo.getEntityCode());
                    if (!CollectionUtils.isEmpty(nameHisList)) {
                        count = nameHisList.size();
                    }
                }
                resultMap.put(EntityUtils.NAME_USED_NUM, count);

            } catch (Exception e) {
                return resultMap;
            }
        }
        return resultMap;
    }

    private List<Map<String, Object>> getFinSpecial(List<Map<String, Object>> record, List<String> codeList) {
        QueryWrapper<EntityAttrValue> valueQueryWrapper = new QueryWrapper<>();

        List<EntityAttrValue> attrValueList = entityAttrValueMapper.selectList(valueQueryWrapper.lambda()
                .eq(EntityAttrValue::getAttrId, 656)
                .in(EntityAttrValue::getEntityCode, codeList)
        );
        if (CollectionUtils.isEmpty(attrValueList)) {
            return record;
        }
        for (int i = 0; i < record.size(); i++) {

            Map<String, Object> result = record.get(i);
            String entityCode = result.get("entityCode").toString();
            List<EntityAttrValue> valueList = attrValueList.stream().collect(Collectors.groupingBy(EntityAttrValue::getEntityCode)).get(entityCode);
            if (CollectionUtils.isEmpty(valueList)) {
                record.set(i, result);
                continue;
            }
            //存续债明细 TODO
            List<String> liveBondDetail = new ArrayList<>();
            valueList.stream().forEach(o -> liveBondDetail.add(o.getValue()));
            //细分行业
            result.put("industry", liveBondDetail);
            record.set(i, result);
        }
        return record;
    }

    /**
     * 查询发债特定列----存续债数量 存续债明细  2
     */
    private List<Map<String, Object>> getIssSpecial(List<Map<String, Object>> record, List<String> codeList) {
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
            Map<String, Object> result = record.get(i);
            String entityCode = result.get("entityCode").toString();
            List<EntityBondRel> bondRels = entityBondRels.stream().collect(Collectors.groupingBy(EntityBondRel::getEntityCode)).get(entityCode);

            //债券存续数量
            result.put("liveBond", liveBond);
            if (CollectionUtils.isEmpty(bondRels)) {
                record.set(i, result);
                continue;
            }
            liveBond = bondRels.size();
            //存续债明细 TODO
            List<String> liveBondDetail = new ArrayList<>();
            bondRels.stream().forEach(o -> liveBondDetail.add(o.getBdCode()));
            //债券存续数量
            result.put("liveBond", liveBond);
            result.put("liveBondDetail", liveBondDetail);
            record.set(i, result);
        }
        return record;
    }

    /**
     * 查询上市特定列----证券代码，上市日期，退市日期，更新记录  1
     */
    private List<Map<String, Object>> getListSpecial(List<Map<String, Object>> record, List<String> codeList) {

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
            Map<String, Object> result = record.get(i);
            String o = result.get("entityCode").toString();
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
            result.put("stockCode", stockCodeList);
            result.put("listDate", stockDateList);
            result.put("downDate", stockdownDateList);
            record.set(i, result);
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
        bondVo.setTransactionCode(
                entityAttrValueMapper.selectOne(new QueryWrapper<EntityAttrValue>()
                        .lambda().eq(EntityAttrValue::getEntityCode, bondInfo.getBondCode())
                        .eq(EntityAttrValue::getAttrId, Common.TRANSACTION_CODE_ID)).getValue()
        );
        //债券全称
        bondVo.setFullName(
                entityAttrValueMapper.selectOne(new QueryWrapper<EntityAttrValue>()
                        .lambda().eq(EntityAttrValue::getEntityCode, bondInfo.getBondCode())
                        .eq(EntityAttrValue::getAttrId, Common.BOND_NAME_ID)).getValue()
        );
        //债券简称
        bondVo.setShortName(bondInfo.getBondShortName());
        //TODO 存续状态
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
    public R<List<TargetEntityBondsVo>> findBondOrEntity(String name, String keyword,Integer pageNum,Integer pageSize) {
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
                return R.ok(res);
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
                return R.ok(rest);
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
     * 覆盖情况快速查询
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
            return R.fail("未输入页码");
        }
        if (ObjectUtil.isEmpty(pageSize)) {
            pageSize = 9;
        }
        //创建分页对象
        Page<EntityInfo> pageInfo = new Page<>(pageNum, pageSize);

        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();

        //创建分页结果集
        List<EntityInfoValueResult> resultList = new ArrayList<>();
        Page<EntityInfoValueResult> pageResult = new Page<>();

        Page<EntityInfo> page = entityInfoMapper.selectPage(pageInfo, queryWrapper.lambda()
                .like(EntityInfo::getEntityCode, param)
                .or().like(EntityInfo::getEntityName, param)
                .or().like(EntityInfo::getCreditCode, param)
        );
        //新的分页结果赋值
        pageResult.setTotal(page.getTotal()).setSize(page.getSize());

        List<EntityInfo> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            pageResult.setRecords(null);
        } else {
            records.stream().forEach(o -> {
                //封装后的新结果
                EntityInfoValueResult result = new EntityInfoValueResult();
                result.setEntityInfo(o);
                String entityCode = o.getEntityCode();
                //获取上市情况
                String listDetail=getListDetail(o);
                //获取发债情况


                result.setListDetail(listDetail);
                resultList.add(result);
            });
            pageResult.setRecords(resultList);
        }
        return R.ok(pageResult);
    }
    //获取上市情况
    private String getListDetail(EntityInfo o) {
        String listDetail="";

        QueryWrapper<EntityAttrValue> valueQuery = new QueryWrapper<>();
        List<EntityAttrValue> attrValueListA = entityAttrValueMapper.selectList(valueQuery.lambda()
                .eq(EntityAttrValue::getEntityCode, o.getEntityCode())
                .eq(EntityAttrValue::getAttrId, 25)
        );
        List<EntityAttrValue> attrValueListG = entityAttrValueMapper.selectList(valueQuery.lambda()
                .eq(EntityAttrValue::getEntityCode, o.getEntityCode())
                .eq(EntityAttrValue::getAttrId, 44)
        );
        //                25		A股上市状态		1、存续 2、已退市 3、未曾A股上市
        if (!CollectionUtils.isEmpty(attrValueListA)){
            String value = attrValueListA.get(0).getValue();
            if ("1".equals(value)){
                listDetail=listDetail+"A股(存续)";
            }else if("2".equals(value)){
                listDetail=listDetail+"A股(已退市)";
            }
        }
        //                44		港股上市状态		1、存续 2、已退市
        if (!CollectionUtils.isEmpty(attrValueListG)){
            String value = attrValueListG.get(0).getValue();
            if ("1".equals(value)){
                if (ObjectUtils.isEmpty(listDetail)){
                    listDetail="B股";
                }else {
                    listDetail=listDetail+",B股";
                }
            }
        }
        return listDetail;
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
     *   ****************
     *   *    通用方法   *
     *   ****************
     *
     * 拼接 0
     * @param prefixWord 前缀 拼接的字符
     * @param prefixLength 前缀长度
     * @param target 目标字符
     */
    @Override
    public String appendPrefixDiy(String prefixWord, Integer prefixLength, Integer target) {
        return prefixWord+String.format("%0"+prefixLength,target);
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
    public void addEntityeMsg(EntityInfo entityInfo) {
        QueryWrapper<EntityInfo> entityQuery = new QueryWrapper<>();
        int update = entityInfoMapper.update(entityInfo, entityQuery.lambda().eq(EntityInfo::getEntityCode, entityInfo.getEntityCode()));
        if (update<1){
            entityInfoMapper.insert(entityInfo);
        }
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