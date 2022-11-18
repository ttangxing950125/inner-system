package com.deloitte.crm.dto;

import lombok.Data;

/**
 * @author PenTang
 * @date 2022/10/11 18:49
 */
@Data
public class TaskDto {

    /**
     *
     * 任务总数
     */
    private String taskCount;
    /**
     *
     * 任务完成数
     */

    private String taskCop;
    /**
     *
     * 任务未完成
     */
    private String taskNoCount;


}
