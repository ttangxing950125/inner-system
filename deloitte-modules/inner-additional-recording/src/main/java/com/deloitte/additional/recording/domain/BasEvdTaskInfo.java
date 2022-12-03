package com.deloitte.additional.recording.domain;

import lombok.*;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Builder;

import java.util.Date;

/**
 * @创建人 tangx
 * @创建时间 2022/11/29
 * @描述 BasEvdTaskInfo实体表
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class BasEvdTaskInfo implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据记录的id，bas_evd_data.id
     */
    private Integer dataId;
    /**
     * 数据类型(1-固定
     */
    private Integer dataType;
    /**
     * 分配的数据补录人员
     */
    private Integer collocter;
    /**
     * 分配的数据审核人员
     */
    private Integer approver;
    /**
     * 采集开始时间，分配任务的时间
     */
    private Date colTime;
    /**
     * 补录人员最近提交的时间
     */
    private Date subTime;
    /**
     * 补录人员第一次提交任务的时间
     */
    private Date firstSubTime;
    /**
     * 审核时间，审核任务的时间
     */
    private Date aprTime;
    /**
     * 审核人员审核时选择的操作 2-审核打回 4-审核通过
     */
    private Integer aprStatus;
    /**
     * 任务状态 参考字典表collStat
     * <p>
     * 0	待分配
     * 1	补录中
     * 2	审核打回
     * 3	审核中
     * 4	审核通过
     */
    private Integer status;

    /**
     * 该任务预期结束时间
     */
    private Date expectedEndTime;
    /**
     * 原文描述
     */
    private String oriDescription;
    /**
     * 原文链接
     */
    private String oriSrc;

    /**
     * 异常值备注
     */
    private String errDescription;
    private Date created;

    private Date updated;
    /**
     * 备注
     */
    private String mark;

    /**
     * 数据备注
     */
    private String dataMark;




}
