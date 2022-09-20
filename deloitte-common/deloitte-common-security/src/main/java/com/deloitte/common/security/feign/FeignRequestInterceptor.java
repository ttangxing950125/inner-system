package com.deloitte.common.security.feign;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;
import com.deloitte.common.core.constant.SecurityConstants;
import com.deloitte.common.core.utils.ServletUtils;
import com.deloitte.common.core.utils.ip.IpUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * feign 请求拦截器
 * 
 * @author lipeng
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor
{
    @Override
    public void apply(RequestTemplate requestTemplate)
    {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (ObjectUtil.isNotNull(httpServletRequest))
        {
            Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
            // 传递用户信息请求头，防止丢失
            String userId = headers.get(SecurityConstants.DETAILS_USER_ID);
            if (StrUtil.isNotEmpty(userId))
            {
                requestTemplate.header(SecurityConstants.DETAILS_USER_ID, userId);
            }
            String userName = headers.get(SecurityConstants.DETAILS_USERNAME);
            if (StrUtil.isNotEmpty(userName))
            {
                requestTemplate.header(SecurityConstants.DETAILS_USERNAME, userName);
            }
            String authentication = headers.get(SecurityConstants.AUTHORIZATION_HEADER);
            if (StrUtil.isNotEmpty(authentication))
            {
                requestTemplate.header(SecurityConstants.AUTHORIZATION_HEADER, authentication);
            }

            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IpUtils.getIpAddr(ServletUtils.getRequest()));
        }
    }
}