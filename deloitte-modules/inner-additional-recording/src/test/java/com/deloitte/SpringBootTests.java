package com.deloitte;

import com.alibaba.fastjson.JSON;
import com.deloitte.additional.recording.InnerAdditionalRecordingApplication;
import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.vo.DataListPageTataiVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

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

    @Test
    public void test() {
        final List<DataListPageTataiVo> dataListPageTataiVos = prsModelQualService.queryByPageStatsdetail("M_029", "2021", "第一创业");
        System.out.println(JSON.toJSONString(dataListPageTataiVos));
    }
}
