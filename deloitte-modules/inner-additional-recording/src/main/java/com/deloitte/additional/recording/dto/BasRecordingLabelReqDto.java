package com.deloitte.additional.recording.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/23/14:37
 * @Description:
 */
@Data
public class BasRecordingLabelReqDto implements Serializable {
    /**
     * 分页参数开始页 1
     */
    private Integer pageNum = 1;
    /***
     *分页参数 默认是10
     */
    private Integer pagesize = 10;
    /**
     * 主体/code
     */
    private String entityCode;
    /**
     * 主体名称
     */
    private String entityName;
    /**
     * 报告期
     */
    private Date periodReportTime;

    /**
     * 表名称
     */
    private String tableCode;
    /**
     * 标签名
     */
    private String lableCode;
    /**
     * 状态
     */
    private Integer status;
    //标签补录人员Id

    private Integer periodCollocterId;
    //标签审核人员Id

    private Integer periodApproverId;
}
