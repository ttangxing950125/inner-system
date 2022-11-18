package com.deloitte.crm.service.impl;

import com.deloitte.common.core.utils.EmailUtil;
import com.deloitte.crm.service.SendEmailService;
import com.deloitte.system.api.RoleService;
import com.deloitte.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 发邮件接口
 *
 * @author PenTang
 * @date 2022/09/23 15:46
 */
@Component
public class SendEmailServiceImp implements SendEmailService {

    @Autowired
    private RoleService roleService;

    /**
     * 根据角色id发送给相关用户实现
     *
     * @param id
     * @param title
     * @param content
     * @return String
     * @author penTang
     * @date 2022/9/23 15:58
     */
    @Override
    @Async()
    public String SendEmail(Integer id, String title, String content) {
        List<SysUser> sysUsers = roleService.selectUserListById(id);
        sysUsers.stream().forEach(row -> {
            String center = "你好" + row.getUserName() + content;
            EmailUtil.sendTemplateEmail(title, center, row.getEmail());

        });


        return null;
    }

    /**
     * 角色2 邮件格式
     * @param roleId
     * @param taskCount
     * @param taskDate
     */
    public void email(Integer roleId,Integer taskCount,String taskDate){
        this.SendEmail(roleId,"[新任务︰待划分敞口主体" + taskCount + "个]",
                "</br>  "+ taskDate +"新增主体确认任务已完成，共计新增 " + taskCount + " 个主体需划分敞口。" +
                        "请尽快登陆平台完成相关任务。</br>" +
                        "<a href='https://ibond.deloitte.com.cn:8080/crm-door/index'>主体管理平台</a></br>" +
                        "</br></br>Thanks,</br>主体管理平台");
    }

}
