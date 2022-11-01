package com.deloitte.crm.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.domain.dto.*;
import com.deloitte.crm.dto.GovInfoBynameDto;
import com.deloitte.crm.dto.GovInfoDto;
import com.deloitte.crm.dto.MoreIndex;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.EntityInfoLogsUpdatedService;
import com.deloitte.crm.service.IGovInfoService;
import com.deloitte.crm.utils.TimeFormatUtil;
import com.deloitte.crm.utils.excel.ExcelUtils;
import com.deloitte.crm.vo.EntityOrGovByAttrVo;
import com.deloitte.crm.vo.GovInfoDetailVo;
import com.deloitte.crm.vo.ParentLevelVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
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
@Slf4j
@AllArgsConstructor
public class GovInfoServiceImpl extends ServiceImpl<GovInfoMapper, GovInfo> implements IGovInfoService {
    @Resource
    private GovInfoMapper govInfoMapper;

    @Resource
    private EntityAttrValueMapper entityAttrValueMapper;

    @Resource
    private EntityNameHisMapper nameHisMapper;

    @Resource
    private EntityAttrIntypeMapper intypeMapper;

    @Resource
    private EntityGovRelMapper entityGovRelMapper;

    @Resource
    private GovLevelMapper govLevelMapper;

    @Resource
    private EntityInfoLogsUpdatedService entityInfoLogsUpdatedService;

    @Resource
    private ProductsCoverMapper productsCoverMapper;

    @Resource
    private ProductsMapper productsMapper;

    /**
     * 默认查询页码
     */
    private static final Integer DEFAULT_PAGE_NUM = 1;
    /**
     * 默认查询页面size
     */
    private static final Integer DEFAULT_PAGE_NUM_SIZE = 50;

    /**
     * 德勤政府主体唯一识别码前缀
     */
    private static final String frontTitle = "GV";

    /**
     * 城市规模
     */
    private static final String govScale = "城市规模";
    /**
     * 城市分级
     */
    private static final String govGrading = "城市分级";
    /**
     * 省级行政区
     */
    private static final String isProvince = "省级行政区";
    /**
     * 地级行政区
     */
    private static final String isCity = "地级行政区";
    /**
     * 县级行政区
     */
    private static final String isCounty = "县级行政区";
    /**
     * 经开高新区
     */
    private static final String isJKGX = "经开高新区";
    /**
     * 八大经济区
     */
    private static final String eightER = "八大经济区";
    /**
     * 19个城市群
     */
    private static final String nineteenCity = "19个城市群";
    /**
     * 百强县
     */
    private static final String hundred = "百强县";
    /**
     * 国家中心城市
     */
    private static final String CCity = "国家中心城市";
    /**
     * 省会城市
     */
    private static final String provincial = "省会城市";

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
    @Transactional(rollbackFor = Exception.class)
    public R insertGovInfo(GovInfo govInfo) {
        String govCodeInfo = govInfo.getGovCode();
        boolean matches = govCodeInfo.matches(Common.REGEX_GOV_CODE);
        if(!matches){
            return R.fail("格式不匹配，请修改官方行政代码为6位数字后再提交");
        }
        //校验是否重复提交
        Long count = govInfoMapper.selectCount(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getGovCode, govCodeInfo));
        if (count > 0) {
            return R.fail("请勿重复提交，请修改官方行政代码后再提交");
        }
//        count = govInfoMapper.selectCount(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getGovName, govInfo.getGovName()));
//        if (count > 0) {
//            return R.fail("请勿重复提交，请修改主体名称后再提交");
//        }

        //生成政府主体德勤主体唯一识别代码
        String dqGovCode = getDqGovCode();
        govInfo.setDqGovCode(dqGovCode);

