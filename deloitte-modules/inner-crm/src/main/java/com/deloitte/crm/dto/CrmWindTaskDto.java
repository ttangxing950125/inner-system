package com.deloitte.crm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author PenTang
 * @date 2022/09/22 10:06
 * 用于返回当天统计任务完成率
 */
@ApiModel(value = "CrmWindTaskDtos",description = "用于返回当天统计任务完成率")
public class CrmWindTaskDto {
    @ApiModelProperty(value = "任务描述")
    private Integer taskCateId;


     private String taskCategory;


    /** 任务描述 */
    @ApiModelProperty(value = "任务描述")
    private String  taskDesc;
    /** 任务总数(指定日期) */
    @ApiModelProperty(value = "任务总数")
    private String taskCount;
    /** 未完成任务数(指定日期) */
    @ApiModelProperty(value = "未完成任务数(指定日期)")
    private String complete;

    public Integer getTaskCateId() {
        return taskCateId;
    }

    public void setTaskCateId(Integer taskCateId) {
        this.taskCateId = taskCateId;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(String taskCount) {
        this.taskCount = taskCount;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String notComplete) {
        this.complete = notComplete;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    @Override
    public String toString() {
        return "CrmWindTaskDto{" +
                "taskDesc='" + taskDesc + '\'' +
                ", taskCount='" + taskCount + '\'' +
                ", Complete='" + complete + '\'' +
                '}';
    }
}
