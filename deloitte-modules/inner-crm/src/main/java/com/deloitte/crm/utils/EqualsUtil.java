package com.deloitte.crm.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/10/17
 */
public class EqualsUtil {

    /**
     * 比较指定注解的属性
     * @return
     */
    public static boolean equalsAnnoField(Object o1, Object o2, Class<? extends Annotation> anno){
        if (o1 == o2) return true;
        if (o1 == null || o1.getClass() != o2.getClass()) return false;



        Class<?> clazz = o1.getClass();
        Field[] fields = clazz.getDeclaredFields();
        //获得需要比较的字段
        for (Field field : fields) {
            Annotation filedAnno = field.getAnnotation(anno);
            if (filedAnno==null){
                continue;
            }
            field.setAccessible(true);

            //o1 o2的属性
            try {
                Object o1Value = field.get(o1);
                Object o2Value = field.get(o2);

                boolean b = equalsObj(o1Value, o2Value);
                if (!b){
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }

        }

        return true;
    }

    /**
     * 比较java自带的类型，不够自己补，请保证o1 o2 都是objClass类型，并且都非空
     * @return
     */
    public static boolean equalsObj(Object o1, Object o2){
        if (o1 == o2) return true;
        if (o1 == null || o1.getClass() != o2.getClass()) return false;

        Class<?> aClass = o1.getClass();

        //bigdecimal
        if (aClass==BigDecimal.class){
            return ((BigDecimal)o1).compareTo( (BigDecimal)o2 )==0;
        }else if (aClass==Double.class){
            //double
            return ((Double)o1).compareTo( (Double)o2 )==0;
        }
        else{
            return o1.equals(o2);
        }
    }

}
