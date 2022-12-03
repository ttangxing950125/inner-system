package com.deloitte.additional.recording.vo.kpi;

import lombok.Data;

import java.util.Date;

/**
 * @author PenTang
 * @date 2022/11/26 18:08
 */
@Data
public class KpiWorkCalViewVo {

    private Integer groupId;

    private String  groupName;

    /**
     * 用户id
     */
    private Integer  userId;

    /**
     * 用户名
     */
    private  String userName;


    /**
     * 当天日期
     */
    private String workDay;

    /**
     * 总小时
     */
    private Double  totalHour;
}
