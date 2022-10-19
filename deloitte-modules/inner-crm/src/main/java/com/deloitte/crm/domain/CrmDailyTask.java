package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PenTang
 * @date 2022/09/22 15:24
 */
public class CrmDailyTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 3crm角色1
     * 4	crm角色2
     * 5	crm角色3
     * 6	crm角色4
     * 7	crm角色5
     * 8	crm角色6
     */
    @Excel(name = "角色id")
    private String taskRoleType;

    /**
     * 1-今日无任务
     * 2-有任务未全部处理完
     * 3-任务处理完毕
     */
    @Excel(name = "任务文件状态 0-禁用 1-启用")
    private Integer taskStatus;

    /**
     * 任务日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskDate;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskRoleType() {
        return taskRoleType;
    }

    public void setTaskRoleType(String taskRoleType) {
        this.taskRoleType = taskRoleType;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "CrmDailyTask{" +
                "id=" + id +
                ", taskRoleType='" + taskRoleType + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskDate=" + taskDate +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
