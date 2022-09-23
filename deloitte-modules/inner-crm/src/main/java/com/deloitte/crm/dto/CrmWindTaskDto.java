package com.deloitte.crm.dto;

/**
 * @author PenTang
 * @date 2022/09/22 10:06
 * 用于返回当天统计任务完成率
 */

public class CrmWindTaskDto {
    /** 任务描述 */
    private String  taskDesc;
    /** 任务总数(指定日期) */
    private String taskCount;
    /** 未完成任务数(指定日期) */
    private String notComplete;

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

    public String getNotComplete() {
        return notComplete;
    }

    public void setNotComplete(String notComplete) {
        this.notComplete = notComplete;
    }

    @Override
    public String toString() {
        return "CrmWindTaskDto{" +
                "taskDesc='" + taskDesc + '\'' +
                ", taskCount='" + taskCount + '\'' +
                ", notComplete='" + notComplete + '\'' +
                '}';
    }
}
