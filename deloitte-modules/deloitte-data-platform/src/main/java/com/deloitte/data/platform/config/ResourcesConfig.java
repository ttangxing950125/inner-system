package com.deloitte.data.platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * 通用映射配置
 * 
 * @author lipeng
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer
{

    /**
     * 开启跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路由
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 设置允许的方法
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}