package com.deloitte.additional.recording.util;

import com.deloitte.additional.recording.domain.SysUser;
import com.deloitte.additional.recording.mapper.SysUserMapper;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.JwtUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author 吴鹏鹏ppp
 * @date 2022/11/10
 */
public class MetaSecurity {

    /**
     * 获得当前登录的用户
     * @return
     */
    public static SysUser getLoginUser(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes==null){
            throw new ServiceException("请在spring_web环境下运行");
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String token = request.getHeader("token");
        String userId = JwtUtil.getuserid(token);

        SysUserMapper userMapper = ApplicationContextHolder.getBean(SysUserMapper.class);

        return userMapper.selectById(userId);
    }

}
