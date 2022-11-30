package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @创建人 tangx
 * @创建时间 2022/11/24
 * @描述 (prs_qual_efficiency)实体表
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("prs_qual_efficiency")
public class PrsQualEfficiency extends BaseEntity implements Serializable {


    /**
     * 指标code
     */
    @TableField("qual_code")
    private String qualCode;

    /**
     * 主体数量
     */
    @TableField("entity_count")
    private Integer entityCount;

    /**
     * 风险级别 参考字典表 riskLevel
     * 1	高风险
     * 2	中风险
     * 3	低风险
     * 4	无风险
     */
    @TableField("risk_level")
    private Integer riskLevel;
    /**
     * 缺失率
     */
    @TableField("missing_rate")
    private BigDecimal missingRate;
    /**
     * 预期效率
     */
    @TableField("expected_efficiency")
    private Integer expectedEfficiency;
    /**
     * 实际效率
     */
    @TableField("actual_efficiency")
    private Integer actualEfficiency;

    /**
     * 通过率
     */
    @TableField("passing_rate")
    private BigDecimal passingRate;
    /**
     * 打回率
     */
    @TableField("return_rate")
    private BigDecimal returnRate;
    /**
     * 最后一次跑批时间，页面上的更新时间
     */
    @TableField("run_batch_time")
    private Date runBatchTime;
    /**
     * 当前分配的审核人员 id
     */
    @TableField("check_user_id")
    private Integer checkUserId;

    /**
     * 任务状态 1-未分配 2-已分配 3-已提交 4-任务打回 5-已通过
     */
    @TableField("task_status")
    private Integer taskStatus;
}
