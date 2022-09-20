package com.deloitte.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.deloitte.common.security.annotation.EnableCustomConfig;
import com.deloitte.common.security.annotation.EnableRyFeignClients;
import com.deloitte.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 代码生成
 * 
 * @author lipeng
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringBootApplication
public class DeloitteGenApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DeloitteGenApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  德勤代码生成模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
