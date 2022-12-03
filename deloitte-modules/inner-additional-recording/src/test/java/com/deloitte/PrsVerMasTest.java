package com.deloitte;

import com.deloitte.additional.recording.InnerAdditionalRecordingApplication;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.service.PrsVerMasEntityService;
import com.deloitte.additional.recording.service.PrsVersionMasterService;
import com.deloitte.common.redis.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/11/28
 */
@SpringBootTest(classes = InnerAdditionalRecordingApplication.class)
public class PrsVerMasTest {

    @Resource
    private PrsVerMasEntityService verMasEntityService;

    @Resource
    private RedisService redisService;

    @Test
    void testPullCrm(){
        verMasEntityService.pullCrmEntity(144);
    }

}
