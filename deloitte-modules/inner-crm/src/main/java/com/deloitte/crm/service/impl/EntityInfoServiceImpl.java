package com.deloitte.crm.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.bean.BeanUtils;
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
import com.deloitte.crm.dto.EntitySupplyBack;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.EntityInfoManager;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.service.IEntityNameHisService;
import com.deloitte.crm.utils.HttpUtils;
import com.deloitte.crm.vo.BondVo;
import com.deloitte.crm.vo.EntityInfoVo;
import com.deloitte.crm.vo.EntityVo;
import com.deloitte.crm.vo.TargetEntityBondsVo;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
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

    private EntityInfoMapper entityInfoMapper;

    private EntityNameHisMapper nameHisMapper;

    private EntityAttrValueMapper entityAttrValueMapper;

    private EntityBondRelMapper entityBondRelMapper;

    @Autowired
    private GovInfoMapper govInfoMapper;

    private BondInfoMapper bondInfoMapper;

    private EntityInfoManager entityInfoManager ;

    @Autowired
    private HttpUtils httpUtils;

    private EntityNameHisMapper entityNameHisMapper;

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

    private final String IB = "IB";

    private final String ZERO = "0";

    private final Integer CODE_NUMBER = 6;

    /**
     * 新增【请填写功能名称】
     *
     * @param entityDto 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityInfo(EntityDto entityDto) {
        EntityInfo entityInfo = new EntityInfo();
        BeanUtils.copyBeanProp(entityInfo, entityDto);

        // 生成entity_code 那么将该值的 用 IB+0..+id  例 IB000001
        baseMapper.insert(entityInfo);
        Integer id = entityInfo.getId();
        StringBuilder sb = new StringBuilder(IB);
        for (int j = 0; j < CODE_NUMBER - String.valueOf(id).length(); j++) {
            sb.append(ZERO);
        }
        entityInfo.setEntityCode(sb.toString() + id);

        UpdateWrapper<EntityInfo> wrapper = new UpdateWrapper<>();
        wrapper.lambda()
                .eq(EntityInfo::getId, id)
                .set(EntityInfo::getEntityCode, entityInfo.getEntityCode());

        // 新增曾用名 entity_name_his
        EntityNameHis entityNameHis = new EntityNameHis();
        //1-企业主体
        entityNameHis.setEntityType(1);
        entityNameHis.setDqCode(entityInfo.getEntityCode());
        entityNameHis.setOldName(entityDto.getOldName());
        //1-曾用名为自动生曾
        entityNameHis.setSource(1);
        entityNameHis.setCreater(SecurityUtils.getUsername());
        entityNameHis.setCreated(new Date());
        iEntityNameHisService.insertEntityNameHis(entityNameHis);

        return entityInfoMapper.update(entityInfo, wrapper);
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
     * 传入社会信用代码于企业名称
     * => 存在该社会信用代码 返回 比较信息为 false
     * ==> 前端跳转调用人工对比信息，并确认
     * <p>
     * => 不存在社会信用代码 但存在相同企业名称 返回 比较信息 false
     * ==> 前端跳转调用人工对比信息，并确认
     * <p>
     * => 不存在社会信用代码 也不存在相同企业名称 返回 比较信息 true
     * ==> 确认新增主体 生成企业主体德勤代码、统一社会信用代码相关字段
     *
     * @param creditCode 传入 企业统一社会信用代码
     * @param entityName 传入 企业名称
     * @return 比较信息结果
     * @author 正杰
     * @date 2022/9/22
     */
    @Override
    public R<EntityInfoVo> validEntity(String creditCode, String entityName) {
        // 校验数据库是否存在该主体
        EntityInfo entityByCode = baseMapper.selectOne(new QueryWrapper<EntityInfo>()
                .lambda().eq(EntityInfo::getCreditCode, creditCode));
        EntityInfo entityByName = baseMapper.selectOne(new QueryWrapper<EntityInfo>()
                .lambda().eq(EntityInfo::getEntityName, entityName));
        //库内无该社会信用代码
        if (entityByCode == null) {
            //库内无该主体 是新增
            if (entityByName == null) {
                return R.ok(new EntityInfoVo().setMsg(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo()));
                //库内存在该主体 但是不存在该社会信用代码
            } else {
                return R.ok(new EntityInfoVo()
                        .setEntityInfo(entityByName)
                        .setBo(BadInfo.GET)
                        .setMsg(BadInfo.EXITS_ENTITY_NAME.getInfo()));
            }
            //库内存在该主体 但是主体名称不同
        } else if (entityByName == null) {
            return R.ok(new EntityInfoVo()
                    .setEntityInfo(entityByCode)
                    .setBo(BadInfo.GET)
                    .setMsg(BadInfo.EXITS_ENTITY_DIFFERENT_NAME.getInfo()));
        }
        //库内已存在该主体
        return R.ok(new EntityInfoVo()
                .setEntityInfo(entityByCode)
                .setBo(BadInfo.GET)
                .setMsg(BadInfo.EXITS_ENTITY_CODE.getInfo()));
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
        return R.ok(entityInfoManager.updateEntityName(entity,entityNewName,remarks));
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

        if (type == 1) {
            entityInfo.setList(1);
        } else if (type == 2) {
            entityInfo.setIssueBonds(1);
        } else if (type == 3) {
            entityInfo.setList(0);
            entityInfo.setList(0);
        } else if (type == 4) {
            entityInfo.setFinance(1);
        }
        entityInfo.setEntityCode(param);
        entityInfo.setEntityName(param);
        List<EntityInfo> entityInfoList = entityInfoMapper.selectGovInfoListByTypeAndParam(entityInfo);
        //封装结果集
        List<Map<String, Object>> records = new ArrayList<>();
        //查出所有的曾用名
        QueryWrapper<EntityNameHis> hisQuery = new QueryWrapper<>();
        List<EntityNameHis> nameHisList = nameHisMapper.selectList(hisQuery.lambda().eq(EntityNameHis::getEntityType, 1));
        Map<String, List<EntityNameHis>> hisNameListMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(nameHisList)) {
            hisNameListMap = nameHisList.stream().collect(Collectors.groupingBy(EntityNameHis::getDqCode));
        }
        Map<String, List<EntityNameHis>> finalHisNameListMap = hisNameListMap;
        entityInfoList.stream().forEach(o -> {
            records.add(getResultMap(o, finalHisNameListMap));
        });
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

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(queryWrapper);

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

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        Page<EntityInfo> pageInfo = new Page<>(pageNum, pageSize);
        QueryWrapper<EntityInfo> queryWrapper = new QueryWrapper<>();
        Page<EntityInfo> entityInfoPage = entityInfoMapper.selectPage(pageInfo, queryWrapper);

        //查询分页数据集
        Page<EntityInfoResult> pageResult = new Page<>();
        pageResult.setTotal(entityInfoPage.getTotal()).setPages(entityInfoPage.getPages()).setCurrent(entityInfoPage.getCurrent());

        //封装新的结果集
        List<EntityInfoResult> resultRecords = new ArrayList<>();
        //添加指标栏位
        List<EntityInfo> records = entityInfoPage.getRecords();

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
    public Map<String, Object> getResultMap(EntityInfo entityInfo, Map<String, List<EntityNameHis>> map) {
        Map<String, Object> resultMap = new HashMap();
        if (null != entityInfo) {
            resultMap = JSON.parseObject(JSON.toJSONString(entityInfo), new TypeReference<Map<String, String>>() {
            });
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

    public static final String ENTITY = "ENTITY";

    public static final String BOND = "BOND";

    public static final String ENTITY_CODE = "ENTITY_CODE";

    public static final String BOND_CODE = "BOND_CODE";

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
    public R<List<TargetEntityBondsVo>> findBondOrEntity(String name, String keyword) {
        //模糊匹配 查询主体||债券信息
        switch (keyword) {
            //模糊匹配主体名
            case ENTITY:
                List<TargetEntityBondsVo> res = new ArrayList<>();
                //模糊匹配后的主体list
                List<EntityInfo> entityInfos = baseMapper.selectList(new QueryWrapper<EntityInfo>()
                        .lambda().like(EntityInfo::getEntityName, name));
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
                entityAttrs = entityAttrValueMapper.matchingNameByBondName(name);
                //模糊匹配短名 债券list
                List<BondInfo> bondInfos = bondInfoMapper.selectList(new QueryWrapper<BondInfo>().lambda()
                        .like(BondInfo::getBondShortName, name));
                if (entityAttrs.size() == 0 && bondInfos.size() == 0) {
                    return R.ok(null, BadInfo.VALID_EMPTY_TARGET.getInfo());
                }

                Map<String, String> collect = bondInfos.stream().collect(Collectors.toMap(BondInfo::getBondCode, BondInfo::getBondShortName));
                List<EntityAttrValue> targetList = entityAttrs.stream().filter(row -> !collect.containsKey(row.getEntityCode())).collect(Collectors.toList());

                targetList.forEach(row -> {
                    BondInfo bondInfo = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda()
                            .eq(BondInfo::getBondCode, row.getEntityCode()));
                    bondInfos.add(bondInfo);
                });
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
    public R findRelationEntityOrBond(Integer id, String keyword) {
        List<TargetEntityBondsVo> result = new ArrayList<>();
        switch (keyword){
            case ENTITY:
                List<EntityBondRel> entityBondRels = entityBondRelMapper.selectList(new QueryWrapper<EntityBondRel>()
                        .lambda().eq(EntityBondRel::getId, id));
                entityBondRels.forEach(row->{
                    BondInfo bondInfo = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda()
                            .eq(BondInfo::getBondCode, row.getBdCode()));
                    result.add(this.matchingBondInfo(bondInfo));
                });
                return R.ok(result);
            case BOND:
                List<EntityBondRel> entityBondRels1 = entityBondRelMapper.selectList(new QueryWrapper<EntityBondRel>().lambda()
                        .eq(EntityBondRel::getId, id));
                entityBondRels1.forEach(item->{
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
        Long count = entityInfoMapper.selectCount(new QueryWrapper<>());
        return null;
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
     * @author 正杰
     * @date 2022/9/28
     * @param creditCode
     * @return
     */
    @Override
    public R<EntityInfoVo> checkCreditCode(String creditCode) {
        List<EntityInfo> entityInfos = entityInfoMapper.selectList(new QueryWrapper<EntityInfo>().lambda()
                .eq(EntityInfo::getCreditCode, creditCode));
        if(entityInfos.size()==0){return R.ok(new EntityInfoVo().setBo(true)
                    .setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));}
        return R.ok(new EntityInfoVo().setBo(false)
                .setMsg(BadInfo.EXITS_ENTITY_CODE.getInfo())
                .setEntityInfo(entityInfos.get(0)));
    }

    /**
     * 校验主体名称是否存在
     * @author 正杰
     * @date 2022/9/28
     * @param entityName
     * @return R
     */
    @Override
    public R<EntityInfoVo> checkEntityName(String entityName) {
        List<EntityInfo> entName = entityInfoMapper.selectList(new QueryWrapper<EntityInfo>().lambda()
                .eq(EntityInfo::getEntityName, entityName));
        if(entName.size()!=0){return R.ok(new EntityInfoVo()
                .setBo(false).setEntityInfo(entName.get(0)));}
        return R.ok(new EntityInfoVo()
                .setBo(true).setMsg(SuccessInfo.EMPTY_ENTITY_CODE.getInfo()));
    }

}