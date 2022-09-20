package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 entity_bond_rel
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class EntityBondRel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 主体code */
    @Excel(name = "主体code")
    private String entityCode;

    /** 债券code */
    @Excel(name = "债券code")
    private String bdCode;

    /** 关系状态 */
    @Excel(name = "关系状态")
    private Integer status;

    /** 新发行人名称 */
    @Excel(name = "新发行人名称")
    private String newEntityName;

    /** 新发行人code */
    @Excel(name = "新发行人code")
    private String newEntityCode;

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
    public void setEntityCode(String entityCode) 
    {
        this.entityCode = entityCode;
    }

    public String getEntityCode() 
    {
        return entityCode;
    }
    public void setBdCode(String bdCode) 
    {
        this.bdCode = bdCode;
    }

    public String getBdCode() 
    {
        return bdCode;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setNewEntityName(String newEntityName) 
    {
        this.newEntityName = newEntityName;
    }

    public String getNewEntityName() 
    {
        return newEntityName;
    }
    public void setNewEntityCode(String newEntityCode) 
    {
        this.newEntityCode = newEntityCode;
    }

    public String getNewEntityCode() 
    {
        return newEntityCode;
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
