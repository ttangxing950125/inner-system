package com.deloitte.crm.domain;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 bond_info
 * 
 * @author deloitte
 * @date 2022-09-23
 */
public class BondInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 债券状态 */
    @Excel(name = "债券状态")
    private Integer bondStatus;

    /** 德勤内部债券代码 bond_0000id 6位数字 */
    @Excel(name = "德勤内部债券代码 bond_0000id 6位数字")
    private String bondCode;

    /** 债券简称 */
    @Excel(name = "债券简称")
    private String bondShortName;

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
    public void setBondStatus(Integer bondStatus)
    {
        this.bondStatus = bondStatus;
    }

    public Integer getBondStatus()
    {
        return bondStatus;
    }
    public void setBondCode(String bondCode) 
    {
        this.bondCode = bondCode;
    }

    public String getBondCode() 
    {
        return bondCode;
    }
    public void setBondShortName(String bondShortName) 
    {
        this.bondShortName = bondShortName;
    }

    public String getBondShortName() 
    {
        return bondShortName;
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
