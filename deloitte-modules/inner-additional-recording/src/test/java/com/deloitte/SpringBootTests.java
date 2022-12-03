package com.deloitte;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.InnerAdditionalRecordingApplication;
import com.deloitte.additional.recording.application.service.SysGroupUserRoleService;
import com.deloitte.additional.recording.controller.CommonController;
import com.deloitte.additional.recording.domain.*;
import com.deloitte.additional.recording.mapper.PrsProjectVersionsMapper;
import com.deloitte.additional.recording.request.SysUserRequest;
import com.deloitte.additional.recording.vo.DataListCustomEntityInfoVo;
import com.deloitte.additional.recording.vo.DataListFindPrsProjectVersionsByYearVo;
import com.deloitte.additional.recording.vo.DataListPageTataiVo;
import com.deloitte.additional.recording.vo.recording.ChooseDistributionPeriodCollocterVo;
import com.deloitte.additional.recording.vo.user.SysUserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.additional.recording.dto.GetTaskTargetvalPageListDto;
import com.deloitte.additional.recording.mapper.*;
import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.dto.*;
import com.deloitte.additional.recording.mapper.EntityInfoMapper;
import com.deloitte.additional.recording.mapper.SysDictDataMapper;
import com.deloitte.additional.recording.mapper.SysDictTypeMapper;
import com.deloitte.additional.recording.service.*;
import com.deloitte.additional.recording.service.biz.DataListBizComponentService;
import com.deloitte.common.core.domain.R;
import com.deloitte.system.api.domain.SysDictData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/08/15:44
 * @Description:
 */
@Slf4j
@SpringBootTest(classes = InnerAdditionalRecordingApplication.class)
public class SpringBootTests {
    @Resource
    private PrsModelQualService prsModelQualService;
    @Resource
    private PrsProjectVersionsMapper prsProjectVersionsMapper;
    @Resource
    private DataListBizComponentService dataListBizService;
    @Resource
    private EntityInfoMapper entityInfoMapper;
    @Resource
    private PrsQualDataService prsQualDataService;
    @Resource
    private SysDictTypeMapper sysDictTypeMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private PrsVerMasEntityService prsVerMasEntityService;
    @Resource
    private EntityInfoService entityInfoService;

    @Autowired
    private SysGroupUserRoleService sysGroupUserRoleService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService roleService;
    @Resource
    private CommonController commonController;
    @Resource
    private BasEvdInfoService basEvdInfoService;
    @Resource
    private SysUserMapper sysUserMapper;


    @Test
    public void test() {
        //24
//        final List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetailNoSql("M_029", "2021", "第一创业");
//        final List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetail("M_029", "2021", "第一创业");
//        System.out.println(JSON.toJSONString(dataListPageTataiVos));
    }

    @Resource
    private PrsModelMasterService prsModelMasterService;
    @Resource
    private PrsProjectVersionsService prsProjectVersionsService;
    @Resource
    private SysDictDataService sysDictDataService;
    @Resource
    private FFileService fFileService;

    @Resource
    private BasEvdTaskInfoService taskInfoService;
    @Resource
    private PrsModelQualService qualService;


//
//    @Test
//    public void test() {
//        //24
////        final List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetailNoSql("M_029", "2021", "第一创业");
//        final List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetail("M_029", "2021", null);
//        System.out.println(JSON.toJSONString(dataListPageTataiVos));
//    }

    /**
     *
     */
    @Test
    public void test2() {

    }

    @Test
    public void test3() {
        final List<DataListCustomEntityInfoVo> results = prsProjectVersionsMapper.getCustomEntityInfoByVersionIdAndModelId("2021", "Q_000044", "449");
//        final Map<String, List<DataListFindPrsProjectVersionsByYearVo>> collect = dataListFindPrsProjectVersionsByYearVos.stream().collect(Collectors.groupingBy(DataListFindPrsProjectVersionsByYearVo::getName));
        System.out.println(JSON.toJSONString(results));
    }

