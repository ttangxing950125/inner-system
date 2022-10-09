package com.deloitte.crm.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 冉浩岑
 * @date 2022/10/09 16:36
 */
@Data
@Accessors(chain = true)
public class TaskStatistics {
    /**
     * 当前日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date todayDate;
    /**
     * 当前星期几
     */
    private String todayWeek;
    /**
     * 任务合计条数
     */
    private Integer taskTotal;
    /**
     * 待完成任务条数
     */
    private Integer taskWait;
    /**
     * 已完成任务条数
     */
    private Integer taskComplete;
}
