package com.deloitte;

import com.alibaba.fastjson.JSON;
import com.deloitte.additional.recording.InnerAdditionalRecordingApplication;
import com.deloitte.additional.recording.mapper.PrsProjectVersionsMapper;
import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.vo.DataListCustomEntityInfoVo;
import com.deloitte.additional.recording.vo.DataListFindPrsProjectVersionsByYearVo;
import com.deloitte.additional.recording.vo.DataListPageTataiVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/08/15:44
 * @Description:
 */
@SpringBootTest(classes = InnerAdditionalRecordingApplication.class)
public class SpringBootTests {
    @Resource
    private PrsModelQualService prsModelQualService;
    @Resource
    private PrsProjectVersionsMapper prsProjectVersionsMapper;

    @Test
    public void test() {
        //24
//        final List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetailNoSql("M_029", "2021", "第一创业");
        final List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetail("M_029", "2021", "第一创业");
        System.out.println(JSON.toJSONString(dataListPageTataiVos));
    }

    /**
     *
     */
    @Test
    public void test2() {
        final List<DataListFindPrsProjectVersionsByYearVo> dataListFindPrsProjectVersionsByYearVos = prsProjectVersionsMapper.finPrsProjectVersionsByYear(new Integer[]{2020,2021});
        final Map<String, List<DataListFindPrsProjectVersionsByYearVo>> collect = dataListFindPrsProjectVersionsByYearVos.stream().collect(Collectors.groupingBy(DataListFindPrsProjectVersionsByYearVo::getName));
        System.out.println(JSON.toJSONString(collect));
    }

    @Test
    public void test3() {
        final List<DataListCustomEntityInfoVo> results = prsProjectVersionsMapper.getCustomEntityInfoByVersionIdAndModelId("2021", "Q_000044", "449");
//        final Map<String, List<DataListFindPrsProjectVersionsByYearVo>> collect = dataListFindPrsProjectVersionsByYearVos.stream().collect(Collectors.groupingBy(DataListFindPrsProjectVersionsByYearVo::getName));
        System.out.println(JSON.toJSONString(results));
    }
}
