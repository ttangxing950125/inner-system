package com.deloitte.additional.recording.vo.kpi;

import lombok.Data;

import java.util.Date;

/**
 * @author PenTang
 * @date 2022/11/28 13:38
 */
@Data
public class KpiWorkScoreVo {
    private Integer id;

    /**
     * 用户id
     */
    private Integer  userId;

    private  String userName;

    /**
     * 小时
     */
    private Integer hours;

    /**
     * 工作日期
     */
    private Date workDay;

    /**
     * 提交补录的总量
     */
    private  Integer recordTotal;

    /**
     * 补录数据(为空的时候)
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
     * 总评分
     */
    private  Integer  score;

}
