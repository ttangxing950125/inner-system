package com.deloitte.crm.domain.dto;

import com.deloitte.common.core.annotation.Excel;
import com.deloitte.crm.domain.EntityFinancial;
import com.deloitte.crm.domain.EntityInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * EntityFinancial 导入工具类
 *
 * @author 冉浩岑
 * @since 2022-10-21 17:11:57
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class EntityFinancialDto {

    @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 主体编码
     */
    @Excel(name = "主体编码")
    private String entityCode;
    /**
     * 金融细分领域
     */
    @Excel(name = "金融细分领域")
    private String mince;
    /**
     * gov_info中的dq_code
     */
    @Excel(name = "gov_info中的dq_code")
    private String region;
    /**
     * 对口监管机构
     */
    @Excel(name = "对口监管机构")
    private String regulators;
    /**
     * 对口监管机构
     */
    @Excel(name = "所属地区")
    private String belPlace;

    /**
     * 对口监管机构
     */
    @Excel(name = "所属辖区")
    private String belJurisdiction;

    /**
     * 对口监管机构
     */
    @Excel(name = "备注")
    private String remarks;

    public EntityFinancial getEntityFinancial(){
        EntityFinancial entityFinancial = new EntityFinancial();
        entityFinancial.setEntityCode(this.entityCode)
                       .setMince(this.mince)
                       .setBelJurisdiction(this.belJurisdiction)
                       .setBelPlace(this.belPlace)
                       .setRegion(this.region)
                       .setRegulators(this.regulators)
                       .setRemarks(this.remarks);
        return entityFinancial;
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



    /** 对象映射，配合前端接参数 */
    private EntityInfo city;
    private String county;
    private String creditCode;
    private Object date1;
    private Object date2;
    private String delivery;
    private String desc;
    private String district;
    private String entityName;
    private String name;
    private String resource;
}
