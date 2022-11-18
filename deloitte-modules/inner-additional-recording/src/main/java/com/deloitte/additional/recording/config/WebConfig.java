package com.deloitte.additional.recording.config;


import com.deloitte.additional.recording.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @创建人 tangx
 * @创建时间 2022/11/15
 * @描述 web配置类
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String[] excludeUrls = {"/sysUser/login", "/sysUser/logout", "/sysUser/refresh",
            "/swagger-ui.html", "/swagger-resources/**", "webjars/**", "/login", "/v2/**"};

    /**
     * 添加Web项目的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 对所有访问路径，都通过MyInterceptor类型的拦截器进行拦截
        registry.addInterceptor(getMyInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls)
                .order(-10);
        //放行登录页，登陆操作，静态资源
    }

    /**
     * 自定义请求头拦截器
     */
    public MyInterceptor getMyInterceptor() {
        return new MyInterceptor();
    }
}
