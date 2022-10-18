package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.annotation.UpdateLog;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.EntityInfoLogsUpdated;
import com.deloitte.crm.domain.StockCnInfo;
import com.deloitte.crm.domain.StockThkInfo;
import com.deloitte.crm.mapper.EntityInfoLogsUpdatedMapper;
import com.deloitte.crm.service.EntityInfoLogsUpdatedService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author 正杰
 * @description: EntityInfoLogsUpdatedServiceImpl
 * @date 2022/10/17
 */
@Service
@AllArgsConstructor
@Slf4j
public class EntityInfoLogsUpdatedServiceImpl extends ServiceImpl<EntityInfoLogsUpdatedMapper, EntityInfoLogsUpdated> implements EntityInfoLogsUpdatedService {

    /**
     *  查询上市企业或是地方政府的更新记录
     *
     * @param tableType -> 1-企业上市信息 || 2-地方政府上市信息
     * @param pageNum default 1
     * @param pageSize default 10
     * @return {@link EntityInfoLogsUpdated}
     */
    @Override
    public R<Page<EntityInfoLogsUpdated>> getInfo(Integer tableType, Integer pageNum, Integer pageSize) {
        pageNum = pageNum==null?1:pageNum;
        pageSize = pageSize==null?10:pageSize;
        return R.ok(baseMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<EntityInfoLogsUpdated>().lambda().eq(EntityInfoLogsUpdated::getTableType,tableType).orderBy(true,false,EntityInfoLogsUpdated::getUpdated)), SuccessInfo.SUCCESS.getInfo());
    }

    @Override
    public void insert(String code,String stockShortName,Object old, Object now, Integer tableType) {
        String username = SecurityUtils.getUsername();
        switch (tableType){
            case 1:
                this.insertEntityInfoLogsUpdatedOne(code,stockShortName,old, now, username);
            case 2:
                return;
            default:
                break;
        }
    }

    public void insertEntityInfoLogsUpdatedOne(String code,String stockShortName,Object old,Object now,String userName){
        Field[] declaredFields;
        if(old instanceof EntityInfo){
            Class<EntityInfo> entityInfoClass = EntityInfo.class;
            declaredFields = entityInfoClass.getDeclaredFields();
        }else if(old instanceof StockCnInfo){
            Class<StockCnInfo> stockCnInfoClass = StockCnInfo.class;
            declaredFields = stockCnInfoClass.getDeclaredFields();
        }else if(old instanceof StockThkInfo){
            Class<StockThkInfo> stockThkInfoClass = StockThkInfo.class;
            declaredFields = stockThkInfoClass.getDeclaredFields();
        }else{
            log.info("  =>> 字段类型未映射成功，更新记录表记录失败 <<=  ");
            return;
        }
        //开始新增
        log.info("  =>> 开始更新关于 "+stockShortName+" 相关的数据 <<=  ");
        doInsert(declaredFields, code, stockShortName, old, now, userName);
        log.info("  =>>  字段更新成功  <<=  ");
    }

    @Transactional(rollbackFor = Exception.class)
    void doInsert(Field[] declaredFields,String code,String stockShortName,Object old,Object now,String userName){
        for (Field declaredField : declaredFields) {
            UpdateLog annotation = declaredField.getAnnotation(UpdateLog.class);
            if(annotation == null){continue;}
            String fieldName = annotation.fieldName();
            String originalValue = getValue(declaredField, old);
            String tableFieldName = annotation.tableFieldName();
            String value = getValue(declaredField, now);
            if(Objects.equals(originalValue, value)){continue;}
            log.info("  =>> 更新字段为 "+fieldName+" <<=  ");
            String created = null;
            if(originalValue!=null){
                EntityInfoLogsUpdated entityInfoLogsUpdated = baseMapper.selectOne(new QueryWrapper<EntityInfoLogsUpdated>().lambda().eq(EntityInfoLogsUpdated::getCode, code).eq(EntityInfoLogsUpdated::getTableFieldName, tableFieldName).orderBy(true, false, EntityInfoLogsUpdated::getId).last("limit 1"));
                if(entityInfoLogsUpdated!=null){
                    created = entityInfoLogsUpdated.getUpdated();
                    entityInfoLogsUpdated.setStatus(false);
                    baseMapper.updateById(entityInfoLogsUpdated);
                }
            }
            EntityInfoLogsUpdated entityInfoLogsUpdated = new EntityInfoLogsUpdated(code,stockShortName,fieldName,originalValue,value,userName,"entity_info",tableFieldName,1);
            if(created!=null){
                entityInfoLogsUpdated.setCreated(created);
            }
            baseMapper.insert(entityInfoLogsUpdated);
        }
    }

    String getValue(Field field,Object object){
        String value = null;
        try {
            field.setAccessible(true);
            value = field.get(object).toString();
        } catch (Exception e) {
            return value;
        }
        return value;
    }

}
