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
 * @date 2022/11/28 10:56
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KpiWorkScore implements Serializable {

    private static final long serialVersionUID = 337891707856428940L;
    @Excel(name = "${column.comment}")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer  userId;

    /**
     * 当前补录的时间段 0-23'
     */
    private Integer hours;


    /**
     * 工作一天
     */
    private Date workDay;

    /**
     * 提交补录的总量
     */
    private Integer recordTotal;


    /**
     * 补录数据，如果有异常备注，这里+1
     */
    private  Integer emptyTotal;

    /**
     * 检查通过总数
     */
    private  Integer checkTotal;

    /**
     * 审核打回的总数
     */
    private  Integer repulseTotal;

    /**
     * 评分，审核通过一个记1分，打回一个-1分
     */
    private  Integer  score;
}
