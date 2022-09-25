package com.deloitte.crm.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析导入的excel对应对象的工具类
 * @author 吴鹏鹏ppp
 * @date 2022/9/24
 */
public class AttrValueUtils {

    /**
     * 将对象转为map
     * key： 指定注解指定属性的名称
     * value: 改注解字段的值
     * @param obj
     * @param anno
     * @param annoFiled
     * @return
     */
    public static Map<String, Object> parseObj(
            Object obj,
            Class<? extends Annotation> anno,
            String annoFiled
            ) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Annotation filedAnno = field.getAnnotation(anno);
            field.setAccessible(true);
            if (filedAnno==null){
                continue;
            }


            Method method = anno.getMethod(annoFiled);
            method.setAccessible(true);
            String key = method.invoke(filedAnno).toString();
            Object value = field.get(obj);
            dataMap.put(key, value);
        }

        return dataMap;
    }


}
