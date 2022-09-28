package com.deloitte.crm.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 冉浩岑
 * @date 2022/09/25 12:42
 */
@Component
public class HttpUtils {
    /**
     * 获取客户端用户信息
     * @return String
     * @author 冉浩岑
     * @date 2022/9/25 12:37
     */
    public static String getRemoter(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String remoteUser = request.getRemoteUser();//取得客户端的用户
        return remoteUser;
    }
}
