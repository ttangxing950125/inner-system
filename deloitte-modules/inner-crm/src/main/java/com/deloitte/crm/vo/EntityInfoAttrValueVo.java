package com.deloitte.crm.vo;

import com.alibaba.fastjson.JSON;
import com.deloitte.crm.domain.EntityInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 冉浩岑
 * @date 2022/11/07 10:43
 */
@Data
@Accessors(chain = true)
public class EntityInfoAttrValueVo {
    private Integer id;
    private String entityName;
    private String entityCode;
    private String reportType;
    private String windMaster;
    private String shenWanMaster;
    private String cicsIndustryDetails;
    private String creditCode;
    private Integer list;
    private Integer issueBonds;
    private Integer finance;
    private String listType;
    private Integer creditError;
    private String creditErrorRemark;
    private Integer creditErrorType;
    private String entityNameHis;
    private String entityNameHisRemarks;
    private String creater;
    private String updater;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updated;
    private String entityIndustrySsc;
    private String entityAuditinstitNew;
    private String entityStockTag;
    private String entityBondTag;
    private String value;
    private String valueId;

    public EntityInfo haveEntityInfo(){
        EntityInfo entityInfo = new EntityInfo( this.id,
                                                this.entityName,
                                                this.entityCode,
                                                this.reportType,
                                                this.windMaster,
                                                this.shenWanMaster,
                                                this.cicsIndustryDetails,
                                                this.creditCode,
                                                this.list,
                                                this.issueBonds,
                                                this.finance,
                                                this.listType,
                                                this.creditError,
                                                this.creditErrorRemark,
                                                this.creditErrorType,
                                                this.entityNameHis,
                                                this.entityNameHisRemarks,
                                                this.creater,
                                                this.updater,
                                                this.status,
                                                this.created,
                                                this.updated,
                                                this.entityIndustrySsc,
                                                this.entityAuditinstitNew,
                                                this.entityStockTag,
                                                this.entityBondTag);
        return entityInfo;
    }
    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
