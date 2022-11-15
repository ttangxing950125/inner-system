package com.deloitte.additional.recording.interceptor;

/**
 * @创建人 tangx
 * @创建时间 2022/11/15
 * @描述
 */

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.deloitte.additional.recording.domain.SysUser;
import com.deloitte.additional.recording.util.MetaSecurity;
import com.deloitte.common.core.constant.TokenConstants;
import com.deloitte.common.core.exception.auth.NotLoginException;
import com.deloitte.common.core.utils.JwtUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * 自定义拦截器类
 */
public class MyInterceptor implements HandlerInterceptor {
    /**
     * 访问控制器方法前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println(new Date() + "--preHandle:" + request.getRequestURL());
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //检验token
        String token = request.getHeader(TokenConstants.AUTHENTICATION);
        if (StringUtils.isBlank(token)) {
            throw new NotLoginException("token不能为空");
        }
        if (!JwtUtil.verify(token)) {
            throw new NotLoginException("用户未授权");
        }
        //校验用户有效性
        checkValidTime();
        return true;
    }

    private static void checkValidTime() {
        SysUser user = MetaSecurity.getLoginUser();
        if (user != null) {
            //判断是不是超级管理员
            if (1L == user.getId()) {
                return;
            }
            Date validTime = user.getValidTime();
            if (validTime != null) {
                long time = validTime.getTime();
                if (time < new Date().getTime()) {
                    throw new NotLoginException("用户已失效，请联系管理员。");
                }
            }
        }
    }

}