        //设置上级政府官方代码 proCode
        String preGovCode = govInfo.getPreGovCode();
        if (!ObjectUtils.isEmpty(preGovCode)) {
            GovInfo father = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, preGovCode));
            String govCode = father.getGovCode();
            govInfo.setPreCode(govCode);
        }

        //曾用名不为空，则新增曾用名记录表
        String govNameHis = govInfo.getGovNameHis();
        if (!ObjectUtils.isEmpty(govNameHis)) {
            EntityNameHis entityNameHis = new EntityNameHis();
            entityNameHis.setDqCode(govInfo.getDqGovCode());
            entityNameHis.setOldName(govNameHis);
            entityNameHis.setEntityType(2);
            entityNameHis.setHappenDate(new Date());
            entityNameHis.setRemarks(govInfo.getEntityNameHisRemarks());
            entityNameHis.setSource(1);
            nameHisMapper.insert(entityNameHis);
        }
        Date now = new Date();
        String username = SecurityUtils.getUsername();
        govInfo.setCreater(username).setUpdater(username);
        govInfo.setCreated(now).setUpdated(now);
        govInfoMapper.insertGovInfo(govInfo);
        return R.ok("新增政府主体成功");
    }

    /**
     * 获取德勤政府唯一识别码
     *
     * @return String
     * @author 冉浩岑
     * @date 2022/10/20 11:52
     */
    public String getDqGovCode() {
        GovInfo latestGov = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().like(GovInfo::getDqGovCode, frontTitle).orderByDesc(GovInfo::getId).last(" limit 1"));
        if (ObjectUtils.isEmpty(latestGov)) {
            return frontTitle + 000001;
        }
        Long id = latestGov.getId() + 1;
        int length = id.toString().length();
        String dqGovCode = frontTitle;
        for (int i = 0; i < 6 - length; i++) {
            dqGovCode = dqGovCode + 0;
        }
        dqGovCode = dqGovCode + id;
        return dqGovCode;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateInfoList(GovInfo o) {

        entityInfoLogsUpdatedService.insert(o.getDqGovCode(), o.getGovName(), o, o);
        GovInfo govInfo = govInfoMapper.selectById(o.getId());
        String oldName = o.getGovName();
        //修改政府主体名称时，需要先添加曾用名
        if (!ObjectUtils.isEmpty(oldName) && !oldName.equals(govInfo.getGovName())) {
            GovInfo addOldName = new GovInfo();
            addOldName.setId(o.getId());
            addOldName.setGovNameHis(oldName);
            addOldName.setEntityNameHisRemarks(o.getEntityNameHisRemarks());
            addOldName(addOldName);
            //修改曾用名后需要将曾用名和曾用名备注置空
            o.setGovNameHis(null).setEntityNameHisRemarks(null);
        }
        Date now = new Date();
        String username = SecurityUtils.getUsername();
        o.setUpdater(username);
        o.setUpdated(now);
        govInfoMapper.updateById(o);
        return R.ok();
    }

    @Override
    public R getInfoDetail(String dqGovCode) {
        List<GovInfo> govInfos = govInfoMapper.selectList(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, dqGovCode));
        if (CollectionUtils.isEmpty(govInfos)) {
            return R.fail("异常查询，数据为空");
        }
        if (govInfos.size() > 1) {
            return R.fail("异常查询，唯一识别码查出多条数据");
        }
        //查询上级行政code
        GovInfo govInfo = govInfos.get(0);
        String preGovCode = govInfo.getPreGovCode();
        //查询上级关联政府
        GovInfo preGov = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, preGovCode));

        //查询关联企业主体
        Long count = entityGovRelMapper.selectCount(new QueryWrapper<EntityGovRel>().lambda().eq(EntityGovRel::getDqGovCode, dqGovCode));

        //创建响应结果集
        GovInfoDetails govInfoDetails = new GovInfoDetails();
        govInfoDetails.setGovInfo(govInfo).setRelationGov(preGov).setRelationEntity(count);


        return R.ok(govInfoDetails);
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
        LambdaQueryWrapper<GovInfo> eq = new LambdaQueryWrapper<GovInfo>().eq(GovInfo::getStatus, 1);
        List<GovInfo> list = this.list(eq);

        GovInfoDto govInfoDto = new GovInfoDto();

// gov_level_big 是否 省  1-是
        List<GovInfo> province = list.stream()
                .filter(row -> row.getGovLevelBig() != null && row.getGovLevelBig() == 1)
                .collect(Collectors.toList());

// gov_level_big 是否 市  2-是
        List<GovInfo> city = list.stream()
                .filter(row -> row.getGovLevelBig() != null && row.getGovLevelBig() == 2)
                .collect(Collectors.toList());

// gov_level_big 是否 县  3-是
        List<GovInfo> county = list.stream()
                .filter(row -> row.getGovLevelBig() != null && row.getGovLevelBig() == 3)
                .collect(Collectors.toList());

