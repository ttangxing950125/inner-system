package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PenTang
 * @date 2022/11/23 16:14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KpiWorkFill implements Serializable {
    private static final long serialVersionUID = 337891707856428940L;
    @Excel(name = "${column.comment}")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer  userId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 总小时
     */
    private Double  totalHour;

    private Integer kMonth;

    private  Integer kYear;

    /**
     * 当天日期
     */
    private Date  workDay;

    /**
     * 当天第几个小时
     */
    private Double hour0;

    private Double hour1;

    private Double hour2;

    private Double hour3;

    private Double hour4;

    private Double hour5;

    private Double hour6;
    private Double hour7;
    private Double hour8;
    private Double hour9;
    private Double hour10;
    private Double hour11;
    private Double hour12;
    private Double hour13;
    private Double hour14;
    private Double hour15;
    private Double hour16;
    private Double hour17;
    private Double hour18;
    private Double hour19;
    private Double hour20;
    private Double hour21;
    private Double hour22;
    private Double hour23;


}
