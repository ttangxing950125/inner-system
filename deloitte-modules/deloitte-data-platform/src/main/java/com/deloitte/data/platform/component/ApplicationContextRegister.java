package com.deloitte.data.platform.component;

import com.deloitte.data.platform.util.FormulaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * springboot静态方法获取 bean
 *
 * @author: XY
 * @version: 1
 * @date: 2022-11-19
 */
@Component
public class ApplicationContextRegister<T> implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaUtils.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextRegister.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        if (applicationContext == null) {
            return null;
        }
        Object object = null;
        try {
            object = applicationContext.getBean(name);
        } catch (Exception e) {
            LOGGER.error("获取Bean错误：", e);
        }
        return object;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        T t = null;
        try {
            t = applicationContext.getBean(clazz);
        } catch (Exception e) {
            LOGGER.error("获取Bean错误：", e);
        }
        return t;
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        T t = null;
        try {
            t = applicationContext.getBean(name, clazz);
        } catch (Exception e) {
            LOGGER.error("获取Bean错误：", e);
        }
        return t;
    }
}
