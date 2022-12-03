package com.deloitte.additional.recording.enums;

public enum TaskStatus {


    TASK_STATUS_ASSIGNED(0, "待分配"),
    TASK_STATUS_ADDITION(1, "补录中"),
    TASK_STATUS_BACKBack(2, "审核打回"),
    TASK_STATUS_UNDER_REVIEW(3,"审核中"),
    TASK_STATUS_APPROVEDA(4,"审核通过");

    private Integer status;

    private String desc;

    TaskStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(Integer type) {
        TaskStatus[] taskStatuses = values();
        for (TaskStatus taskStatus : taskStatuses) {
            if (taskStatus.status.equals(type)) {
              return   taskStatus.desc;
            }
        }
        return null;
    }
}
