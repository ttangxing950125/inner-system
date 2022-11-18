package com.deloitte.crm.domain.dto;

import com.alibaba.fastjson.JSON;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.crm.domain.EntityGovRel;
import com.deloitte.crm.domain.EntityInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 【请填写功能名称】对象 entity_gov_rel
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Data
@Accessors(chain = true)
public class EntityGovRelDto
{
    /** $column.columnComment */
    private Integer id;

    /** 城投主体code */
    @Excel(name = "城投主体code")
    private String entityCode;

    /** 德勤政府code */
    @Excel(name = "德勤政府code")
    private String dqGovCode;

    /** 德勤政府code */
    @Excel(name = "持股方式")
    private String shareMethod;

    /** 德勤政府code */
    @Excel(name = "支持力度")
    private String support;

    /** 德勤政府code */
    @Excel(name = "支持力度判断依据")
    private String judgment;

    /** 德勤政府code */
    @Excel(name = "持股比例")
    private String shareRatio;

    public EntityGovRel getEntityGovRel(){
        EntityGovRel entityGovRel = new EntityGovRel();
        entityGovRel.setEntityCode(this.entityCode)
                .setDqGovCode(this.dqGovCode)
                .setShareMethod(this.shareMethod)
                .setSupport(this.support)
                .setJudgment(this.judgment)
                .setShareRatio(this.shareRatio)
                .setShareRatioYear(this.shareRatioYear)
                .setRemarks(this.remarks);
        return entityGovRel;
    }

    public EntityInfo getEntityInfo(){
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setEntityCode(this.entityCode)
                .setWindMaster(this.windMaster)
                .setShenWanMaster(this.shenWanMaster);
        return entityInfo;
    }

    /**
     * entity_info的entity_code
     */
    @Excel(name = "wind行业划分")
    private String windMaster;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "申万行业划分")
    private String shenWanMaster;






    /** 德勤政府code */
    @Excel(name = "持股比例年份")
    private String shareRatioYear;

    /** 德勤政府code */
    @Excel(name = "备注")
    private String remarks;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}







