package com.deloitte.additional.recording.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author PenTang
 * @date 2022/11/26 16:18
 */
@Data
public class KpiWorkCalDto {
    private  Integer userId;

    private  Integer groupId;

    /**
     * 日期(YYYY-MM)
     */
    private  String date;

    /**
     * 统计方式(1-按日 2-按月)
     */
    private Integer  statistical;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    private  Integer  pageNum;

    private  Integer pageSize;

}
