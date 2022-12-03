package com.deloitte.additional.recording.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中间补录层任务表
 * </p>
 *
 * @author lanyxp
 * @since 2022-11-21
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BasRecordingTaskInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 主体编码关联entity_info#code
     */
    private String entityCode;

    /**
     * 主体名称关联entity_info#name 建议不要使用该字段，让你，
     */
    private String entityName;

    /**
     * 报告期 yyyy-MM-dd
     */
    private Date periodReportTime;

    /**
     * 状态参考字典表collStat
     * 0-待分配
     * 1-补录中(待补录)
     * 2-审核打回(标签补录中用不到)
     * 3-审核中
     * 4-审核通过
     */
    private Integer periodReportStatus;

    /**
     * 数据采集时间
     */
    private LocalDateTime periodColTime;

    /**
     * 数据预计完成时间
     */
    private Date expectedEndTime;

    /**
     * 数据补录人员
     */
    private Integer periodCollocter;

    /**
     * 数据分配时间
     */
    private Date collocterTaskTime;

    /**
     * 分配的数据审核人员
     */
    private Integer periodApprover;

    /**
     * 任务描述
     */
    private String descs;

    /**
     * 表code bas_recording_table_info#code
     */
    private String tableCode;

    @TableField(exist = false)
    private String tableName;

    /**
     * 标签code bas_recording_label_info#code
     */
    private String lableCode;
    @TableField(exist = false)
    private String lableName;

    /**
     * 删除标识-1生效0-失效
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;
    /**
     * 审核人员操作的状态
     */
    private Integer appStatus;
    /**
     * 补录人员最近一次提交时间
     */
    private Date subTime;
    /**
     * 补录人员第一次提交时间
     */
    private Date firstSubTime;

    /**
     * 分配的数据审核人员名称
     */
    @TableField(exist = false)
    private String periodApproverName;
    /**
     * 数据补录人员名称
     */
    @TableField(exist = false)
    private String periodCollocterName;
    /**
     * 状态名称
     */
    @TableField(exist = false)
    private String periodReportStatusName;


}
