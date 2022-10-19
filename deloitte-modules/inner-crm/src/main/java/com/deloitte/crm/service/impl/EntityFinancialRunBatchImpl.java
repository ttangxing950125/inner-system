package com.deloitte.crm.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityFinancial;
import com.deloitte.crm.domain.EntityGovRel;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.mapper.EntityFinancialMapper;
import com.deloitte.crm.service.EntityAttrValueRunBatchTask;
import com.deloitte.crm.utils.GetAnnotationAttr;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 正杰
 * @description: EntityFinancialRunBatchImpl
 * @date 2022/10/14
 */
@Service
@AllArgsConstructor
@Slf4j
public class EntityFinancialRunBatchImpl implements EntityAttrValueRunBatchTask {

    private EntityFinancialMapper entityFinancialMapper;

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
        log.info(" =>> 当前时间 ： "+ date +" 开始对 EntityFinancial 进行数据拉去");
        DateTime start = cn.hutool.core.date.DateUtil.beginOfDay(date);
        DateTime end = cn.hutool.core.date.DateUtil.endOfDay(date);
        List<EntityAttrValue> result = new ArrayList<>();
        List<EntityFinancial> entityFinancials = entityFinancialMapper.selectList(new QueryWrapper<EntityFinancial>().lambda().between(EntityFinancial::getUpdated,start,end).or().between(EntityFinancial::getCreated,start,end));

        int size = entityFinancials.size();
        if(size==0){ log.info(" =>> EntityFinancial 今日数据为零 录入操作终止 <<= "); return null;}
        log.info(" =>> EntityFinancial 今日数据为"+ size +" 准备开始导入 <<= ");

        entityFinancials.stream().map(row -> getAnnotationAttr.get(row, row.getEntityCode())).collect(Collectors.toList()).forEach(result::addAll);
        return result;
    }

    /**
     * 批量导入数据
     */
    @Override
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
        log.info(" =>> EntityFinancial 中，数据导入成功 <<= ");
    }
}
