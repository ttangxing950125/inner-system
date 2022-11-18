package com.deloitte.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.deloitte.common.security.annotation.EnableCustomConfig;
import com.deloitte.common.security.annotation.EnableRyFeignClients;
import com.deloitte.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 系统模块
 * 
 * @author lipeng
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class DeloitteSystemApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DeloitteSystemApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  德勤系统模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
