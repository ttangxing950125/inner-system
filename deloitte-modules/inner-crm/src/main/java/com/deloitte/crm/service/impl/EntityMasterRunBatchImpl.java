package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.crm.annotation.Attrs;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityMaster;
import com.deloitte.crm.mapper.EntityMasterMapper;
import com.deloitte.crm.service.EntityAttrValueRunBatchTask;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * 表 entity_master 批量数据导入
 * @author 正杰
 * @description: EntityMasterRunBatchImpl
 * @date 2022/10/14
 */
@Service
@AllArgsConstructor
public class EntityMasterRunBatchImpl implements EntityAttrValueRunBatchTask {

    private EntityMasterMapper entityMasterMapper;

    /**
     * 需要参数 {@link com.deloitte.crm.domain.EntityAttrValue}
     * 获取当前表中所有需要上传的参数
     * @return EntityAttrValue
     * @throws Exception
     */
    @Override
    public List<EntityAttrValue> getPrams() throws Exception {
        Date date = new Date();
        List<EntityMaster> entityMasters = entityMasterMapper.selectList(new QueryWrapper<EntityMaster>().lambda().eq(EntityMaster::getUpdate, date).eq(EntityMaster::getCreated, date));
        EntityMaster entityMaster = entityMasters.get(0);
        String entityCode = entityMaster.getEntityCode();
        if(entityCode==null){return null;}

        EntityAttrValue result = new EntityAttrValue();
        result.setEntityCode(entityCode);

        Class<? extends EntityMaster> aClass = entityMaster.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            Attrs annotation = declaredField.getAnnotation(Attrs.class);
            if(annotation==null){break;}
            int i = annotation.attrId();


        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void runBatchData() {

    }
}
