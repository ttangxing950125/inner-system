package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 model_master
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class ModelMaster extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 敞口名 */
    @Excel(name = "敞口名")
    private String masterName;

    /** 敞口code */
    @Excel(name = "敞口code")
    private String masterCode;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMasterName(String masterName) 
    {
        this.masterName = masterName;
    }

    public String getMasterName() 
    {
        return masterName;
    }
    public void setMasterCode(String masterCode) 
    {
        this.masterCode = masterCode;
    }

    public String getMasterCode() 
    {
        return masterCode;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setCreated(Date created) 
    {
        this.created = created;
    }

    public Date getCreated() 
    {
        return created;
    }
    public void setUpdated(Date updated) 
    {
        this.updated = updated;
    }

    public Date getUpdated() 
    {
        return updated;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
