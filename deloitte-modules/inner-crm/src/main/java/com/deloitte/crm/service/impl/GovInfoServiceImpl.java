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
 * ???????????????????????????Service???????????????
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
     * ??????????????????
     */
    private static final Integer DEFAULT_PAGE_NUM = 1;
    /**
     * ??????????????????size
     */
    private static final Integer DEFAULT_PAGE_NUM_SIZE = 50;

    /**
     * ???????????????????????????????????????
     */
    private static final String frontTitle = "GV";

    /**
     * ????????????
     */
    private static final String govScale = "????????????";
    /**
     * ??????????????????
     */
    private static final String gegphZones = "??????????????????";
    /**
     * ????????????
     */
    private static final String govGrading = "????????????";
    /**
     * ???????????????
     */
    private static final String isProvince = "???????????????";
    /**
     * ???????????????
     */
    private static final String isCity = "???????????????";
    /**
     * ???????????????
     */
    private static final String isCounty = "???????????????";
    /**
     * ???????????????
     */
    private static final String isJKGX = "???????????????";
    /**
     * ???????????????
     */
    private static final String eightER = "???????????????";
    /**
     * 19????????????
     */
    private static final String nineteenCity = "19????????????";
    /**
     * ?????????
     */
    private static final String hundred = "???????????????";
    /**
     * ??????????????????
     */
    private static final String CCity = "????????????????????????";
    /**
     * ????????????
     */
    private static final String provincial = "??????????????????";
    /**
     * ???
     */
    private static final String YES = "???";
    /**
     * ???
     */
    private static final String NO = "???";
    /**
     * ?????????
     */
    private static final String NOT_APPLY = "?????????";

    /**
     * ?????????????????????????????????
     *
     * @param id ?????????????????????????????????
     * @return ???????????????????????????
     */
    @Override
    public GovInfo selectGovInfoById(Long id) {
        return govInfoMapper.selectGovInfoById(id);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param govInfo ???????????????????????????
     * @return ???????????????????????????
     */
    @Override
    public List<GovInfo> selectGovInfoList(GovInfo govInfo) {
        return govInfoMapper.selectGovInfoList(govInfo);
    }

    /**
     * ?????????????????????????????????
     *
     * @param govInfo ???????????????????????????
     * @return ??????
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R insertGovInfo(GovInfo govInfo) {
        String govCodeInfo = govInfo.getGovCode();
        boolean matches = govCodeInfo.matches(Common.REGEX_GOV_CODE);
        if (!matches) {
            return R.fail("????????????????????????????????????????????????6?????????????????????");
        }
        //????????????????????????
        Long count = govInfoMapper.selectCount(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getGovCode, govCodeInfo));
        if (count > 0) {
            return R.fail("????????????????????????????????????????????????????????????");
        }
//        count = govInfoMapper.selectCount(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getGovName, govInfo.getGovName()));
//        if (count > 0) {
//            return R.fail("??????????????????????????????????????????????????????");
//        }

        //????????????????????????????????????????????????
        String dqGovCode = getDqGovCode();
        govInfo.setDqGovCode(dqGovCode);

        //?????????????????????????????? proCode
        String preGovCode = govInfo.getPreGovCode();
        if (!ObjectUtils.isEmpty(preGovCode)) {
            GovInfo father = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, preGovCode));
            String govCode = father.getGovCode();
            govInfo.setPreCode(govCode);
        }

        //????????????????????????????????????????????????
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
        return R.ok("????????????????????????");
    }

    /**
     * ?????????????????????????????????
     *
     * @return String
     * @author ?????????
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
     * ?????????????????????????????????
     *
     * @param govInfo ???????????????????????????
     * @return ??????
     */
    @Override
    public int updateGovInfo(GovInfo govInfo) {
        return govInfoMapper.updateGovInfo(govInfo);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param ids ????????????????????????????????????????????????
     * @return ??????
     */
    @Override
    public int deleteGovInfoByIds(Long[] ids) {
        return govInfoMapper.deleteGovInfoByIds(ids);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param id ?????????????????????????????????
     * @return ??????
     */
    @Override
    public int deleteGovInfoById(Long id) {
        return govInfoMapper.deleteGovInfoById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateInfoList(GovInfo info) {
        Integer updateStatus = info.getStatus();
        GovInfo govInfo = govInfoMapper.selectById(info.getId());
        Integer organStatus = govInfo.getStatus();
        if (!ObjectUtils.isEmpty(updateStatus) && !ObjectUtils.isEmpty(organStatus) && updateStatus == 0 && organStatus == 1) {
            String newGovCode = info.getNewGovCode();
            String newGovName = info.getNewGovName();
            if (ObjectUtils.isEmpty(newGovCode)) {
                return R.fail("?????????????????????????????????");
            }
            if (ObjectUtils.isEmpty(newGovName)) {
                return R.fail("?????????????????????????????????");
            }
            info.setNewDqCode(info.getDqGovCode());
        }
        entityInfoLogsUpdatedService.insert(info.getDqGovCode(), info.getGovName(), govInfo, info);
        String oldName = info.getGovName();
        //??????????????????????????????????????????????????????
        if (!ObjectUtils.isEmpty(oldName) && !oldName.equals(govInfo.getGovName())) {
            GovInfo addOldName = new GovInfo();
            addOldName.setId(info.getId());
            addOldName.setGovNameHis(oldName);
            addOldName.setEntityNameHisRemarks(info.getEntityNameHisRemarks());
            addOldName(addOldName);
            //????????????????????????????????????????????????????????????
            info.setGovNameHis(null).setEntityNameHisRemarks(null);
        }
        Date now = new Date();
        String username = SecurityUtils.getUsername();
        info.setUpdater(username);
        info.setUpdated(now);
        govInfoMapper.updateById(info);
        return R.ok();
    }

    @Override
    public R getInfoDetail(String dqGovCode) {
        List<GovInfo> govInfos = govInfoMapper.selectList(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, dqGovCode));
        if (CollectionUtils.isEmpty(govInfos)) {
            return R.fail("???????????????????????????");
        }
        if (govInfos.size() > 1) {
            return R.fail("????????????????????????????????????????????????");
        }
        //??????????????????code
        GovInfo govInfo = govInfos.get(0);
        String preGovCode = govInfo.getPreGovCode();
        //????????????????????????
        GovInfo preGov = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, preGovCode));

        //????????????????????????
        Long count = entityGovRelMapper.selectCount(new QueryWrapper<EntityGovRel>().lambda().eq(EntityGovRel::getDqGovCode, dqGovCode));

        //?????????????????????
        GovInfoDetails govInfoDetails = new GovInfoDetails();
        govInfoDetails.setGovInfo(govInfo).setRelationGov(preGov).setRelationEntity(count);


        return R.ok(govInfoDetails);
    }

    /**
     * ??????????????????
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

// gov_level_big ?????? ???  1-???
        List<GovInfo> province = list.stream()
                .filter(row -> row.getGovLevelBig() != null && row.getGovLevelBig() == 1)
                .collect(Collectors.toList());

// gov_level_big ?????? ???  2-???
        List<GovInfo> city = list.stream()
                .filter(row -> row.getGovLevelBig() != null && row.getGovLevelBig() == 2)
                .collect(Collectors.toList());

// gov_level_big ?????? ???  3-???
        List<GovInfo> county = list.stream()
                .filter(row -> row.getGovLevelBig() != null && row.getGovLevelBig() == 3)
                .collect(Collectors.toList());

// gov_level_big ?????? ??????  4-???
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

        //???????????????
        Page<GovInfoList> page = new Page(pageNum, pageSize);
        page.setTotal(govInfoPage.getTotal());

        List<GovInfoList> records = new ArrayList<>();
        //????????????????????????
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
        //???????????????????????????
        GovInfo govInfo = govInfoMapper.selectById(gov.getId());
        //???????????????????????????
        String entityCode = govInfo.getDqGovCode();
        QueryWrapper<EntityNameHis> queryWrapper = new QueryWrapper<>();
        String nameHis = gov.getGovNameHis();
        Long count = nameHisMapper.selectCount(queryWrapper.lambda()
                .eq(EntityNameHis::getDqCode, entityCode)
                .eq(EntityNameHis::getOldName, nameHis));
        if (count > 0) {
            return R.fail("?????????????????????????????????");
        }
        //??????????????????
        String remoter = SecurityUtils.getUsername();
        //?????????????????????
        String entityNameHis = govInfo.getGovNameHis();
        if (ObjectUtils.isEmpty(entityNameHis)) {
            govInfo.setGovNameHis(gov.getGovNameHis());
        } else {
            govInfo.setGovNameHis(entityNameHis + "," + gov.getGovNameHis());
        }
        String nameHisRemarks = gov.getEntityNameHisRemarks();
        String remarks = "";
        if (ObjectUtils.isEmpty(nameHisRemarks)) {
            remarks = TimeFormatUtil.getFormartDate(new Date()) + " " + remoter + " ??????????????????";
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

        //????????????????????????
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

        if (ObjectUtils.isEmpty(pageNum) && ObjectUtils.isEmpty(pageSize)) {
            return getListEntityAll(govAttrDto);
        } else {
            return getListEntityPage(govAttrDto);
        }
    }

    /**
     * ????????????
     *
     * @param govAttrByDto
     * @return List<GovInfoResult>
     * @author ?????????
     * @date 2022/9/26 00:35
     */
    public List<GovInfoResult> getListEntityAll(GovAttrByDto govAttrByDto) {
        //??????????????????
        List<MoreIndex> mapList = govAttrByDto.getMapList();
        if (!CollectionUtils.isEmpty(mapList)) {
            List<MoreIndex> newMapList = mapList.stream().filter(o -> !ObjectUtils.isEmpty(o.getId())).collect(Collectors.toList());
            govAttrByDto.setMapList(newMapList);
        }
        //??????????????????
        govAttrByDto = getSend(govAttrByDto);

        //????????????????????????
        mapList = govAttrByDto.getMapList();

        List<GovInfo> govInfos = govInfoMapper.getGovByAttrValue(govAttrByDto);
        List<GovInfoResult> resultRecords = new ArrayList<>();
        if (CollectionUtils.isEmpty(govInfos)){
            return resultRecords;
        }
        //?????????????????????
        resultRecords=getGovInfoResultNew(govInfos,mapList,resultRecords);

//        //?????????????????????
//        List<GovInfoResult> resultRecords = new ArrayList<>();
//
//        govInfos.stream().forEach(o -> {
//            GovInfoResult govInfoResult = getGovInfoResult(o, mapList);
//            resultRecords.add(govInfoResult);
//        });

        return resultRecords;
    }

    private List<GovInfoResult> getGovInfoResultNew(List<GovInfo> govInfos, List<MoreIndex> mapList, List<GovInfoResult> resultRecords) {
        List<String>idList=new ArrayList<>();
        List<String>codeList=new ArrayList<>();
        List<String>header=new ArrayList<>();
        govInfos.forEach(o->codeList.add(o.getDqGovCode()));
        QueryWrapper<EntityAttrValue> query = new QueryWrapper<>();
        //??????????????????????????????
        List<EntityAttrValue> attrValueList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(codeList)){
            query.lambda().in(EntityAttrValue::getEntityCode,codeList );
        }
        mapList.forEach(o->{
            header.add(o.getName());
            idList.add(o.getId());
        });
        //??????????????????????????????????????????
        if (!CollectionUtils.isEmpty(idList)){
            query.lambda().in(EntityAttrValue::getAttrId,idList );
            //?????????????????????
            attrValueList = entityAttrValueMapper.selectList(query);
        }
        Map<String, List<EntityAttrValue>> entityCodeMap =new HashMap<>();
        if (!CollectionUtils.isEmpty(attrValueList)){
            entityCodeMap = attrValueList.stream().collect(Collectors.groupingBy(EntityAttrValue::getEntityCode));
        }
        Map<String, List<EntityAttrValue>> finalEntityCodeMap = entityCodeMap;
        govInfos.forEach(info->{
            List<MoreIndex> more=new ArrayList<>();
            List<String> values=new ArrayList<>();//????????????????????????????????????????????????
            if (!CollectionUtils.isEmpty(mapList)){
                //??????????????????
                mapList.forEach(o->{
                    MoreIndex moreIndex = new MoreIndex();
                    moreIndex.setName(o.getName()).setId(o.getId()).setKey(o.getName());
                    String value="";
                    if (!ObjectUtils.isEmpty(finalEntityCodeMap)){
                        List<EntityAttrValue> valueList = finalEntityCodeMap.get(info.getDqGovCode());
                        if (!ObjectUtils.isEmpty(valueList)){
                            Map<Long, List<EntityAttrValue>> attrValuesById = valueList.stream().collect(Collectors.groupingBy(EntityAttrValue::getAttrId));
                            List<EntityAttrValue> attrValuesByAttrId = attrValuesById.get(Long.valueOf(o.getId()));
                            if (!ObjectUtils.isEmpty(attrValuesByAttrId)){
                                value = attrValuesByAttrId.get(0).getValue();
                            }
                        }
                    }
                    moreIndex.setValue(value);
                    values.add(value);
                    more.add(moreIndex);
                });
            }
            GovInfoResult govInfoResult =new GovInfoResult();
            govInfoResult.setGovInfo(info).setHeader(header).setValues(values).setMore(more);
            resultRecords.add(govInfoResult);
        });

        return resultRecords;
    }

    /**
     * ????????????????????????
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
            map.put("??????", serialNumber.incrementAndGet());
            map.put("??????????????????", info.getDqGovCode());
            map.put("????????????", info.getGovName());
            map.put("????????????", info.getStatus());
            map.put("?????????????????????", info.getGovCode());
            map.put("????????????", DateUtil.parseDateToStr("yyyy/MM/dd", info.getCreated()));
            map.put("?????????", info.getCreater());
            if (!CollectionUtils.isEmpty(vo.getMore())) {
                vo.getMore().forEach(entryMap -> map.put(entryMap.getKey(), entryMap.getValue()));
            }
            rows.add(map);
        });
        //??????????????????????????????????????????
        writer.write(rows, true);
        // ???????????????
        Sheet sheet = writer.getSheet();
        // ??????????????????
        for (int columnIndex = 0; columnIndex < writer.getColumnCount(); columnIndex++) {
            int width = sheet.getColumnWidth(columnIndex) / 256;
            // ??????????????????
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
        // response???HttpServletResponse??????
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //test.xls??????????????????????????????????????????????????????????????????????????????
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + (URLEncoder.encode("????????????", "UTF-8")) + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        try (ServletOutputStream out = response.getOutputStream()) {
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();

        }
        // ??????writer???????????????
        writer.close();
        IoUtil.close(out);


    }

    /**
     * ????????????
     *
     * @param govAttrDto
     * @return Page<GovInfoResult>
     * @author ?????????
     * @date 2022/9/25 17:05
     */
    public Page<GovInfoResult> getListEntityPage(GovAttrByDto govAttrDto) {
        //??????????????????
        List<MoreIndex> mapList = govAttrDto.getMapList();
        if (!CollectionUtils.isEmpty(mapList)) {
            List<MoreIndex> newMapList = mapList.stream().filter(o -> !ObjectUtils.isEmpty(o.getId())).collect(Collectors.toList());
            govAttrDto.setMapList(newMapList);
        }
        //??????????????????
        govAttrDto = getSend(govAttrDto);

        Integer pageNum = govAttrDto.getPageNum();
        Integer pageSize = govAttrDto.getPageSize();
        Page<GovInfoResult> pageResult = new Page<>(pageNum, pageSize);
        mapList = govAttrDto.getMapList();

        pageNum = (pageNum - 1) * pageSize;
        govAttrDto.setPageNum(pageNum);
        //??????????????????
        List<GovInfo> govInfos = govInfoMapper.getGovByAttrValueByPage(govAttrDto);
        //????????????
        Integer count = govInfoMapper.getGovCountByAttrValue(govAttrDto);
        pageResult.setTotal(count);
        /** =================================================================== */
        //        //?????????????????????
        List<GovInfoResult> resultRecords = new ArrayList<>();
        if (CollectionUtils.isEmpty(govInfos)){
            return pageResult;
        }
        //?????????????????????
        resultRecords=getGovInfoResultNew(govInfos,mapList,resultRecords);
        /** =================================================================== */
//        govInfos.stream().forEach(o -> {
//            GovInfoResult govInfoResult = getGovInfoResult(o, finalMapList);
//            resultRecords.add(govInfoResult);
//        });
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
                String value = "";
                //???????????????
                if (!CollectionUtils.isEmpty(attrValueList)) {
                    for (EntityAttrValue attrValue : attrValueList) {
                        if (ObjectUtils.isEmpty(value)) {
                            value = attrValue.getValue();
                        } else {
                            value = value + "," + attrValue.getValue();
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
        log.info("  >>>>  ??????,??????????????????????????????,dqCode=[{}],oldName=[{}],newOldName=[{}],status=[{}],remarks=[{}] <<<<  ", dqCode, oldName, newOldName, status, remark);
        if (ObjectUtils.isEmpty(oldName)) {
            return R.fail("????????????????????????");
        }
        if (ObjectUtils.isEmpty(status)) {
            if (ObjectUtils.isEmpty(newOldName)) {
                return R.fail("?????????????????????");
            }
            //?????????????????????????????????????????????
            Long count = nameHisMapper.selectCount(new QueryWrapper<EntityNameHis>().lambda()
                    .eq(EntityNameHis::getDqCode, dqCode)
                    .eq(EntityNameHis::getOldName, newOldName));
            if (count > 0) {
                return R.ok("?????????????????????????????????");
            }
        }
        //??????dqCode???????????????
        QueryWrapper<GovInfo> infoQuery = new QueryWrapper<>();
        GovInfo govInfo = govInfoMapper.selectOne(infoQuery.lambda().eq(GovInfo::getDqGovCode, dqCode));

        //?????????????????????
        String govNameHis = govInfo.getGovNameHis();
        String govNameHisRemarks = govInfo.getEntityNameHisRemarks();
        if (ObjectUtils.isEmpty(govNameHis)) {
            return R.fail("???????????????");
        }
        List<String> nameList = Arrays.asList(govNameHis.split(","));
        List<String> remarkList = Arrays.asList(govNameHisRemarks.split(";"));

        String newNameResult = "";
        String newRemarkResult = "";
        for (int i = 0; i < nameList.size(); i++) {
            String remarkVo = "";
            String nameVo = "";
            String hisName = nameList.get(i);

            //??????????????????????????????????????????
            if (oldName.equals(hisName)) {
                //?????????????????????????????????????????????
                EntityNameHis one = nameHisMapper.selectOne(new QueryWrapper<EntityNameHis>().lambda()
                        .eq(EntityNameHis::getDqCode, dqCode)
                        .eq(EntityNameHis::getOldName, oldName).last(" limit 1"));
                if (ObjectUtils.isEmpty(one)) {
                    continue;
                }
                one.setSource(2);
                // status ?????????????????????????????????
                if (!ObjectUtils.isEmpty(status)) {
                    one.setStatus(0);
                    //???????????????????????????
                    nameHisMapper.updateById(one);
                    continue;
                }
                //???????????????
                if (ObjectUtils.isEmpty(remark)) {
                    remark = "??????????????????";
                }
                one.setRemarks(remark).setOldName(newOldName);
                //?????????????????????
                nameHisMapper.updateById(one);
                nameVo = newOldName;
                remarkVo = TimeFormatUtil.getFormartDate(new Date()) + " " + SecurityUtils.getUsername() + " " + remark;
                if (ObjectUtils.isEmpty(remark)) {
                    remarkVo = remarkVo + "??????????????????";
                }
            } else {
                nameVo = hisName;
                if (remarkList.size() > i) {
                    remarkVo = remarkList.get(i);
                } else {
                    remarkVo = TimeFormatUtil.getFormartDate(new Date()) + " " + SecurityUtils.getUsername() + " ??????????????????";
                }
            }
            // ??????????????????????????????
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
        //???????????????????????????
        backInfo.setId(govInfo.getId()).setGovNameHis(newNameResult).setEntityNameHisRemarks(newRemarkResult);
        //??????????????????
        govInfoMapper.updateById(backInfo);
        return R.ok();
    }

    @Override
    public Map<String, Object> getOverview() {
        QueryWrapper<GovInfo> query = new QueryWrapper<>();
        Long count = govInfoMapper.selectCount(query);
        Long aLong = govInfoMapper.selectCount(query.lambda().eq(GovInfo::getStatus, 1));
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("invalid", aLong);
        result.put("unInvalid", count - aLong);
        return result;
    }

    @Override
    public Map<String, Object> getOverviewByGroup() {
        Long count = govInfoMapper.selectCount(new QueryWrapper<>());
//        ???????????????GVA???+000001????????????
        Integer JK = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_JK_CODE).size();
//        ???????????????GVB???+000001????????????
        Integer GX = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_GX_CODE).size();
//        ????????????GVC???+000001????????????
        Integer XQ = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_XQ_CODE).size();
//        ???????????????????????????GVZ???+000001????????????
        Integer QT = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_QT_CODE).size();

//        ??????????????????GV+?????????????????????
        List<GovInfo> govInfosList = govInfoMapper.selectCountByGroup(Common.DOV_INFO_TYPE_PRIVINCE_CODE);
        QueryWrapper<GovInfo> query = new QueryWrapper<>();
//        ??????
        List<GovInfo> provinceInfos = govInfoMapper.selectList(query.lambda().eq(GovInfo::getGovLevelBig, 1));
        query.clear();
        Integer province = provinceInfos.size();
//        ???
        List<String> dqCodeList = new ArrayList<>();
        Integer city = 0;
        if (!CollectionUtils.isEmpty(provinceInfos)) {
            provinceInfos.stream().forEach(o -> {
                dqCodeList.add(o.getDqGovCode());
            });
            List<GovInfo> cityList = govInfoMapper.selectList(query.lambda().in(GovInfo::getPreGovCode, dqCodeList));
            city = cityList.size();
        }
//        ???
        Integer area = govInfosList.size() - province - city;
//        ??????????????????GV+?????????????????????
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
     * ???????????????????????????????????? by??????
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
     * ?????????????????????
     */
    public static final String MORE_ENTITY_KPI_NAME = "name";
    /**
     * id
     */
    public static final String MORE_ENTITY_KPI_ID = "id";
    /**
     * ???????????????????????????
     */
    public static final String MORE_ENTITY_KPI_KEY = "key";
    /**
     * ????????????????????????
     */
    public static final String MORE_ENTITY_KPI_VALUE = "value";

    /**
     * GovInfo ????????? map,????????? ???????????????
     *
     * @param govInfo
     * @return Map<String, Object>
     * @author ?????????
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
     * ????????????????????????
     *
     * @return R
     * @author penTang
     * @date 2022/10/10 14:38
     */
    @Override
    public R getGovEntityResult(EntityOrGovByAttrVo entityOrGovByAttrVo) {
        Page<GovInfo> page = new Page<>(entityOrGovByAttrVo.getPageNum(), entityOrGovByAttrVo.getPageSize());
        LambdaQueryWrapper<GovInfo> eq;
        if (entityOrGovByAttrVo.getEntityName() == null || entityOrGovByAttrVo.getEntityName().equals("")) {

            eq = new LambdaQueryWrapper<GovInfo>();
        } else {
            eq = new LambdaQueryWrapper<GovInfo>().like(GovInfo::getGovName, entityOrGovByAttrVo.getEntityName());

        }
        Page<GovInfo> page1 = govInfoMapper.selectPage(page, eq);
        List<GovInfo> records = page1.getRecords();
        ArrayList<GovInfoBynameDto> govInfoBynameDtos = new ArrayList<>();
        for (GovInfo record : records) {
            GovInfoBynameDto govInfoBynameDto = new GovInfoBynameDto();
            ArrayList<HashMap<String, String>> maps = new ArrayList<>();
            List<Integer> proId = entityOrGovByAttrVo.getProId();
            //???????????????
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
     * ????????????-????????????-????????????
     *
     * @return R
     * @author ?????????
     * @date 2022/10/11 17:10
     */

    @Override
    public List<ParentLevelVo> getGovRange() {

        List<ParentLevelVo> parentLevelVo = new ArrayList<>();

//        ??????????????? 1 ??????????????? 2 ??????????????? 3 ??????????????? 4
        parentLevelVo = setAdministrationRegion(parentLevelVo);
//        ??????????????????
        parentLevelVo = setGegphZone(parentLevelVo);
//        ???????????????
        parentLevelVo = setEightEconomicsRegion(parentLevelVo);
//        19????????????
        parentLevelVo = setNineteenCityGroup(parentLevelVo);
//        ????????????
        parentLevelVo = setGovScale(parentLevelVo);
//        ????????????
        parentLevelVo = setGovGrading(parentLevelVo);
//        ??????????????????
        parentLevelVo = setCCity(parentLevelVo);
//        ????????????
        parentLevelVo = setProcincial(parentLevelVo);
//        ?????????
        parentLevelVo = setHundred(parentLevelVo);
        return parentLevelVo;
    }

    private List<ParentLevelVo> setGegphZone(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo gegphZone = new ParentLevelVo();
        gegphZone.setName(gegphZones);

        List<ParentLevelVo> list = new ArrayList<>();

        QueryWrapper<EntityAttrIntype> intypeQuery = new QueryWrapper<>();
        List<EntityAttrIntype> entityAttrIntypes = intypeMapper.selectList(intypeQuery.lambda().eq(EntityAttrIntype::getAttrId, 19));
        entityAttrIntypes.stream().forEach(o -> {
            ParentLevelVo parentValue = getParentValue(o.getValue(), o.getValue(), gegphZones);
            list.add(parentValue);
        });

        gegphZone.setValue(list);
        parentLevelVo.add(gegphZone);

        return parentLevelVo;
    }

    private List<ParentLevelVo> setHundred(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo hund = new ParentLevelVo();
        hund.setName(hundred);
        List<ParentLevelVo> list = new ArrayList<>();

        ParentLevelVo no = getParentValue(NO, 0, hundred);
        ParentLevelVo yes = getParentValue(YES, 1, hundred);
        ParentLevelVo notApply = getParentValue(NOT_APPLY, 2, hundred);

        list.add(yes);
        list.add(no);
        list.add(notApply);

        hund.setValue(list);
        parentLevelVo.add(hund);

        return parentLevelVo;

    }

    private List<ParentLevelVo> setProcincial(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo province = new ParentLevelVo();
        province.setName(provincial);
        List<ParentLevelVo> list = new ArrayList<>();

        ParentLevelVo no = getParentValue(NO, 0, province.getName());
        ParentLevelVo yes = getParentValue(YES, 1, province.getName());
        ParentLevelVo notApply = getParentValue(NOT_APPLY, 2, province.getName());

        list.add(yes);
        list.add(no);
        list.add(notApply);

        province.setValue(list);
        parentLevelVo.add(province);

        return parentLevelVo;

    }

    private List<ParentLevelVo> setCCity(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo cCity = new ParentLevelVo();
        cCity.setName(CCity);
        List<ParentLevelVo> list = new ArrayList<>();
        ParentLevelVo no = getParentValue(NO, 0, CCity);
        ParentLevelVo yes = getParentValue(YES, 1, CCity);
        ParentLevelVo notApply = getParentValue(NOT_APPLY, 2, CCity);

        list.add(yes);
        list.add(no);
        list.add(notApply);

        cCity.setValue(list);
        parentLevelVo.add(cCity);

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
                case gegphZones:
                    govAttrByDto.setGegphZone(value);
                    break;
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
                    govAttrByDto.setHundred(value);
                    break;
                case CCity:
                    govAttrByDto.setCCity(value);
                    break;
                case provincial:
                    govAttrByDto.setProvincial(value);
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
        //????????????
        if (!CollectionUtils.isEmpty(govInfos)) {
            total = govInfos.size();
        }
        //????????????
        if (!CollectionUtils.isEmpty(listGovMap.get(1))) {
            province = listGovMap.get(1).size();
        }
        //????????????
        if (!CollectionUtils.isEmpty(listGovMap.get(2))) {
            city = listGovMap.get(2).size();
        }
        //????????????
        if (!CollectionUtils.isEmpty(listGovMap.get(3))) {
            area = listGovMap.get(3).size();
        }
        //??????????????????
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
        // ???????????????
        List<List<Object>> sheetDataList = new ArrayList<>();
        List<Object> head = Arrays.asList("????????????", "????????????????????????", "???????????????????????????",
                "??????????????????????????????", "?????????????????????????????????", "??????????????????",
                "??????????????????????????????-??????", "??????????????????????????????-??????", "???????????? 0.?????? 1.??????");
        sheetDataList.add(head);
        for (GovInfo govInfo : govInfoList) {
            //???????????????
            List<Object> sheetData = new ArrayList<>();
            //????????????
            sheetData.add(govInfo.getGovName());
            sheetData.add(govInfo.getGovCode());
            sheetData.add(govInfo.getDqGovCode());
            //??????????????????
            sheetData.add(govInfo.getPreCode());
            sheetData.add(govInfo.getPreGovCode());
            sheetData.add(govInfo.getPreGovName());
            Integer govLevelBig = govInfo.getGovLevelBig();
            Integer govLevelSmall = govInfo.getGovLevelSmall();
            //??????????????????????????????????????????????????????
            //????????????
            if (!CollectionUtils.isEmpty(levelMap.keySet()) && !ObjectUtils.isEmpty(govLevelBig) && !CollectionUtils.isEmpty(levelMap.get(Long.valueOf(govLevelBig)))) {
                sheetData.add(levelMap.get(Long.valueOf(govLevelBig)).get(0).getName());
            } else {
                sheetData.add(govLevelBig);
            }
            //????????????
            if (!CollectionUtils.isEmpty(levelMap.keySet()) && !ObjectUtils.isEmpty(govLevelSmall) && !CollectionUtils.isEmpty(levelMap.get(Long.valueOf(govLevelSmall)))) {
                sheetData.add(levelMap.get(Long.valueOf(govLevelSmall)).get(0).getName());
            } else {
                sheetData.add(govLevelSmall);
            }
            sheetData.add(govInfo.getStatus());
            //???????????????
            sheetDataList.add(sheetData);
        }
        // ????????????
        ExcelUtils.export(response, "???????????????", sheetDataList);
        log.info("???????????????????????????");
    }

    @Override
    public void updateGovInfosByPreCode() {
        List<GovInfo> govInfoList = govInfoMapper.selectList(new QueryWrapper<GovInfo>());
        for (GovInfo govInfo : govInfoList) {
            String preGovCode = govInfo.getPreGovCode();
            //???????????? code ?????????????????????????????????
            if (ObjectUtils.isEmpty(preGovCode)) {
                continue;
            }
            GovInfo preGov = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, preGovCode).last(" limit 1"));
            //?????????????????????????????????????????????
            if (ObjectUtils.isEmpty(preGov)) {
                continue;
            }
            //???????????????????????????????????????????????????????????????
            if (!ObjectUtils.isEmpty(govInfo.getPreCode()) && !ObjectUtils.isEmpty(govInfo.getPreGovName()) &&
                    govInfo.getPreCode().equals(preGov.getGovCode()) && govInfo.getPreGovName().equals(preGov.getGovName())) {
                continue;
            }
            //???????????????????????? ??????????????????????????????code ??? ??????????????????
            govInfo.setPreCode(preGov.getGovCode()).setPreGovName(preGov.getGovName());
            govInfoMapper.updateById(govInfo);
        }
    }

    @Override
    public R getGovInfoByLevel(Integer bigLevel, Integer smallLevel) {
        LambdaQueryWrapper<GovInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (!ObjectUtils.isEmpty(bigLevel)) {
            queryWrapper.eq(GovInfo::getGovLevelBig, (bigLevel - 1));
        }
//        if (!ObjectUtils.isEmpty(smallLevel)){
//            queryWrapper.eq(GovInfo::getGovLevelSmall,smallLevel);
//        }
        return R.ok(govInfoMapper.selectList(queryWrapper));
    }

    //??????????????????--????????????
    private List<ParentLevelVo> setGovGrading(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo govGrade = new ParentLevelVo();
        govGrade.setName(govGrading);
        List<ParentLevelVo> list = new ArrayList<>();

        QueryWrapper<EntityAttrIntype> intypeQuery = new QueryWrapper<>();
        List<EntityAttrIntype> entityAttrIntypes = intypeMapper.selectList(intypeQuery.lambda().eq(EntityAttrIntype::getAttrId, 24));
        entityAttrIntypes.stream().forEach(o -> {
            ParentLevelVo parentValue = getParentValue(o.getValue(), o.getValue(), govGrade.getName());
            list.add(parentValue);
        });

        govGrade.setValue(list);
        parentLevelVo.add(govGrade);

        return parentLevelVo;

    }

    //??????????????????--????????????
    private List<ParentLevelVo> setGovScale(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo govScales = new ParentLevelVo();
        List<ParentLevelVo> list = new ArrayList<>();
        govScales.setName(govScale);

        QueryWrapper<EntityAttrIntype> intypeQuery = new QueryWrapper<>();
        List<EntityAttrIntype> entityAttrIntypes = intypeMapper.selectList(intypeQuery.lambda().eq(EntityAttrIntype::getAttrId, 23));
        entityAttrIntypes.stream().forEach(o -> {
            ParentLevelVo parentValue = getParentValue(o.getValue(), o.getValue(), govScale);
            list.add(parentValue);
        });

        govScales.setValue(list);
        parentLevelVo.add(govScales);

        return parentLevelVo;

    }

    //??????????????????--??????19????????????
    private List<ParentLevelVo> setNineteenCityGroup(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo nineteenCityGroup = new ParentLevelVo();
        nineteenCityGroup.setName(nineteenCity);

        List<ParentLevelVo> list = new ArrayList<>();

        QueryWrapper<EntityAttrIntype> intypeQuery = new QueryWrapper<>();
        List<EntityAttrIntype> entityAttrIntypes = intypeMapper.selectList(intypeQuery.lambda().eq(EntityAttrIntype::getAttrId, 21));
        entityAttrIntypes.stream().forEach(o -> {
            ParentLevelVo parentValue = getParentValue(o.getValue(), o.getValue(), nineteenCity);
            list.add(parentValue);
        });

        nineteenCityGroup.setValue(list);
        parentLevelVo.add(nineteenCityGroup);

        return parentLevelVo;
    }

    //??????????????????--?????????????????????
    private List<ParentLevelVo> setEightEconomicsRegion(List<ParentLevelVo> parentLevelVo) {
        ParentLevelVo eightEconomicsRegion = new ParentLevelVo();
        eightEconomicsRegion.setName(eightER);

        List<ParentLevelVo> list = new ArrayList<>();

        QueryWrapper<EntityAttrIntype> intypeQuery = new QueryWrapper<>();
        List<EntityAttrIntype> entityAttrIntypes = intypeMapper.selectList(intypeQuery.lambda().eq(EntityAttrIntype::getAttrId, 20));
        entityAttrIntypes.stream().forEach(o -> {
            ParentLevelVo parentValue = getParentValue(o.getValue(), o.getValue(), eightER);
            list.add(parentValue);
        });

        eightEconomicsRegion.setValue(list);
        parentLevelVo.add(eightEconomicsRegion);

        return parentLevelVo;
    }

    //??????????????????--????????????????????????
    private List<ParentLevelVo> setAdministrationRegion(List<ParentLevelVo> parentLevelVo) {

        QueryWrapper<GovLevel> levelQuery = new QueryWrapper<>();
        Map<Long, List<GovLevel>> levelMap = govLevelMapper.selectList(levelQuery.lambda().isNotNull(GovLevel::getParentId))
                .stream().collect(Collectors.groupingBy(GovLevel::getParentId));
        for (Long parentId : levelMap.keySet()) {
            if (parentId == 1L) {
                ParentLevelVo province = new ParentLevelVo();
                province.setName(isProvince);

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
                city.setName(isCity);

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
                country.setName(isCounty);

                List<GovLevel> govLevels = levelMap.get(parentId);
                List<ParentLevelVo> list = new ArrayList<>();
                for (GovLevel level : govLevels) {
                    ParentLevelVo parentValue = getParentValue(level.getName(), level.getId(), country.getName());
                    list.add(parentValue);
                }

                country.setValue(list);
                parentLevelVo.add(country);

            } else if (parentId == 4L) {
                ParentLevelVo isJKG = new ParentLevelVo();
                isJKG.setName(isJKGX);

                List<GovLevel> govLevels = levelMap.get(parentId);
                List<ParentLevelVo> list = new ArrayList<>();
                for (GovLevel level : govLevels) {
                    ParentLevelVo parentValue = getParentValue(level.getName(), level.getId(), isJKG.getName());
                    list.add(parentValue);
                }

                isJKG.setValue(list);
                parentLevelVo.add(isJKG);
            }
        }
        return parentLevelVo;
    }

    //??????????????????????????????
    public ParentLevelVo getParentValue(String name, Object value, String preName) {
        ParentLevelVo parentLevelVo = new ParentLevelVo();
        parentLevelVo.setName(name).setSend(value).setPreName(preName);
        return parentLevelVo;
    }

    @Override
    public List<GovInfo> selectList() {
        LambdaQueryWrapper<GovInfo> eq = new LambdaQueryWrapper<GovInfo>().eq(GovInfo::getStatus, 1);
        return list(eq);
    }


}
