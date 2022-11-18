package com.deloitte.modules.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * 监控中心
 * 
 * @author lipeng
 */
@EnableAdminServer
@SpringBootApplication
public class DeloitteMonitorApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DeloitteMonitorApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  监控中心启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
