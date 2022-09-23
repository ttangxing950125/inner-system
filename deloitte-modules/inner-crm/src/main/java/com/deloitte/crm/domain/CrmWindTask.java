package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;

/**
 * 角色1的每日任务，导入wind文件的任务对象 crm_wind_task
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class CrmWindTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** task_cate_id*/
    @Excel(name = "task_cate_id")
    private Integer taskCateId;

    /** crm_task_dict的id */
    @Excel(name = "crm_task_dict的id")
    private Long taskDictId;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String taskDesc;

    /** 任务日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskDate;

    /** 任务文件名 */
    @Excel(name = "任务文件名")
    private String taskFileName;

    /** 任务分类 */
    @Excel(name = "任务分类")
    private String taskCategory;

    /** 是否导入过该任务的文件  0-否 1-是 */
    @Excel(name = "是否导入过该任务的文件  0-否 1-是")
    private Integer imported;

    /** 是否确认过该任务的新增记录  0-否 1-是 */
    @Excel(name = "是否确认过该任务的新增记录  0-否 1-是")
    private Integer confirmInsert;

    /** 是否确认过该任务的更新记录  0-否 1-是 */
    @Excel(name = "是否确认过该任务的更新记录  0-否 1-是")
    private Integer confirmUpdate;

    /** 该条任务是否完成   0-否 1-是 */
    @Excel(name = "该条任务是否完成   0-否 1-是")
    private Integer complete;

    /** 任务完成人 */
    @Excel(name = "任务完成人")
    private Long handleUser;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String remarks;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskDictId(Long taskDictId) 
    {
        this.taskDictId = taskDictId;
    }

    public Long getTaskDictId() 
    {
        return taskDictId;
    }
    public void setTaskDesc(String taskDesc) 
    {
        this.taskDesc = taskDesc;
    }

    public String getTaskDesc() 
    {
        return taskDesc;
    }
    public void setTaskDate(Date taskDate) 
    {
        this.taskDate = taskDate;
    }

    public Date getTaskDate() 
    {
        return taskDate;
    }
    public void setTaskFileName(String taskFileName) 
    {
        this.taskFileName = taskFileName;
    }

    public String getTaskFileName() 
    {
        return taskFileName;
    }
    public void setTaskCategory(String taskCategory) 
    {
        this.taskCategory = taskCategory;
    }

    public String getTaskCategory() 
    {
        return taskCategory;
    }
    public void setImported(Integer imported) 
    {
        this.imported = imported;
    }

    public Integer getImported() 
    {
        return imported;
    }
    public void setConfirmInsert(Integer confirmInsert) 
    {
        this.confirmInsert = confirmInsert;
    }

    public Integer getConfirmInsert() 
    {
        return confirmInsert;
    }
    public void setConfirmUpdate(Integer confirmUpdate) 
    {
        this.confirmUpdate = confirmUpdate;
    }

    public Integer getConfirmUpdate() 
    {
        return confirmUpdate;
    }
    public void setComplete(Integer complete) 
    {
        this.complete = complete;
    }

    public Integer getComplete() 
    {
        return complete;
    }
    public void setHandleUser(Long handleUser) 
    {
        this.handleUser = handleUser;
    }

    public Long getHandleUser() 
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
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    public Integer getTaskCateId() {
        return taskCateId;
    }

    public void setTaskCateId(Integer taskCateId) {
        this.taskCateId = taskCateId;
    }

    @Override
    public String toString() {
        return "CrmWindTask{" +
                "id=" + id +
                ", taskCateId=" + taskCateId +
                ", taskDictId=" + taskDictId +
                ", taskDesc='" + taskDesc + '\'' +
                ", taskDate=" + taskDate +
                ", taskFileName='" + taskFileName + '\'' +
                ", taskCategory='" + taskCategory + '\'' +
                ", imported=" + imported +
                ", confirmInsert=" + confirmInsert +
                ", confirmUpdate=" + confirmUpdate +
                ", complete=" + complete +
                ", handleUser=" + handleUser +
                ", created=" + created +
                ", updated=" + updated +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
