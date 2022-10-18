package com.deloitte.crm.utils;

import com.deloitte.crm.annotation.Attrs;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityInfoLogsUpdated;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 正杰
 * @description: getAnnotationAttr
 * @date 2022/10/14
 */
@Component
public class GetAnnotationAttr {

    /**
     * 解析实体中 Attrs 中的 id 以及对应字段 属性值
     * @param obj
     * @param entityCode
     * @return
     */
    public List<EntityAttrValue> get(Object obj,String entityCode){
        ArrayList<EntityAttrValue> entityAttrValues = new ArrayList<>();
        Class<?> aClass = obj.getClass();
        for (Field declaredField : aClass.getDeclaredFields()) {
            Attrs annotation = declaredField.getAnnotation(Attrs.class);
            if(annotation==null){continue;}
            EntityAttrValue entityAttrValue = new EntityAttrValue();
            int attrId = annotation.attrId();
            declaredField.setAccessible(true);
            String value = null;
            try {
                Object o = declaredField.get(obj);
                if(o==null){
                    continue;
                }else {
                    value = o.toString();
                }
            } catch (IllegalAccessException e) {
                continue;
            }
            entityAttrValues.add(entityAttrValue.setAttrId((long) attrId).setValue(value).setEntityCode(entityCode));
        }
        return entityAttrValues;
    }

    public List<EntityInfoLogsUpdated> getEntityInfoLogsUpdated(String tableName,Object old,Object now,String userName){
        return null;
    }

}
