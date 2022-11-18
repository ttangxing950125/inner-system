package com.deloitte.crm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 正杰
 * @description: UpdateLog
 * @date 2022/10/18
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateLog {

    /**
     * 字段名
     * @return
     */
    String fieldName();

    /**
     * 表中字段名
     * @return
     */
    String tableFieldName();

}
