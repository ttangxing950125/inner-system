package com.deloitte.additional.recording.domain;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中间补录层 标签和主体对应关系表
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BasRecordingLableEntity implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标签code bas_recording_lable#code
     */
    private String lableCode;

    /**
     * entity_info#code
     */
    private String entityCode;

    /**
     * 审核人员最终选择的标签选项
     */
    private Integer checkOption;

    /**
     * 补录人员选择的标签选项
     */
    private Integer recordingOption;

    /**
     * 审核人员提交的选项
     */
    private Integer finalOption;

    /**
     * 是否审核通过
     */
    private Integer isCheck;

    /**
     * 资产平台是否拉取过该数据
     */
    private Integer isPull;

    /**
     * 数据年份
     */
    private String dataYear;

    /**
     * 报告期
     */
    private String reportTime;



    private LocalDateTime updated;

    private LocalDateTime created;


}
