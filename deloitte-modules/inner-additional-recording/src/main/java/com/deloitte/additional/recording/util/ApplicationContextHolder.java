package com.deloitte.additional.recording.util;

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

    /**
     * 获取bean
     * @param requiredType bean类型
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType){
        if(CONTEXT==null){
            return null;
        }
        return CONTEXT.getBean(requiredType);
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
