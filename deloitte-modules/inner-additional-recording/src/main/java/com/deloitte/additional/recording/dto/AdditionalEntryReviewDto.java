package com.deloitte.additional.recording.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 王大鹏
 * 补录审核的传参
 */
@Data
public class AdditionalEntryReviewDto implements Serializable {

    /**
     * 分页参数开始页 1
     */
    private Integer pageNum=1;
    /***
     *分页参数 默认是10
     */
    private Integer pagesize=5;

    /**
     * 版本Id
     */
    private Integer versionId;

    /**
     * 敞口Code
     */
    private String modelCode;

    /**
     * 年份
     */
    private String timeValue;

    /**
     * 是否上市  0-未上市 1-已上市
     */
    private Byte list;

    /**
     * 是否发债 0-未发债 1-已发债
     */
    private Byte entityBondTag;

    /**
     * 任务状态
     */
    private Integer taskStatus;

    /**
     * 1-一般敞口 2-政府敞口
     */
    private Integer modelType;

    /**
     * 1、补录 2、审核
     */
    private Integer type;

    /**
     * 补录人员id
     */
    private List<Integer> collocterIds;

    /**
     * 审核人员id
     */
    private List<Integer> approverIds;

    /**
     * 当前用户
     */
    private Integer currentUserId;


}
