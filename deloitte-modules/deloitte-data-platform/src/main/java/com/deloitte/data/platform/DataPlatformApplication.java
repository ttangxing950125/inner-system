package com.deloitte.data.platform;

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
public class DataPlatformApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(DataPlatformApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  数据资产平台服务启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
