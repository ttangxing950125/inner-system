package com.deloitte.crm;

import com.alibaba.fastjson.JSON;
import com.deloitte.crm.mapper.EntityCaptureSpeedMapper;
import com.deloitte.crm.service.SendEmailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/10/9
 */
@SpringBootTest
public class SendEmailTest {
    @Resource
    private SendEmailService sendEmailService;
    @Resource
    EntityCaptureSpeedMapper entityCaptureSpeedMapper;

    @Test
    void testSend() {
        sendEmailService.SendEmail(4, "新增主体3个待确认",
                "今日wind导入任务已完成，平台捕获3个疑似新增主体需要确认。\n" +
                        "请尽快登陆平台完成相关任务。");
    }

    @Test
    void test02() {
        System.out.println(JSON.toJSONString(entityCaptureSpeedMapper.search("哈尔滨")));
    }

}
