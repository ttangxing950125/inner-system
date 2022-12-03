package com.deloitte.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.deloitte.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 文件服务
 *
 * @author lipeng
 */
@EnableCustomSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DeloitteFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeloitteFileApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  德勤文件服务模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
