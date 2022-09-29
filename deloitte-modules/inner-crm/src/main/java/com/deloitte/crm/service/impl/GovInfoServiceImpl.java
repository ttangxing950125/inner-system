package com.deloitte.crm.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.constants.EntityUtils;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityNameHis;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.GovInfoResult;
import com.deloitte.crm.dto.GovInfoDto;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.mapper.EntityNameHisMapper;
import com.deloitte.crm.mapper.GovInfoMapper;
import com.deloitte.crm.service.IGovInfoService;
import com.deloitte.crm.utils.HttpUtils;
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

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class GovInfoServiceImpl extends ServiceImpl<GovInfoMapper, GovInfo> implements IGovInfoService {
    @Resource
    private GovInfoMapper govInfoMapper;

    @Autowired
    private EntityAttrValueMapper entityAttrValueMapper;

    @Resource
    private EntityNameHisMapper nameHisMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public GovInfo selectGovInfoById(Long id) {
        return govInfoMapper.selectGovInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param govInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<GovInfo> selectGovInfoList(GovInfo govInfo) {
        return govInfoMapper.selectGovInfoList(govInfo);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param govInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertGovInfo(GovInfo govInfo) {
        return govInfoMapper.insertGovInfo(govInfo);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param govInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateGovInfo(GovInfo govInfo) {
        return govInfoMapper.updateGovInfo(govInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteGovInfoByIds(Long[] ids) {
        return govInfoMapper.deleteGovInfoByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteGovInfoById(Long id) {
        return govInfoMapper.deleteGovInfoById(id);
    }

    @Transactional
    @Override
    public R updateInfoList(List<GovInfo> list) {
        list.stream().forEach(o -> {
            GovInfo govInfo = govInfoMapper.selectById(o.getId());
            govInfoMapper.updateById(o);
            //修改政府主体名称时，需要添加曾用名
            if (!ObjectUtils.isEmpty(o.getGovName())) {
                String oldName = govInfo.getGovName();
                GovInfo addOldName = new GovInfo();
                addOldName.setId(o.getId());
                addOldName.setGovNameHis(oldName);
                addOldName.setEntityNameHisRemarks(o.getEntityNameHisRemarks());
                addOldName(addOldName);
            }
            //修改政府主体代码时，需要修改主体历史表中的政府主体代码
            if (!ObjectUtils.isEmpty(o.getDqGovCode())) {
                String oldDqCode = govInfo.getDqGovCode();
                QueryWrapper<EntityNameHis> wrapper = new QueryWrapper<>();
                EntityNameHis nameHis = new EntityNameHis();
                nameHis.setDqCode(o.getDqGovCode());
                nameHisMapper.update(nameHis, wrapper.lambda().eq(EntityNameHis::getDqCode, oldDqCode));
            }
        });
        return R.ok(list.size());
    }

    @Override
    public R getInfoDetail(GovInfo govInfo) {
        QueryWrapper<GovInfo> queryWrapper = new QueryWrapper<>(govInfo);
        List<GovInfo> govInfos = govInfoMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(govInfos)) {
            return R.fail("异常查询，数据为空");
        }
        if (govInfos.size() > 1) {
            return R.fail("异常查询，唯一识别码查出多条数据");
        }
        //TODO  查询上级行政code
        String preGovCode = govInfos.get(0).getPreGovCode();
        queryWrapper.clear();
        Map<String, Object> result = new HashMap<>();
        result.put("govInfo", govInfos.get(0));
        if (ObjectUtils.isEmpty(preGovCode)) {
            return R.ok(result);
        }
        GovInfo preGov = govInfoMapper.selectOne(queryWrapper.lambda().eq(GovInfo::getDqGovCode, preGovCode));
        result.put("preGov", preGov);
        return R.ok(result);
    }


    /**
     * 统计政府信息
     *
     * @return GovInfoDto
     * @author penTang
     * @date 2022/9/22 23:21
     */

    @Override
    public GovInfoDto getGovInfo() {
        List<GovInfo> list = this.list();
        GovInfoDto govInfoDto = new GovInfoDto();
//TODO gov_level_big 是否 省  1-是
        List<GovInfo> province = list().stream()
                .filter(row -> row.getGovLevelBig() != null && row.getGovLevelBig() == 1)
                .collect(Collectors.toList());

//TODO gov_level_big 是否 市  2-是
        List<GovInfo> city = list().stream()
                .filter(row -> row.getGovLevelBig() != null && row.getGovLevelBig() == 2)
                .collect(Collectors.toList());

//TODO gov_level_big 是否 县  3-是
        List<GovInfo> county = list().stream()
                .filter(row -> row.getGovLevelBig() != null && row.getGovLevelBig() == 3)
                .collect(Collectors.toList());

//TODO gov_level_big 是否 经开  4-是
        List<GovInfo> open = list().stream()
                .filter(row -> row.getGovLevelBig() != null && row.getGovLevelBig() == 4)

                .collect(Collectors.toList());

        govInfoDto.setProvince(province.size());
        govInfoDto.setCity(city.size());
        govInfoDto.setCounty(county.size());
        govInfoDto.setOpen(open.size());
        govInfoDto.setGovSum(list.size());
        return govInfoDto;
    }

    @Override
    public R getInfoList(Integer type, String param) {
        GovInfo govInfo = new GovInfo();
        govInfo.setGovType(type);
        govInfo.setDqGovCode(param);
        govInfo.setGovName(param);
        List<GovInfo> govInfoList = govInfoMapper.selectGovInfoListByTypeAndParam(govInfo);
        //封装结果集
        List<Map<String, Object>> records = new ArrayList<>();
        //查出所有的曾用名
        QueryWrapper<EntityNameHis> hisQuery = new QueryWrapper<>();
        List<EntityNameHis> nameHisList = nameHisMapper.selectList(hisQuery.lambda().eq(EntityNameHis::getEntityType, 2));
        Map<String, List<EntityNameHis>> hisNameListMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(nameHisList)) {
            hisNameListMap = nameHisList.stream().collect(Collectors.groupingBy(EntityNameHis::getDqCode));
        }
        Map<String, List<EntityNameHis>> finalHisNameListMap = hisNameListMap;
        govInfoList.stream().forEach(o -> {
            records.add(getResultMap(o, finalHisNameListMap));
        });
        return R.ok(records);
    }

    @Transactional
    @Override
    public R addOldName(GovInfo gov) {
        //获取操作用户
        String remoter = HttpUtils.getRemoter();

        GovInfo govInfo = govInfoMapper.selectById(gov.getId());

        String govCode = govInfo.getDqGovCode();
        String nameHis = gov.getGovNameHis();
        QueryWrapper<EntityNameHis> queryWrapper = new QueryWrapper<>();
        Long aLong = nameHisMapper.selectCount(queryWrapper.lambda()
                .eq(EntityNameHis::getDqCode, govCode)
                .eq(EntityNameHis::getOldName, nameHis));
        if (aLong > 0) {
            return R.fail("曾用名重复，请重新输入");
        }

        //修改曾用名记录
        String govNameHis = govInfo.getGovNameHis();
        if (ObjectUtils.isEmpty(govNameHis)) {
            govInfo.setGovNameHis(nameHis);
        } else {
            govInfo.setGovNameHis(govNameHis + EntityUtils.NAME_USED_SIGN + nameHis);
        }
        String nameHisRemarks = gov.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(nameHisRemarks)) {
            govInfo.setEntityNameHisRemarks(new Date() + remoter + gov.getEntityNameHisRemarks());
        } else {
            govInfo.setEntityNameHisRemarks(govNameHis + EntityUtils.NAME_USED_REMARK_SIGN + new Date() + remoter + gov.getEntityNameHisRemarks());
        }
        govInfoMapper.updateById(govInfo);

        //插入曾用名记录表
        EntityNameHis entityNameHis = new EntityNameHis();
        entityNameHis.setDqCode(govInfo.getDqGovCode());
        entityNameHis.setOldName(gov.getGovNameHis());
        entityNameHis.setEntityType(2);
        entityNameHis.setHappenDate(gov.getUpdated());
        entityNameHis.setRemarks(gov.getEntityNameHisRemarks());
        entityNameHis.setSource(2);
        nameHisMapper.insert(entityNameHis);
        return R.ok();
    }

    @Override
    public R checkGov(GovInfo govInfo) {
        QueryWrapper<GovInfo> queryWrapper = new QueryWrapper(govInfo);
        return R.ok(govInfoMapper.selectList(queryWrapper));
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
     * @return List<GovInfoResult>
     * @author 冉浩岑
     * @date 2022/9/26 00:35
     */
    public List<GovInfoResult> getListEntityAll(EntityAttrByDto entityAttrDto) {

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        QueryWrapper<GovInfo> queryWrapper = new QueryWrapper<>();
        List<GovInfo> govInfos = govInfoMapper.selectList(queryWrapper);

        //封装新的结果集
        List<GovInfoResult> resultRecords = new ArrayList<>();

        govInfos.stream().forEach(o -> {
            GovInfoResult govInfoResult = getGovInfoResult(o, mapList);
            resultRecords.add(govInfoResult);
        });
        return resultRecords;
    }

    /**
     * 导出政府主体数据
     *
     * @param entityAttrDto
     * @return R
     * @author penTang
     * @date 2022/9/26 18:58
     */
    @Override
    public void ExportEntityGov(EntityAttrByDto entityAttrDto) {
        List<GovInfoResult> listEntityAll = this.getListEntityAll(entityAttrDto);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        AtomicInteger serialNumber = new AtomicInteger();
        listEntityAll.forEach(vo -> {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            GovInfo info = vo.getGovInfo();
            map.put("序号", serialNumber.incrementAndGet());
            map.put("德勤主体代码", info.getDqGovCode());
            map.put("主体名称", info.getGovName());
            map.put("续存状态", info.getStatus());
            map.put("统一社会性代码", info.getGovCode());
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
            response.setHeader("Content-Disposition", "attachment;filename=" + (URLEncoder.encode("政府主体", "UTF-8")) + ".xlsx");
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
     * @return Page<GovInfoResult>
     * @author 冉浩岑
     * @date 2022/9/25 17:05
     */
    public Page<GovInfoResult> getListEntityPage(EntityAttrByDto entityAttrDto) {
        Integer pageNum = entityAttrDto.getPageNum();
        Integer pageSize = entityAttrDto.getPageSize();

        List<Map<String, String>> mapList = entityAttrDto.getMapList();

        Page<GovInfo> pageInfo = new Page<>(pageNum, pageSize);
        QueryWrapper<GovInfo> queryWrapper = new QueryWrapper<>();
        Page<GovInfo> entityInfoPage = govInfoMapper.selectPage(pageInfo, queryWrapper);

        //查询分页数据集
//        Page<Map<String, Object>> pageResult = new Page<>();
        Page<GovInfoResult> pageResult = new Page<>();
        pageResult.setTotal(entityInfoPage.getTotal()).setPages(entityInfoPage.getPages()).setCurrent(entityInfoPage.getCurrent());

        //封装新的结果集
        List<GovInfoResult> resultRecords = new ArrayList<>();
        //添加指标栏位
        List<GovInfo> records = entityInfoPage.getRecords();

        records.stream().forEach(o -> {
            GovInfoResult govInfoResult = getGovInfoResult(o, mapList);
            resultRecords.add(govInfoResult);
        });
        pageResult.setRecords(resultRecords);
        return pageResult;
    }

    private GovInfoResult getGovInfoResult(GovInfo o, List<Map<String, String>> mapList) {
        GovInfoResult govInfoResult = new GovInfoResult();
        govInfoResult.setGovInfo(o);
        List<String> header = new ArrayList<>();
        List<String> values = new ArrayList<>();
        if (!CollectionUtils.isEmpty(mapList)) {
            List<Map<String, Object>> more = new ArrayList<>();
            for (Map<String, String> map : mapList) {
                QueryWrapper<EntityAttrValue> valueQuer = new QueryWrapper<>();
                EntityAttrValue attrValue = entityAttrValueMapper.selectOne(valueQuer.lambda()
                        .eq(EntityAttrValue::getAttrId, map.get(MORE_ENTITY_KPI_ID))
                        .eq(EntityAttrValue::getEntityCode, o.getDqGovCode()));
                //新增指标栏
                Map<String, Object> moreMap = new HashMap<>();
                moreMap.put(MORE_ENTITY_KPI_KEY, map.get(MORE_ENTITY_KPI_NAME));
                header.add(map.get(MORE_ENTITY_KPI_NAME));
                if (ObjectUtils.isEmpty(attrValue)) {
                    values.add(null);
                    moreMap.put(MORE_ENTITY_KPI_VALUE, null);
                } else {
                    values.add(attrValue.getValue());
                    moreMap.put(MORE_ENTITY_KPI_VALUE, attrValue.getValue());
                }
                more.add(moreMap);

            }
            govInfoResult.setMore(more).setHeader(header).setValues(values);
        }
        return govInfoResult;
    }

    @Transactional
    @Override
    public R updateOldName(String dqCode, String oldName, String newOldName, String status) {
        //根据dqCode查询主体表
        QueryWrapper<GovInfo> infoQuery = new QueryWrapper<>();
        GovInfo govInfo = govInfoMapper.selectOne(infoQuery.lambda().eq(GovInfo::getDqGovCode, dqCode));
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
            govInfo.setGovNameHis(govInfo.getGovNameHis().replaceAll(oldName, newOldName));
            govInfo.setEntityNameHisRemarks(govInfo.getEntityNameHisRemarks().replaceAll(oldName, newOldName));
            govInfoMapper.updateById(govInfo);
            //修改曾用名表中的数据
            nameHis.setOldName(newOldName);
            nameHisMapper.updateById(nameHis);
            return R.ok();
        }

        //修改主体表中的数据
        govInfo.setGovNameHis(govInfo.getGovNameHis().replaceAll(oldName, ""));
        govInfo.setEntityNameHisRemarks(govInfo.getEntityNameHisRemarks().replaceAll(oldName, newOldName));
        String remarks = govInfo.getEntityNameHisRemarks();
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
        govInfo.setEntityNameHisRemarks(newRemarks);
        govInfoMapper.updateById(govInfo);

        //修改曾用名表中的数据
        nameHis.setStatus(EntityUtils.INVALID);
        nameHisMapper.updateById(nameHis);
        return R.ok();
    }

    @Override
    public Map<String, Object> getOverview() {
        QueryWrapper<GovInfo> query = new QueryWrapper<>();
        Long count = govInfoMapper.selectCount(query);
        Long aLong = govInfoMapper.selectCount(query.lambda().eq(GovInfo::getInvalid, 0));
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("invalid", aLong);
        result.put("unInvalid", count - aLong);
        return result;
    }

    @Override
    public Map<String, Object> getOverviewByGroup() {
        Long count = govInfoMapper.selectCount(new QueryWrapper<>());
//        经开区为“GVA”+000001开始排序
        Integer JK = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_JK_CODE).size();
//        高新区为“GVB”+000001开始排序
        Integer GX = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_GX_CODE).size();
//        新区为“GVC”+000001开始排序
        Integer XQ = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_XQ_CODE).size();
//        其他类型区域暂以“GVZ”+000001开始排序
        Integer QT = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_QT_CODE).size();

//        地级政府为“GV+官方行政代码”
        List<GovInfo> govInfosList = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_PRIVINCE_CODE);
        QueryWrapper<GovInfo> query = new QueryWrapper<>();
//        省级
        List<GovInfo> provinceInfos = govInfoMapper.selectList(query.lambda().eq(GovInfo::getGovLevelBig, 1));
        query.clear();
        Integer province = provinceInfos.size();
//        市
        List<String> dqCodeList = new ArrayList<>();
        Integer city = 0;
        if (!CollectionUtils.isEmpty(provinceInfos)) {
            provinceInfos.stream().forEach(o -> {
                dqCodeList.add(o.getDqGovCode());
            });
            List<GovInfo> cityList = govInfoMapper.selectList(query.lambda().in(GovInfo::getPreGovCode, dqCodeList));
            city = cityList.size();
        }
//        区
        Integer area = govInfosList.size() - province - city;
//        县级政府为“GV+官方行政代码”
//        AtomicReference<Integer> province = new AtomicReference<>(0);
//        AtomicReference<Integer> city = new AtomicReference<>(0);
//        AtomicReference<Integer> area = new AtomicReference<>(0);
//        govInfosList.stream().forEach(o -> {
//            String govName = o.getGovName();
//            if (!ObjectUtils.isEmpty(govName)) {
//                if (govName.contains(Common.DOV_INFO_TYPE_PRIVINCE_NAME)) {
//                    province.getAndSet(province.get() + 1);
//                } else if (govName.contains(Common.DOV_INFO_TYPE_CITY_NAME)) {
//                    city.getAndSet(city.get() + 1);
//                } else {
//                    area.getAndSet(area.get() + 1);
//                }
//            }
//        });
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("JK", JK);
        result.put("GX", GX);
        result.put("XQ", XQ);
        result.put("QT", QT);
        result.put("city", city);
        result.put("area", area);
        result.put("province", province);
        return result;
    }

    @Override
    public List<GovInfo> getGovLevel(String preGovCode) {
        List<GovInfo> govInfos = new ArrayList<>();

        QueryWrapper<GovInfo> govQuery = new QueryWrapper();

        if (ObjectUtils.isEmpty(preGovCode)) {
            govInfos = govInfoMapper.selectList(govQuery.lambda().eq(GovInfo::getGovLevelBig, 1));
        } else {
            govInfos = govInfoMapper.selectList(govQuery.lambda().eq(GovInfo::getPreGovCode, preGovCode));
        }


        return govInfos;
    }


    /**
     * 字段对应的名称
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_NAME = "name";
    /**
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_ID = "id";
    /**
     * 添加的指标封装的字段
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_MORE = "more";
    /**
     * 新增指标的字段名称
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_KEY = "key";
    /**
     * 新增指标的字段值
     *
     * @author 冉浩岑
     * @date 2022/9/23 15:24
     */
    public static final String MORE_ENTITY_KPI_VALUE = "value";


    /**
     * GovInfo 对象转 map,并查询 曾用名条数
     *
     * @param govInfo
     * @return Map<String, Object>
     * @author 冉浩岑
     * @date 2022/9/22 23:45
     */
    public Map<String, Object> getResultMap(GovInfo govInfo, Map<String, List<EntityNameHis>> map) {
        Map<String, Object> resultMap = new HashMap();
        if (null != govInfo) {
            resultMap = JSON.parseObject(JSON.toJSONString(govInfo), new TypeReference<Map<String, String>>() {
            });
            try {
                Integer count = 0;
                if (!map.isEmpty()) {
                    List<EntityNameHis> nameHisList = map.get(govInfo.getDqGovCode());
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
}
