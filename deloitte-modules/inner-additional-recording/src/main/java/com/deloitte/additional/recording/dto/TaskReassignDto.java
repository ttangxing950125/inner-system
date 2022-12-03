package com.deloitte.additional.recording.dto;

import lombok.Data;

import java.util.List;

/**
 * 王大鹏
 * 审核改派
 */
@Data
public class TaskReassignDto {

    /**
     * 任务id
     */
    private List<Integer> taskInfoIds;

    /**
     *审核人员的id
     */
    private Integer approverId;

    /**
     * 当前用户
     */
    private Integer currentUserId;
}
