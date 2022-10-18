package com.deloitte.crm.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityMaster;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.mapper.EntityMasterMapper;
import com.deloitte.crm.service.EntityAttrValueRunBatchTask;
import com.deloitte.crm.utils.GetAnnotationAttr;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表 entity_master 批量数据导入
 * @author 正杰
 * @description: EntityMasterRunBatchImpl
 * @date 2022/10/14
 */
@Service
@AllArgsConstructor
@Slf4j
public class EntityMasterRunBatchImpl implements EntityAttrValueRunBatchTask {

    private EntityMasterMapper entityMasterMapper;

    private GetAnnotationAttr getAnnotationAttr;

    private EntityAttrValueMapper entityAttrValueMapper;

    /**
     * 需要参数 {@link com.deloitte.crm.domain.EntityAttrValue}
     * 获取当前表中所有需要上传的参数
     * @return EntityAttrValue
     */
    @Override
    public List<EntityAttrValue> getPrams(){
        Date date = new Date();
        log.info(" =>> 当前时间 ： "+ date +" 开始对 EntityMaster 进行数据拉去");
        DateTime start = DateUtil.beginOfDay(date);
        DateTime end = DateUtil.endOfDay(date);
        List<EntityAttrValue> result = new ArrayList<>();
        List<EntityMaster> entityMasters = entityMasterMapper.selectList(new QueryWrapper<EntityMaster>().lambda().between(EntityMaster::getUpdate,start,end).or().between(EntityMaster::getCreated,start,end));

        int size = entityMasters.size();
        if(size==0){ log.info(" =>> EntityMaster 今日数据为零 录入操作终止 <<= "); return null;}
        log.info(" =>> EntityMaster 今日数据为"+ size +" 准备开始导入 <<= ");

        entityMasters.stream().map(row -> getAnnotationAttr.get(row, row.getEntityCode())).collect(Collectors.toList()).forEach(result::addAll);
        return result;
    }

    /**
     * 批量导入数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void runBatchData() {
        List<EntityAttrValue> prams = this.getPrams();
        if(prams==null){return;}
        log.info(" =>> 数据转化后 共计 "+ prams.size() +" 条数据，准备开始导入 <<= ");
        prams.forEach(row->{
            EntityAttrValue entityAttrValue = entityAttrValueMapper.selectOne(new QueryWrapper<EntityAttrValue>().lambda().eq(EntityAttrValue::getEntityCode, row.getEntityCode()).eq(EntityAttrValue::getAttrId, row.getAttrId()));
            if(entityAttrValue!=null){
                entityAttrValue.setValue(row.getValue());
                entityAttrValueMapper.updateById(entityAttrValue);
            }else{
                entityAttrValueMapper.insertEntityAttrValue(row);
            }
        });
        log.info(" =>> EntityMaster 中，数据导入成功 <<= ");
    }
}
