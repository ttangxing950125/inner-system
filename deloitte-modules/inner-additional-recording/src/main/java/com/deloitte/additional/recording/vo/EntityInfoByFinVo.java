package com.deloitte.additional.recording.vo;

import lombok.Data;

/**
 * @author PenTang
 * @date 2022/11/29 10:57
 */
@Data
public class EntityInfoByFinVo {

    /**
     * 实体名称
     */
    private  String entityName;

    /**
     * 时间
     */
    private  String timeValue;

    /**
     * 补录类型(1-三表 2-结构化附注 3-非结构化附注)
     */
    private  Integer  dataType;

    /**
     * code关联的name数据
     */
    private  String name;

    /**
     * 是否上市 0-未上市 1-已上市
     */
    private  String list;

    /**
     * 是否发债 0-未发债 1-已发债
     */
    private  String issueBonds;

    /**
     * 补录人员
     */
    private String  collName;

    /**
     * 补录人员id
     */
    private  Integer collId;

    /**
     * 审批人员
     */
    private String approver ;

    /**
     * 审批人员id
     */
    private Integer approverId;

    /**
     * 状态
     */
    private Integer status;

}
