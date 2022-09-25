package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.deloitte.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 【请填写功能名称】对象 entity_name_his
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class EntityNameHis implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 1-企业主体 2-政府 */
    @Excel(name = "1-企业主体 2-政府")
    private Integer entityType;

    /** 生效状态 0-失效 1-生效 */
    @Excel(name = "生效状态 0-失效 1-生效")
    private Integer status;

    /** 德勤code */
    @Excel(name = "德勤code")
    private String dqCode;

    /** 改名日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "改名日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date happenDate = new Date();

    /** 曾用名 */
    @Excel(name = "曾用名")
    private String oldName;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 记录新增来源 1-修改主体名称自动生成 2-曾用名管理中操作 */
    @Excel(name = "记录新增来源 1-修改主体名称自动生成 2-曾用名管理中操作")
    private Integer source;

    /** 创建人，为null的话就是系统创建 */
    @Excel(name = "创建人，为null的话就是系统创建")
    private String creater;

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
    public void setEntityType(Integer entityType) 
    {
        this.entityType = entityType;
    }

    public Integer getEntityType() 
    {
        return entityType;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setDqCode(String dqCode) 
    {
        this.dqCode = dqCode;
    }

    public String getDqCode() 
    {
        return dqCode;
    }
    public void setHappenDate(Date happenDate) 
    {
        this.happenDate = happenDate;
    }

    public Date getHappenDate() 
    {
        return happenDate;
    }
    public void setOldName(String oldName) 
    {
        this.oldName = oldName;
    }

    public String getOldName() 
    {
        return oldName;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setSource(Integer source) 
    {
        this.source = source;
    }

    public Integer getSource() 
    {
        return source;
    }
    public void setCreater(String creater) 
    {
        this.creater = creater;
    }

    public String getCreater() 
    {
        return creater;
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
