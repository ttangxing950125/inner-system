package com.deloitte.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.deloitte.common.security.annotation.EnableRyFeignClients;

/**
 * 认证授权中心
 * 
 * @author lipeng
 */
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DeloitteAuthApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DeloitteAuthApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  认证授权中心启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
