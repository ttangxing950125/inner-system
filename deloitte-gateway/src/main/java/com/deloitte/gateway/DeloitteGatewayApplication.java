package com.deloitte.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 网关启动程序
 * 
 * @author lipeng
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DeloitteGatewayApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DeloitteGatewayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  德勤网关启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
