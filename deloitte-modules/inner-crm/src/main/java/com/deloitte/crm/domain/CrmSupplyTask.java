package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 crm_supply_task
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class CrmSupplyTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 德勤code */
    @Excel(name = "德勤code")
    private String entityCode;

    /** sys_role的id */
    @Excel(name = "sys_role的id")
    private Long roleId;

    /** 任务日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskDate;

    /** 任务处理状态 0-未处理 1-已处理 */
    @Excel(name = "任务处理状态 0-未处理 1-已处理")
    private Integer state;

    /** 处理人，完成人 */
    @Excel(name = "处理人，完成人")
    private String handleUser;

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
    public void setRoleId(Long roleId) 
    {
        this.roleId = roleId;
    }

    public Long getRoleId() 
    {
        return roleId;
    }
    public void setTaskDate(Date taskDate) 
    {
        this.taskDate = taskDate;
    }

    public Date getTaskDate() 
    {
        return taskDate;
    }
    public void setState(Integer state) 
    {
        this.state = state;
    }

    public Integer getState() 
    {
        return state;
    }
    public void setHandleUser(String handleUser) 
    {
        this.handleUser = handleUser;
    }

    public String getHandleUser() 
    {
        return handleUser;
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
