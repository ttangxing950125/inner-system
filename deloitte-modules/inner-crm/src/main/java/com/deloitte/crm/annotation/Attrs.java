package com.deloitte.crm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 关联 entity_attr 的 name 与 id
 * @author 正杰
 * @description: Attr
 * @date 2022/10/14
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Attrs {

    /**
     * 等效于 {@link com.deloitte.crm.domain.EntityAttr} 中的 id
     */
    int attrId() default 0;

    /**
     * 等效于 {@link com.deloitte.crm.domain.EntityAttr} 中的 name
     */
    String attrName() default  "未设置";

}