    @Test
    public void userPage() {

        Page<SysUserVO> page = sysGroupUserRoleService.page(null, "张三", "1", "1,2", "1,2", 1, 10);

        System.out.println(page.getRecords());
    }

    @Test
    public void addUser() {
        SysUserRequest saveRequest = new SysUserRequest();
        saveRequest.setEmail("1164490300@qq.com");
        saveRequest.setSex("男");
        saveRequest.setGroupid("1,2");
        saveRequest.setName("张三");
        saveRequest.setRoles("1,2");
        saveRequest.setStatus("1");
        sysGroupUserRoleService.addSave("张三", "1164490300@qq.com", "男", "1", "1,2", "1,2", saveRequest.getValidTime());
    }

    @Test
    public void resetPassword() {
        sysUserService.resetPassword(1383469057);

    }

    @Test
    public void modfiysave() {
        //sysGroupUserRoleService.modify(1383469057, "李四", "207425690@qq.com", "男", "1", "1", "2");
    }

    @Test
    public void rolePage() {

    }

    public void test4() {
         /*List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetail("M_029", "2021", "第一创业");
        StringBuilder sql =new StringBuilder();
        sql.append("select prs_qual_data.time_value as year, entity_info.id entityId,entity_info.entity_code entityCode,bas_entity_info.entity_name entityName, ");
        for (int i = 0; i < dataListPageTataiVos.size(); i++) {
            DataListPageTataiVo modelQual=dataListPageTataiVos.get(i);
            sql.append(" max(case "+modelQual.getId()+" when qual_id then if(cast(qual_value as signed)>=0,qual_value,'N/A') else NULL end ) "+modelQual.getId()+"_id,");
            sql.append(modelQual.getId()+" "+modelQual.getQualCode()+",");
        }*/
        GetTaskTargetvalPageListDto dto = new GetTaskTargetvalPageListDto();
//        dto.setName(null);
        dto.setYear("2021");
        dto.setModelCode("M_001");
        final Object o = dataListBizService.queryByPage(dto);
        System.out.println(JSON.toJSONString(o));
    }

    @Test
    public void test05() {
        GetTaskTargetvalPageListDto dto = new GetTaskTargetvalPageListDto();
//        dto.setName(null);
        dto.setYear("2021");
        dto.setModelCode("M_001");
        final Object o = dataListBizService.queryByPage(dto);
        System.out.println(o);
    }

    @Test
    public void test06() {
        List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetailNoSql("M_002", "2021", 146);
        List<String> collect = dataListPageTataiVos.stream().map(DataListPageTataiVo::getQualCode).collect(Collectors.toList());
        Page<EntityInfo> page = new Page<>(1, 5);
        final IPage<EntityInfo> entityInfoIPage = entityInfoMapper.queryByPage(page, 146, "", "M_002");
        final List<EntityInfo> records = entityInfoIPage.getRecords();
        final List<EntityInfo> records2 = new ArrayList<>();

        for (EntityInfo record : records) {
            List<PrsQualData> prsQualData = prsQualDataService.getBaseMapper().selectList(new LambdaQueryWrapper<PrsQualData>().eq(PrsQualData::getEntityCode, record.getEntityCode()).in(PrsQualData::getQualCode, collect));
            if (CollUtil.isNotEmpty(prsQualData)) {
                List list = new ArrayList();
                List<String> qualCodeList = new ArrayList<>();
                for (PrsQualData prsQualDatum : prsQualData) {
                    final PrsModelQual prsModelQual = prsModelQualService.getBaseMapper().selectOne(new LambdaQueryWrapper<PrsModelQual>().eq(PrsModelQual::getQualCode, prsQualDatum.getQualCode()));
                    Map<String, Object> mmpp = new HashMap<>();
                    Map<String, Object> qualCodeHashMap = new HashMap<>();
                    mmpp.put(prsModelQual.getId() + "_id", Optional.ofNullable(prsQualDatum.getQualValue()).orElse("N/A"));
                    qualCodeList.add(prsModelQual.getQualCode());
                    list.add(mmpp);
                    record.setMaps(list);
                }
                log.info("获取的指标信息:>>>:{}", JSON.toJSONString(collect));
                collect.removeAll(qualCodeList);
                if (CollUtil.isNotEmpty(collect)) {
                    for (String s : collect) {
                        final PrsModelQual prsModelQual = prsModelQualService.getBaseMapper().selectOne(new LambdaQueryWrapper<PrsModelQual>().eq(PrsModelQual::getQualCode, s));
                        Map<String, Object> otherMap = new HashMap<>();
                        otherMap.put(prsModelQual.getId() + "_id", "N/A");
                        record.getMaps().add(otherMap);
                    }
                    collect = dataListPageTataiVos.stream().map(DataListPageTataiVo::getQualCode).collect(Collectors.toList());
                }


            } else {
                ArrayList<Map<String, Object>> otherMapList = new ArrayList<>();
                for (String qualCode : collect) {
                    final PrsModelQual prsModelQual = prsModelQualService.getBaseMapper().selectOne(new LambdaQueryWrapper<PrsModelQual>().eq(PrsModelQual::getQualCode, qualCode));
                    Map<String, Object> otherMap = new HashMap<>();
                    otherMap.put(prsModelQual.getId() + "_id", "N/A");
                    otherMapList.add(otherMap);
                }
                record.setMaps(otherMapList);
            }
            records2.add(record);
        }
        entityInfoIPage.setRecords(records2);


        System.out.println(JSON.toJSONString(entityInfoIPage));
    }

