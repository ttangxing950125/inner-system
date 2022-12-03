package com.deloitte.additional.recording.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvidenceDistributionPageDto implements Serializable {

    /**
     * 分页参数开始页 1
     */
    private Integer pageNum = 1;
    /***
     *分页参数 默认是10
     */
    private Integer pagesize = 10;

    /**
     * 版本标的名称
     */
    private List<String> versionNames;

    /**
     * 敞口的id
     */
    private List<Integer> modelMasterIds;

    /**
     * 任务状态
     */
    private Integer taskStatus;

    /**
     *历史是否发债
     */
    private Integer entityBondtag;

    /**
     *当前是否上市 0-未上市 1-已上市
     */
    private Byte list;

    /**
     * 补录人员id
     */
    private List<Integer> collocterIds;

    /**
     * 审核人员id
     */
    private List<Integer> approverIds;

    /**
     * 年份
     */
    private Integer timeValue;

    /**
     * evidence的id
     */
    private List<Integer> evidenceIds;

    /**
     * 指标id
     */
    private List<Integer> qualIds;

    /**
     * 主体id
     */
    private List<Integer> entityIds;

    /**
     * 用户组id
     */
    private List<Integer> groupIds;
}
