package com.deloitte.crm.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/09/16:43
 * @Description: Bean获取类 推荐使用
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    private static void setContext(ApplicationContext context) {
        ApplicationContextHolder.CONTEXT = context;
    }

    public static ConfigurableApplicationContext get() {
        return (ConfigurableApplicationContext) CONTEXT;
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (CONTEXT != null) {
            if (context.getParent() == CONTEXT) {
                setContext(context);
            }
        } else {
            setContext(context);
        }
    }
}