    @Test
    public void test07() {
        final ExcelReader reader = ExcelUtil.getReader("E:\\sys_linkage.xlsx", 0);
        List<List<Object>> read = reader.read(1);
        List<SysDictData> sysDictDataList = new ArrayList<>();
        for (List<Object> obj : read) {
            HashMap<String, Object> sysDictDataMap = new HashMap<>();
            SysDictData data = new SysDictData();
            data.setDictLabel(obj.get(4).toString());
            data.setDictValue(obj.get(5).toString());
            data.setRemark(obj.get(7) == null ? null : obj.get(7).toString());
            data.setDictType(obj.get(2).toString());
            sysDictDataList.add(data);
        }
//        final Map<String, List<SysDictData>> collect = sysDictDataList.stream().collect(Collectors.groupingBy(SysDictData::getDictType));
        for (SysDictData sysDictData : sysDictDataList) {
            sysDictDataMapper.insert(sysDictData);
        }

    }

    @Test
    public void fsdf() {
        LambdaQueryWrapper<EntityInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EntityInfo::getId, 1);
        Page<EntityInfo> page = new Page<>(1, 5);

        final Page<EntityInfo> entityInfoPage = entityInfoMapper.selectPage(page, wrapper);
        System.out.println("结果：" + JSON.toJSONString(entityInfoPage));
    }

    @Transactional
    @Test
    public void test09() {
        SysRole role = new SysRole();
        role.setName("审核llll");
        role.setStatus("1");
        sysRoleMapper.insert(role);
        int i = 1 / 0;
    }

    @Test
    public void test10() {
        SysRole role = new SysRole();
        role.setName("审核llll");
        role.setStatus("1");
        System.out.println(sysRoleMapper.selectList(null));
//        int i=1/0;
    }

    @Test
    public void queryPrincipalManifestPageTest() {
        MainBodyPageDto mainBodyDto = new MainBodyPageDto();
        mainBodyDto.setPageNum(1);
        mainBodyDto.setPagesize(10);
        //mainBodyDto.setList((byte)1);
        //mainBodyDto.setEntityBondTag((byte)1);
        //mainBodyDto.setStatus((byte)1);
        //mainBodyDto.setExposureTo("重工业");
        //mainBodyDto.setVersionName("华安版");
        // mainBodyDto.setTimeValue("2020");
        IPage<PrincipalManifestVo> principalManifestDtoIPage = entityInfoService.queryPrincipalManifestPage(mainBodyDto);
        System.out.println("结果：" + JSON.toJSONString(principalManifestDtoIPage));
    }

    @Test
    public void updateIncrStatusTest() {
        prsVerMasEntityService.updateIncrStatus(25848, 0);
    }


    @Test
    public void queryVersionTest() {
        R version = prsProjectVersionsService.getVersion();
        System.out.println("结果：" + JSON.toJSONString(version));
    }

    /**
     * 查询敞口
     */
    @Test
    public void queryExposureToList() {
        R r = prsModelMasterService.queryExposureToList();
        System.out.println("结果：" + JSON.toJSONString(r));
    }

    @Test
    public void finAllsysDictDataTest() {
        List<Map<String, Object>> maps = sysDictDataService.finAllsysDictData();
        List<String> timeValueList = CollectionUtils.isEmpty(maps) ? maps.get(0).keySet().stream().map(String::valueOf).collect(Collectors.toList()) : null;

    }

    @Test
    public void queryReportListTest() {

//        List<FFile> fFileList = fFileService.getFFileList();
//        System.out.println("结果："+JSON.toJSONString(fFileList));
        R r = fFileService.queryReportList("111", 22);
        System.out.println("结果：" + JSON.toJSONString(r));
    }

    @Test
    public void getEvidenceDistributionListTest() {
        EvidenceDistributionPageDto distributionPageDto = new EvidenceDistributionPageDto();
        distributionPageDto.setPageNum(1);
        distributionPageDto.setPagesize(10);
        R evidenceDistributionList = basEvdInfoService.getEvidenceDistributionList(distributionPageDto);
        System.out.println("结果：" + JSON.toJSONString(evidenceDistributionList));
    }

    @Test
    public void updateevidenceBatchTest() {
        EvidenceBatchDto batchDto = new EvidenceBatchDto();
        batchDto.setTaskInfoIds(Arrays.asList(1));
        batchDto.setCollocterId(736);
        taskInfoService.updatEevidenceBatch(batchDto);

    }

    @Test
    public void comboBoxTest() {
        System.out.println("结果：" + JSON.toJSONString(commonController.comboBox()));
    }

    @Resource
    private BasRecordingTaskInfoMapper basRecordingTaskInfoMapper;

    @Test
    public void test0001() {
        List<ChooseDistributionPeriodCollocterVo> periodCollocterVoList = sysUserMapper.chooseDistributionPeriodCollocter("补录人员");
        final List<Integer> collect = periodCollocterVoList.stream().collect(Collectors.groupingBy(ChooseDistributionPeriodCollocterVo::getSysUserId)).keySet().stream().collect(Collectors.toList());
        for (Integer integer : collect) {

            final List<BasRecordingTaskInfo> basRecordingTaskInfos = basRecordingTaskInfoMapper.selectList(new LambdaQueryWrapper<BasRecordingTaskInfo>().
                    eq(BasRecordingTaskInfo::getPeriodReportStatus, 1)
                    .eq(BasRecordingTaskInfo::getPeriodCollocter, integer));
            List<BasRecordingTaskInfo> periodCollocterTaskCountList = basRecordingTaskInfos;


            for (ChooseDistributionPeriodCollocterVo chooseDistributionPeriodCollocterVo : periodCollocterVoList) {
                if (chooseDistributionPeriodCollocterVo.getSysUserId() == integer) {
                    chooseDistributionPeriodCollocterVo.setPeriodCollocterTaskCount(periodCollocterTaskCountList.size());
                }
            }
        }
        System.out.println(JSON.toJSONString(collect));
    }
    @Resource
    private BasRecordingTableService basRecordingTableService;
    @Test
    public  void test00001(){
        final List<BasRecordingTable> allRecordingTable = basRecordingTableService.getAllRecordingTable();
        System.out.println(JSON.toJSONString(allRecordingTable));
    }
    @Test
    public void  test000001(){
        List<Map<String, Object>> maps = sysDictDataService.finAllsysDictData();
        List<String> timeValueList = CollectionUtils.isEmpty(maps) ? maps.get(0).keySet().stream().map(String::valueOf).collect(Collectors.toList()) : null;
//        map.put("year", timeValueList);
        System.out.println(timeValueList);
    }


}
