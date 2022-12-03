package com.deloitte.additional.recording.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/21/14:59
 * @Description:
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecordingTaskInfoGetInfoReqDto implements Serializable {
    private Integer pageNum = 1;
    private Integer pagesize = 10;
    /**
     * 主体编码
     */
    private String entityCode;

    /**
     * 补录人员
     */
    private Integer periodCollocterId;
    /**
     * 审核人员
     */
    private Integer periodApproverId;
    /**
     * 报告期
     */
    private String periodReportTime;
    /**
     * 标签Code
     */
    private String lableCode;
    /**
     * 表Code
     */
    private String tableCode;
    /**
     * 状态
     */
    private Integer status;


}
