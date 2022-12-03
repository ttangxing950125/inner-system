package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-26
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class FinancialTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 主体code
     */
    private String entityCode;

    /**
     * 三表一注code
     */
    private String tableNodeCode;

    /**
     * 如果是非结构化附注，附注的表名
     */
    private String tableName;

    /**
     * 1-三表 2-结构化附注 3-非结构化附注
     */
    private Integer dataType;

    /**
     * 补录人员的id
     */
    private Integer collocter;

    /**
     * 审核人员的id
     */
    private Integer approver;

    /**
     * 分配补录任务的时间
     */
    private Date colTime;

    /**
     * 提交补录的时间
     */
    private Date recordingTime;

    /**
     * 审核任务的时间
     */
    private Date aprTime;

    /**
     * 审核人员审核时选择的操作 2-审核打回 4-审核通过
     */
    private Integer aprStatus;

    /**
     * 任务状态，字典表
     */
    private Integer status;

    /**
     * 任务补录数据的年份
     */
    private String timeValue;

    /**
     * 任务补录的报告期
     */
    private String reportTime;
    /***
     * 创建时间和
     */

    private Date created;
    /**
     * 更新时间
     */
    private Date updated;


}
