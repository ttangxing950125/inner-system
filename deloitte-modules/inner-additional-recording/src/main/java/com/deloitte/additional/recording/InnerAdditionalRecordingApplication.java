package com.deloitte.additional.recording;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.deloitte.common.security.annotation.EnableCustomConfig;
import com.deloitte.common.security.annotation.EnableRyFeignClients;
import com.deloitte.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 系统模块
 * 
 * @author lipeng
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@EnableAsync
public class InnerAdditionalRecordingApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(InnerAdditionalRecordingApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  内部系统补录服务启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
