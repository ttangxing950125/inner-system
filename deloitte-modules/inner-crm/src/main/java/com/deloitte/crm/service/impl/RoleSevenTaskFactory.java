package com.deloitte.crm.service.impl;

import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.service.RoleSevenTask;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @author 正杰
 * @description: roleSevenTaskFactory
 * @date 2022/10/28
 */
@Component
public class RoleSevenTaskFactory implements ApplicationContextAware {

    private Map<String,RoleSevenTask> roleSevenTasks;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.roleSevenTasks = applicationContext.getBeansOfType(RoleSevenTask.class);
    }

    public RoleSevenTask getFactory(String keyword){
        return Optional.ofNullable(roleSevenTasks.get(keyword)).orElseThrow(()->new ServiceException(BadInfo.ERROR_SYSTEM_BUSY.getInfo()));
    }

}
