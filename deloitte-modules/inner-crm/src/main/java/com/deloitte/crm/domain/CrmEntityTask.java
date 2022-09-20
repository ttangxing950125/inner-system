package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;

/**
 * 角色7，根据导入的数据新增主体的任务对象 crm_entity_task
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class CrmEntityTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 捕获渠道， crm_wind_task的task_category */
    @Excel(name = "捕获渠道， crm_wind_task的task_category")
    private String taskCategory;

    /** 1-债券、2-港股、3-股票 表中的id */
    @Excel(name = "1-债券、2-港股、3-股票 表中的id")
    private Long sourceId;

    /** 1-债券、2-港股、3-股票 */
    @Excel(name = "1-债券、2-港股、3-股票")
    private Long sourceType;

    /** 展示给前端的数据 */
    @Excel(name = "展示给前端的数据")
    private String dataShow;

    /** 任务日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskDate;

    /** 任务处理状态 0-未处理 1-已有主体 2-新增主体 */
    @Excel(name = "任务处理状态 0-未处理 1-已有主体 2-新增主体")
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
    public void setTaskCategory(String taskCategory) 
    {
        this.taskCategory = taskCategory;
    }

    public String getTaskCategory() 
    {
        return taskCategory;
    }
    public void setSourceId(Long sourceId) 
    {
        this.sourceId = sourceId;
    }

    public Long getSourceId() 
    {
        return sourceId;
    }
    public void setSourceType(Long sourceType) 
    {
        this.sourceType = sourceType;
    }

    public Long getSourceType() 
    {
        return sourceType;
    }
    public void setDataShow(String dataShow) 
    {
        this.dataShow = dataShow;
    }

    public String getDataShow() 
    {
        return dataShow;
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
