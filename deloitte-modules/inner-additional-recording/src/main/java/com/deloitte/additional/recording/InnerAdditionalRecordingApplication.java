package com.deloitte.additional.recording;

import com.deloitte.common.security.annotation.EnableCustomConfig;
import com.deloitte.common.security.annotation.EnableRyFeignClients;
import com.deloitte.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.scheduling.annotation.EnableAsync;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
@MapperScan("com.deloitte.additional.recording.mapper")
public class InnerAdditionalRecordingApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnerAdditionalRecordingApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  内部系统补录服务启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