// gov_level_big 是否 经开  4-是
        List<GovInfo> open = list.stream()
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
    public R getInfoList(Integer type, String param, Integer pageNum, Integer pageSize) {
        if (ObjectUtil.isEmpty(pageNum)) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        if (ObjectUtil.isEmpty(pageSize)) {
            pageSize = DEFAULT_PAGE_NUM_SIZE;
        }

        Page<GovInfo> pageInfo = new Page(pageNum, pageSize);
        LambdaQueryWrapper<GovInfo> govInfoQuery = new LambdaQueryWrapper<>();
//        if (!ObjectUtil.isEmpty(type)) {
//            govInfoQuery.eq(GovInfo::getGovType, type);
//        }
        if (!ObjectUtil.isEmpty(param)) {
            govInfoQuery.like(GovInfo::getGovName, param).or().like(GovInfo::getDqGovCode, param);
        }

        Page<GovInfo> govInfoPage = govInfoMapper.selectPage(pageInfo, govInfoQuery);
        List<GovInfo> govInfoList = govInfoPage.getRecords();

        //设置结果集
        Page<GovInfoList> page = new Page(pageNum, pageSize);
        page.setTotal(govInfoPage.getTotal());

        List<GovInfoList> records = new ArrayList<>();
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
        page.setRecords(records).setCurrent(govInfoPage.getCurrent());
        return R.ok(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R addOldName(GovInfo gov) {
        //获取原本的主体信息
        GovInfo govInfo = govInfoMapper.selectById(gov.getId());
        //校验曾用名是否存在
        String entityCode = govInfo.getDqGovCode();
        QueryWrapper<EntityNameHis> queryWrapper = new QueryWrapper<>();
        String nameHis = gov.getGovNameHis();
        Long count = nameHisMapper.selectCount(queryWrapper.lambda()
                .eq(EntityNameHis::getDqCode, entityCode)
                .eq(EntityNameHis::getOldName, nameHis));
        if (count > 0) {
            return R.fail("曾用名重复，请重新输入");
        }
        //获取操作用户
        String remoter = SecurityUtils.getUsername();
        //修改曾用名记录
        String entityNameHis = govInfo.getGovNameHis();
        if (ObjectUtils.isEmpty(entityNameHis)) {
            govInfo.setGovNameHis(gov.getGovNameHis());
        } else {
            govInfo.setGovNameHis(entityNameHis + "," + gov.getGovNameHis());
        }
        String nameHisRemarks = gov.getEntityNameHisRemarks();
        String remarks = "";
        if (ObjectUtils.isEmpty(nameHisRemarks)) {
            remarks = TimeFormatUtil.getFormartDate(new Date()) + " " + remoter + " 系统自动生成";
        } else {
            remarks = TimeFormatUtil.getFormartDate(new Date()) + " " + remoter + " " + nameHisRemarks;
        }

        String oldRemarks = govInfo.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(oldRemarks)) {
            oldRemarks = remarks;
        } else {
            oldRemarks = oldRemarks + ";" + remarks;
        }
        GovInfo backInfo = new GovInfo();
        backInfo.setId(govInfo.getId()).setEntityNameHisRemarks(oldRemarks).setGovNameHis(govInfo.getGovNameHis());
        govInfoMapper.updateById(backInfo);

        //插入曾用名记录表
        EntityNameHis newNameHis = new EntityNameHis();
        newNameHis.setDqCode(govInfo.getDqGovCode());
        newNameHis.setOldName(govInfo.getGovNameHis());
        newNameHis.setEntityType(2);
        newNameHis.setHappenDate(new Date());
        newNameHis.setRemarks(gov.getEntityNameHisRemarks());
        newNameHis.setSource(1);
        nameHisMapper.insert(newNameHis);
        return R.ok();
    }

    @Override
    public R getGovByName(String govName) {
        QueryWrapper<GovInfo> queryWrapper = new QueryWrapper();
        return R.ok(govInfoMapper.selectList(queryWrapper.lambda().like(GovInfo::getGovName, govName)));
    }

    @Override
    public Object getListEntityByPage(GovAttrByDto govAttrDto) {
        Integer pageNum = govAttrDto.getPageNum();
        Integer pageSize = govAttrDto.getPageSize();
        //去除无效选项
        List<MoreIndex> mapList = govAttrDto.getMapList();
        if (!CollectionUtils.isEmpty(mapList)){
            List<MoreIndex> newMapList = mapList.stream().filter(o -> !ObjectUtils.isEmpty(o.getId())).collect(Collectors.toList());
            govAttrDto.setMapList(newMapList);
        }
        if (ObjectUtils.isEmpty(pageNum) && ObjectUtils.isEmpty(pageSize)) {
            return getListEntityAll(govAttrDto);
        } else {
            return getListEntityPage(govAttrDto);
        }
    }

    /**
     * 全量导出
     *
     * @param govAttrByDto
     * @return List<GovInfoResult>
     * @author 冉浩岑
     * @date 2022/9/26 00:35
     */
    public List<GovInfoResult> getListEntityAll(GovAttrByDto govAttrByDto) {
        //设置参数信息
        govAttrByDto = getSend(govAttrByDto);

        //获取基础参数信息
        List<MoreIndex> mapList = govAttrByDto.getMapList();

        List<GovInfo> govInfos = govInfoMapper.getGovByAttrValue(govAttrByDto);

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
     * @param govAttrByDto
     * @return R
     * @author penTang
     * @date 2022/9/26 18:58
     */
    @Override
    public void ExportEntityGov(GovAttrByDto govAttrByDto) {
        List<GovInfoResult> listEntityAll = this.getListEntityAll(govAttrByDto);
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
                vo.getMore().forEach(entryMap -> map.put(entryMap.getKey(), entryMap.getValue()));
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
     * @param govAttrDto
     * @return Page<GovInfoResult>
     * @author 冉浩岑
     * @date 2022/9/25 17:05
     */
    public Page<GovInfoResult> getListEntityPage(GovAttrByDto govAttrDto) {
        //设置参数信息
        govAttrDto = getSend(govAttrDto);

        Integer pageNum = govAttrDto.getPageNum();
        Integer pageSize = govAttrDto.getPageSize();
        Page<GovInfoResult> pageResult = new Page<>(pageNum, pageSize);
        List<MoreIndex> mapList = govAttrDto.getMapList();

        pageNum = (pageNum - 1) * pageSize;
        govAttrDto.setPageNum(pageNum);
        //查询页面数据
        List<GovInfo> records = govInfoMapper.getGovByAttrValueByPage(govAttrDto);
        //查询条数
        Integer count = govInfoMapper.getGovCountByAttrValue(govAttrDto);

        pageResult.setTotal(count);

        //封装新的结果集
        List<GovInfoResult> resultRecords = new ArrayList<>();

        records.stream().forEach(o -> {
            GovInfoResult govInfoResult = getGovInfoResult(o, mapList);
            resultRecords.add(govInfoResult);
        });
        pageResult.setRecords(resultRecords);
        return pageResult;
    }


    private GovInfoResult getGovInfoResult(GovInfo o, List<MoreIndex> mapList) {
        GovInfoResult govInfoResult = new GovInfoResult();
        govInfoResult.setGovInfo(o);
        List<String> header = new ArrayList<>();
        List<String> values = new ArrayList<>();
        if (!CollectionUtils.isEmpty(mapList)) {
            List<MoreIndex> more = new ArrayList<>();
            for (MoreIndex moreIndex : mapList) {
                moreIndex.setKey(moreIndex.getName());
                header.add(moreIndex.getName());

                QueryWrapper<EntityAttrValue> valueQuer = new QueryWrapper<>();
                List<EntityAttrValue> attrValueList = entityAttrValueMapper.selectList(valueQuer.lambda()
                        .eq(EntityAttrValue::getAttrId, moreIndex.getId())
                        .eq(EntityAttrValue::getEntityCode, o.getDqGovCode()));
                String value ="";
                //新增指标栏
                if (!CollectionUtils.isEmpty(attrValueList)) {
                    for (EntityAttrValue attrValue:attrValueList){
                        if (ObjectUtils.isEmpty(value)){
                            value=attrValue.getValue();
                        }else {
                            value=value+","+attrValue.getValue();
                        }
                    }
                    moreIndex.setValue(value);
                }
                values.add(value);
                more.add(moreIndex);
            }
            govInfoResult.setMore(more).setHeader(header).setValues(values);
        }
        return govInfoResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateOldName(String dqCode, String oldName, String newOldName, String status, String remark) {
        if (ObjectUtils.isEmpty(newOldName) || ObjectUtils.isEmpty(oldName)) {
            return R.fail("无效曾用名");
        }
        if (ObjectUtils.isEmpty(status)) {
            //校验修改后的曾用名是否已经存在
            Long count = nameHisMapper.selectCount(new QueryWrapper<EntityNameHis>().lambda()
                    .eq(EntityNameHis::getDqCode, dqCode)
                    .eq(EntityNameHis::getOldName, newOldName));
            if (count > 0) {
                return R.ok("曾用名重复，请重新输入");
            }
        }
        //根据dqCode查询主体表
        QueryWrapper<GovInfo> infoQuery = new QueryWrapper<>();
        GovInfo govInfo = govInfoMapper.selectOne(infoQuery.lambda().eq(GovInfo::getDqGovCode, dqCode));

        //找到原本曾用名
        String govNameHis = govInfo.getGovNameHis();
        String govNameHisRemarks = govInfo.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(govNameHis)) {
            return R.fail("无效曾用名");
        }
        List<String> nameList = Arrays.asList(govNameHis.split(","));
        List<String> remarkList = Arrays.asList(govNameHisRemarks.split(";"));

        String newNameResult = "";
        String newRemarkResult = "";
        for (int i = 0; i < nameList.size(); i++) {
            String remarkVo = "";
            String nameVo = "";
            String hisName = nameList.get(i);

            //匹配历史曾用名则停用或者替换
            if (oldName.equals(hisName)) {
                //匹配时，查询出原本的曾用名数据
                EntityNameHis one = nameHisMapper.selectOne(new QueryWrapper<EntityNameHis>().lambda()
                        .eq(EntityNameHis::getDqCode, dqCode)
                        .eq(EntityNameHis::getOldName, oldName).last(" limit 1"));
                if (ObjectUtils.isEmpty(one)) {
                    continue;
                }
                one.setSource(2);
                // status 不为空则表示体用曾用名
                if (!ObjectUtils.isEmpty(status)) {
                    one.setStatus(0);
                    //直接替换原本的数据
                    nameHisMapper.updateById(one);
                    continue;
                }
                //替换曾用名
                if (ObjectUtils.isEmpty(remark)) {
                    remark="系统自动生成";
                }
                one.setRemarks(remark).setOldName(newOldName);
                //更新原本的数据
                nameHisMapper.updateById(one);
                nameVo = newOldName;
                remarkVo = TimeFormatUtil.getFormartDate(new Date()) + " " + SecurityUtils.getUsername() + " " + remark;
                if (ObjectUtils.isEmpty(remark)) {
                    remarkVo = remarkVo + "系统自动生成";
                }
            } else {
                nameVo = hisName;
                if (remarkList.size() > i) {
                    remarkVo = remarkList.get(i);
                } else {
                    remarkVo = TimeFormatUtil.getFormartDate(new Date()) + " " + SecurityUtils.getUsername() + " 系统自动生成";
                }
            }
            // 拼接历史曾用名和备注
            if (ObjectUtils.isEmpty(newNameResult)) {
                newNameResult = nameVo;
            } else {
                newNameResult = newNameResult + "," + nameVo;
            }
            if (ObjectUtils.isEmpty(newRemarkResult)) {
                newRemarkResult = remarkVo;
            } else {
                newRemarkResult = newRemarkResult + ";" + remarkVo;
            }
        }
        GovInfo backInfo = new GovInfo();
        //赋值新的曾用名属性
        backInfo.setId(govInfo.getId()).setGovNameHis(newNameResult).setEntityNameHisRemarks(newRemarkResult);
        //修改主表属性
        govInfoMapper.updateById(backInfo);
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
     * 获取上级地方政府行政编码 by正杰
     *
     * @param govCode
     * @return
     */
    @Override
    public R<String> getPreGovName(String govCode) {
        GovInfo govInfo = baseMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getGovCode, govCode));
        Assert.notNull(govInfo, BadInfo.VALID_EMPTY_TARGET.getInfo());
        return R.ok(govInfo.getGovName());
    }

    /**
     * 字段对应的名称
     */
    public static final String MORE_ENTITY_KPI_NAME = "name";
    /**
     * id
     */
    public static final String MORE_ENTITY_KPI_ID = "id";
    /**
     * 新增指标的字段名称
     */
    public static final String MORE_ENTITY_KPI_KEY = "key";
    /**
     * 新增指标的字段值
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
    public GovInfoList getResultMap(GovInfo govInfo, Map<String, List<EntityNameHis>> map) {
        GovInfoList resultMap = new GovInfoList();
        if (null != govInfo) {
            resultMap.setGovInfo(govInfo);
            try {
                Integer count = 0;
                if (!map.isEmpty()) {
                    List<EntityNameHis> nameHisList = map.get(govInfo.getDqGovCode());
                    if (!CollectionUtils.isEmpty(nameHisList)) {
                        count = nameHisList.size();
                    }
                }
                resultMap.setNameUsedNum(count);
            } catch (Exception e) {
                return resultMap;
            }
        }
        resultMap.newUpdateRecord(resultMap);
        return resultMap;
    }

    /**
     * 覆盖查询政府主体
     *
     * @return R
     * @author penTang
     * @date 2022/10/10 14:38
     */
    @Override
    public R getGovEntityResult(EntityOrGovByAttrVo entityOrGovByAttrVo) {
        Page<GovInfo> page = new Page<>(entityOrGovByAttrVo.getPageNum(), entityOrGovByAttrVo.getPageSize());
        LambdaQueryWrapper<GovInfo> eq;
        if (entityOrGovByAttrVo.getEntityName()==null || entityOrGovByAttrVo.getEntityName().equals("") ){

            eq = new LambdaQueryWrapper<GovInfo>();
        }else {
            eq = new LambdaQueryWrapper<GovInfo>().like(GovInfo::getGovName, entityOrGovByAttrVo.getEntityName());

        }
        Page<GovInfo> page1 = govInfoMapper.selectPage(page, eq);
        List<GovInfo> records = page1.getRecords();
        ArrayList<GovInfoBynameDto> govInfoBynameDtos = new ArrayList<>();
        for (GovInfo record : records) {
            GovInfoBynameDto govInfoBynameDto = new GovInfoBynameDto();
            ArrayList<HashMap<String, String>> maps = new ArrayList<>();
            List<Integer> proId = entityOrGovByAttrVo.getProId();
            //判断空指针
            govInfoBynameDto.setStatus(record.getStatus());
            govInfoBynameDto.setDqCode(record.getDqGovCode());
            govInfoBynameDto.setGovCode(record.getGovCode());
            govInfoBynameDto.setGovName(record.getGovName());
            LambdaQueryWrapper<GovLevel> eq1 = new LambdaQueryWrapper<GovLevel>().eq(GovLevel::getId, record.getGovLevelBig());
            GovLevel govLevel = govLevelMapper.selectOne(eq1);
            govInfoBynameDto.setLevelName(govLevel.getName());
            govInfoBynameDtos.add(govInfoBynameDto);
        }
        Page<GovInfoBynameDto> pageResult = new Page<>(entityOrGovByAttrVo.getPageNum(), entityOrGovByAttrVo.getPageSize());
        pageResult.setRecords(govInfoBynameDtos).setTotal(page1.getTotal());
        return R.ok(pageResult);
    }

    /**
     * 地方政府-更多指标-主体范围
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/10/11 17:10
     */

    @Override
    public List<ParentLevelVo> getGovRange() {

        List<ParentLevelVo> parentLevelVo = new ArrayList<>();

//        省级行政区 1 地级行政区 2 县级行政区 3 经开高新区 4
        parentLevelVo = setAdministrationRegion(parentLevelVo);
//        八大经济区
        parentLevelVo = setEightEconomicsRegion(parentLevelVo);
//        19个城市群
        parentLevelVo = setNineteenCityGroup(parentLevelVo);
//        城市规模
        parentLevelVo = setGovScale(parentLevelVo);
//        城市分级
        parentLevelVo = setGovGrading(parentLevelVo);
//        国家中心城市
        parentLevelVo = setCCity(parentLevelVo);
//        省会城市
        parentLevelVo = setProcincial(parentLevelVo);
//        百强县
        parentLevelVo = setHundred(parentLevelVo);
        return parentLevelVo;
    }

    private List<ParentLevelVo> setHundred(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo province = new ParentLevelVo();
        province.setName(hundred);
        List<ParentLevelVo> list = new ArrayList<>();

        ParentLevelVo parentValue = getParentValue(hundred, 1, province.getName());
        list.add(parentValue);

        province.setValue(list);
        parentLevelVo.add(province);

        return parentLevelVo;

    }

    private List<ParentLevelVo> setProcincial(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo province = new ParentLevelVo();
        province.setName(provincial);
        List<ParentLevelVo> list = new ArrayList<>();

        ParentLevelVo parentValue = getParentValue(provincial, 1, province.getName());
        list.add(parentValue);

        province.setValue(list);
        parentLevelVo.add(province);

        return parentLevelVo;

    }

    private List<ParentLevelVo> setCCity(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo province = new ParentLevelVo();
        province.setName(CCity);
        List<ParentLevelVo> list = new ArrayList<>();

        ParentLevelVo parentValue = getParentValue(CCity, 1, province.getName());
        list.add(parentValue);

        province.setValue(list);
        parentLevelVo.add(province);

        return parentLevelVo;

    }


    public static GovAttrByDto getSend(GovAttrByDto govAttrByDto) {
        List<GovInfoDetailVo> send = govAttrByDto.getSend();
        if (CollectionUtils.isEmpty(send)) {
            return govAttrByDto;
        }
        for (GovInfoDetailVo item : send) {
            List<String> value = item.getValue();
            String key = item.getKey();
            switch (key) {
                case govScale:
                    govAttrByDto.setGovScale(value);
                    break;
                case govGrading:
                    govAttrByDto.setGovGrading(value);
                    break;
                case isProvince:
                    govAttrByDto.setIsProvince(value);
                    break;
                case isCity:
                    govAttrByDto.setIsCity(value);
                    break;
                case isCounty:
                    govAttrByDto.setIsCounty(value);
                    break;
                case isJKGX:
                    govAttrByDto.setIsJKGX(value);
                    break;
                case eightER:
                    govAttrByDto.setEightER(value);
                    break;
                case nineteenCity:
                    govAttrByDto.setNineteenCity(value);
                    break;
                case hundred:
                    if (!CollectionUtils.isEmpty(value)) {
                        govAttrByDto.setHundred(Integer.valueOf(value.get(0)));
                    }
                    break;
                case CCity:
                    if (!CollectionUtils.isEmpty(value)) {
                        govAttrByDto.setCCity(Integer.valueOf(value.get(0)));
                    }
                    break;
                case provincial:
                    if (!CollectionUtils.isEmpty(value)) {
                        govAttrByDto.setProvincial(Integer.valueOf(value.get(0)));
                    }
                    break;
            }
        }
        return govAttrByDto;
    }

    @Override
    public GovView getGovView() {
        GovView govView = new GovView();
        List<GovInfo> govInfos = govInfoMapper.selectList(new QueryWrapper<>());
        Map<Integer, List<GovInfo>> listGovMap = govInfos.stream().filter(o -> !ObjectUtil.isEmpty(o.getGovLevelBig())).collect(Collectors.groupingBy(GovInfo::getGovLevelBig));
        Integer total = 0;
        Integer province = 0;
        Integer city = 0;
        Integer area = 0;
        Integer gx = 0;
        //政府总数
        if (!CollectionUtils.isEmpty(govInfos)) {
            total = govInfos.size();
        }
        //省级总数
        if (!CollectionUtils.isEmpty(listGovMap.get(1))) {
            province = listGovMap.get(1).size();
        }
        //市级总数
        if (!CollectionUtils.isEmpty(listGovMap.get(2))) {
            city = listGovMap.get(2).size();
        }
        //县级总数
        if (!CollectionUtils.isEmpty(listGovMap.get(3))) {
            area = listGovMap.get(3).size();
        }
        //经开高新总数
        if (!CollectionUtils.isEmpty(listGovMap.get(4))) {
            gx = listGovMap.get(4).size();
        }
        govView.setGovTotle(total).setProvince(province).setCity(city).setArea(area).setGx(gx).setNoLevel(total - province - city - area - gx);
        return govView;
    }

    @Override
    public List<GovInfo> getGovByParam(String param) {
        return govInfoMapper.selectList(new QueryWrapper<GovInfo>().lambda()
                .like(GovInfo::getDqGovCode, param)
                .or().like(GovInfo::getGovName, param)
                .or().like(GovInfo::getGovCode, param)
        );
    }

    @Override
    public void exportEntity(HttpServletResponse response) {
        List<GovInfo> govInfoList = govInfoMapper.selectList(new QueryWrapper<GovInfo>());
        if (CollectionUtils.isEmpty(govInfoList)) {
            return;
        }

        List<GovLevel> govLevels = govLevelMapper.selectList(null);
        Map<Long, List<GovLevel>> levelMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(govLevels)) {
            levelMap = govLevels.stream().collect(Collectors.groupingBy(GovLevel::getId));
        }
        // 将数据汇总
        List<List<Object>> sheetDataList = new ArrayList<>();
        List<Object> head = Arrays.asList("政府名称", "政府官方行政编码", "政府德勤唯一识别码",
                "上级政府官方行政编码", "上级政府德勤唯一识别码", "上级政府名称",
                "政府主体行政单位级别-大类", "政府主体行政单位级别-小类", "是否生效 0.失效 1.生效");
        sheetDataList.add(head);
        for (GovInfo govInfo : govInfoList) {
            //添加行数据
            List<Object> sheetData = new ArrayList<>();
            //政府信息
            sheetData.add(govInfo.getGovName());
            sheetData.add(govInfo.getGovCode());
            sheetData.add(govInfo.getDqGovCode());
            //父级政府信息
            sheetData.add(govInfo.getPreCode());
            sheetData.add(govInfo.getPreGovCode());
            sheetData.add(govInfo.getPreGovName());
            Integer govLevelBig = govInfo.getGovLevelBig();
            Integer govLevelSmall = govInfo.getGovLevelSmall();
            //大类小类信息上级政府统一社会信用代码
            //大类信息
            if (!CollectionUtils.isEmpty(levelMap.keySet()) && !ObjectUtils.isEmpty(govLevelBig) && !CollectionUtils.isEmpty(levelMap.get(Long.valueOf(govLevelBig)))) {
                sheetData.add(levelMap.get(Long.valueOf(govLevelBig)).get(0).getName());
            } else {
                sheetData.add(govLevelBig);
            }
            //小类信息
            if (!CollectionUtils.isEmpty(levelMap.keySet()) && !ObjectUtils.isEmpty(govLevelSmall) && !CollectionUtils.isEmpty(levelMap.get(Long.valueOf(govLevelSmall)))) {
                sheetData.add(levelMap.get(Long.valueOf(govLevelSmall)).get(0).getName());
            } else {
                sheetData.add(govLevelSmall);
            }
            sheetData.add(govInfo.getStatus());
            //添加总数据
            sheetDataList.add(sheetData);
        }
        // 导出数据
        ExcelUtils.export(response, "政府主体表", sheetDataList);
        log.info("导出政府主体表完毕");
    }

    @Override
    public void updateGovInfosByPreCode() {
        List<GovInfo> govInfoList = govInfoMapper.selectList(new QueryWrapper<GovInfo>());
        for (GovInfo govInfo : govInfoList) {
            String preGovCode = govInfo.getPreGovCode();
            //父级政府 code 为空时不更新父子级信息
            if (ObjectUtils.isEmpty(preGovCode)) {
                continue;
            }
            GovInfo preGov = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, preGovCode).last(" limit 1"));
            //父级政府为空时不更新父子级信息
            if (ObjectUtils.isEmpty(preGov)) {
                continue;
            }
            //不为空且和父级属性都匹配时不更新父子级信息
            if (!ObjectUtils.isEmpty(govInfo.getPreCode()) && !ObjectUtils.isEmpty(govInfo.getPreGovName()) &&
                    govInfo.getPreCode().equals(preGov.getGovCode()) && govInfo.getPreGovName().equals(preGov.getGovName())) {
                continue;
            }
            //跟新子级记录中的 父级政府社会统一识别code 和 父级政府名称
            govInfo.setPreCode(preGov.getGovCode()).setPreGovName(preGov.getGovName());
            govInfoMapper.updateById(govInfo);
        }
    }

    @Override
    public R getGovInfoByLevel(Integer bigLevel, Integer smallLevel) {
        LambdaQueryWrapper<GovInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (!ObjectUtils.isEmpty(bigLevel)){
            queryWrapper.eq(GovInfo::getGovLevelBig,(bigLevel-1));
        }
//        if (!ObjectUtils.isEmpty(smallLevel)){
//            queryWrapper.eq(GovInfo::getGovLevelSmall,smallLevel);
//        }
        return R.ok(govInfoMapper.selectList(queryWrapper));
    }

    //返回筛选范围--城市分级
    private List<ParentLevelVo> setGovGrading(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo province = new ParentLevelVo();
        province.setName(govGrading);
        List<ParentLevelVo> list = new ArrayList<>();

        QueryWrapper<EntityAttrIntype> intypeQuery = new QueryWrapper<>();
        List<EntityAttrIntype> entityAttrIntypes = intypeMapper.selectList(intypeQuery.lambda().eq(EntityAttrIntype::getAttrId, 24));
        entityAttrIntypes.stream().forEach(o -> {
            ParentLevelVo parentValue = getParentValue(o.getValue(), o.getValue(), province.getName());
            list.add(parentValue);
        });

        province.setValue(list);
        parentLevelVo.add(province);

        return parentLevelVo;

    }

    //返回筛选范围--城市规模
    private List<ParentLevelVo> setGovScale(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo province = new ParentLevelVo();
        List<ParentLevelVo> list = new ArrayList<>();
        province.setName("城市规模");

        QueryWrapper<EntityAttrIntype> intypeQuery = new QueryWrapper<>();
        List<EntityAttrIntype> entityAttrIntypes = intypeMapper.selectList(intypeQuery.lambda().eq(EntityAttrIntype::getAttrId, 23));
        entityAttrIntypes.stream().forEach(o -> {
            ParentLevelVo parentValue = getParentValue(o.getValue(), o.getValue(), province.getName());
            list.add(parentValue);
        });

        province.setValue(list);
        parentLevelVo.add(province);

        return parentLevelVo;

    }

    //返回筛选范围--设置19个城市群
    private List<ParentLevelVo> setNineteenCityGroup(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo province = new ParentLevelVo();
        province.setName("19个城市群");

        List<ParentLevelVo> list = new ArrayList<>();

        QueryWrapper<EntityAttrIntype> intypeQuery = new QueryWrapper<>();
        List<EntityAttrIntype> entityAttrIntypes = intypeMapper.selectList(intypeQuery.lambda().eq(EntityAttrIntype::getAttrId, 21));
        entityAttrIntypes.stream().forEach(o -> {
            ParentLevelVo parentValue = getParentValue(o.getValue(), o.getValue(), province.getName());
            list.add(parentValue);
        });

        province.setValue(list);
        parentLevelVo.add(province);

        return parentLevelVo;
    }

    //返回筛选范围--设置八大经济区
    private List<ParentLevelVo> setEightEconomicsRegion(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo province = new ParentLevelVo();
        province.setName("八大经济区");

        List<ParentLevelVo> list = new ArrayList<>();

        QueryWrapper<EntityAttrIntype> intypeQuery = new QueryWrapper<>();
        List<EntityAttrIntype> entityAttrIntypes = intypeMapper.selectList(intypeQuery.lambda().eq(EntityAttrIntype::getAttrId, 20));
        entityAttrIntypes.stream().forEach(o -> {
            ParentLevelVo parentValue = getParentValue(o.getValue(), o.getValue(), province.getName());
            list.add(parentValue);
        });

        province.setValue(list);
        parentLevelVo.add(province);

        return parentLevelVo;
    }

    //返回筛选范围--设置城市级别数据
    private List<ParentLevelVo> setAdministrationRegion(List<ParentLevelVo> parentLevelVo) {

        QueryWrapper<GovLevel> levelQuery = new QueryWrapper<>();
        Map<Long, List<GovLevel>> levelMap = govLevelMapper.selectList(levelQuery.lambda().isNotNull(GovLevel::getParentId))
                .stream().collect(Collectors.groupingBy(GovLevel::getParentId));
        for (Long parentId : levelMap.keySet()) {
            if (parentId == 1L) {
                ParentLevelVo province = new ParentLevelVo();
                province.setName("省级行政区");

                List<ParentLevelVo> list = new ArrayList<>();
                List<GovLevel> govLevels = levelMap.get(parentId);
                for (GovLevel level : govLevels) {
                    ParentLevelVo parentValue = getParentValue(level.getName(), level.getId(), province.getName());
                    list.add(parentValue);
                }

                province.setValue(list);
                parentLevelVo.add(province);
            } else if (parentId == 2L) {
                ParentLevelVo city = new ParentLevelVo();
                city.setName("地级行政区");

                List<GovLevel> govLevels = levelMap.get(parentId);
                List<ParentLevelVo> list = new ArrayList<>();
                for (GovLevel level : govLevels) {
                    ParentLevelVo parentValue = getParentValue(level.getName(), level.getId(), city.getName());
                    list.add(parentValue);
                }
                city.setValue(list);
                parentLevelVo.add(city);
            } else if (parentId == 3L) {
                ParentLevelVo country = new ParentLevelVo();
                country.setName("县级行政区");

                List<GovLevel> govLevels = levelMap.get(parentId);
                List<ParentLevelVo> list = new ArrayList<>();
                for (GovLevel level : govLevels) {
                    ParentLevelVo parentValue = getParentValue(level.getName(), level.getId(), country.getName());
                    list.add(parentValue);
                }

                country.setValue(list);
                parentLevelVo.add(country);

            } else if (parentId == 4L) {
                ParentLevelVo isJKGX = new ParentLevelVo();
                isJKGX.setName("经开高新区");

                List<GovLevel> govLevels = levelMap.get(parentId);
                List<ParentLevelVo> list = new ArrayList<>();
                for (GovLevel level : govLevels) {
                    ParentLevelVo parentValue = getParentValue(level.getName(), level.getId(), isJKGX.getName());
                    list.add(parentValue);
                }

                isJKGX.setValue(list);
                parentLevelVo.add(isJKGX);
            }
        }
        return parentLevelVo;
    }

    public ParentLevelVo getParentValue(String name, Object value, String preName) {
        ParentLevelVo parentLevelVo = new ParentLevelVo();
        parentLevelVo.setName(name).setSend(value).setPreName(preName);
        return parentLevelVo;
    }
    @Override
    public List<GovInfo>  selectList() {
        LambdaQueryWrapper<GovInfo> eq = new LambdaQueryWrapper<GovInfo>().eq(GovInfo::getStatus, 1);
        return list(eq);
    }

}
