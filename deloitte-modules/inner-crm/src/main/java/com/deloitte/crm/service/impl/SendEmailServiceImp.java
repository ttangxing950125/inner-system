package com.deloitte.crm.service.impl;

import com.deloitte.common.core.constant.SecurityConstants;
import com.deloitte.common.core.utils.EmailUtil;
import com.deloitte.crm.service.SendEmailService;
import com.deloitte.system.api.RoleService;
import com.deloitte.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 发邮件接口
 * @author PenTang
 * @date 2022/09/23 15:46
 */
@Component
public class SendEmailServiceImp implements SendEmailService {

    @Autowired
    private RoleService roleService;

 /**
  *根据角色id发送给相关用户实现
  *
  * @param id
  * @param title
  * @param content
  * @return String
  * @author penTang
  * @date 2022/9/23 15:58
 */
    @Override
    public String SendEmail(Integer id, String title, String content) {
        List<SysUser> sysUsers = roleService.selectUserListById(id);
        sysUsers.stream().forEach(row -> {
            String center = "你好" + row.getUserName() + content;
            EmailUtil.sendTemplateEmail(title, center, row.getEmail());

        });


        return null;
     }
}
