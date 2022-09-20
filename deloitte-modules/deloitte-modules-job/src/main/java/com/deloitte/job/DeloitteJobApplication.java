package com.deloitte.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.deloitte.common.security.annotation.EnableCustomConfig;
import com.deloitte.common.security.annotation.EnableRyFeignClients;
import com.deloitte.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 定时任务
 * 
 * @author lipeng
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringBootApplication
public class DeloitteJobApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DeloitteJobApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  德勤定时任务模块启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}
