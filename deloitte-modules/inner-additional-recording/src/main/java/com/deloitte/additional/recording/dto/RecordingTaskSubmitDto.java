package com.deloitte.additional.recording.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/23/10:40
 * @Description:
 */
@Setter
@Getter
public class RecordingTaskSubmitDto implements Serializable {
    /**
     * 数据预计完成时间
     */
    @NotBlank(message = "数据预计完成时间不可以为空")
    private Date expectedEndTime;
    /**
     * 数据补录人员
     */
    @NotBlank(message = "数据补录人员不可以为空")
    private Integer periodCollocter;
    /**
     * 分配的数据审核人员
     */
    @NotBlank(message = "分配的数据审核人员ID不可以为空")
    private Integer periodApprover;

    /**
     * 主体Code
     */
    private String entityCode;
    /**
     * 表code bas_recording_table_info#code
     */
    private String tableCode;

    /**
     * 标签code bas_recording_label_info#code
     */
    private String lableCode;

    /**
     * 任务ID
     */
    @NotBlank(message = "任务ID不可以为空")
    private Integer taskId;
}
