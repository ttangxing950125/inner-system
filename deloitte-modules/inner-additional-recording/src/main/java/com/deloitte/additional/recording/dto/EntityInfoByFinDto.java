package com.deloitte.additional.recording.dto;

import lombok.Data;

/**
 * @author PenTang
 * @date 2022/11/29 11:21
 */
@Data
public class EntityInfoByFinDto {

    /**
     * 实体名称
     */
    private  String entityName;

    /**
     * 年份
     *
     */
    private  String timeValue;

    /**
     * 补录类型(1-三表 2-结构化附注 3-非结构化附注)
     */
    private  Integer  dataType;

    /**
     * 三表附注名字
     */
    private  String   fanName;

    /**
     * 是否上市 0-未上市 1-已上市
     */
    private  Integer list;

    /**
     * 是否发债 0-未发债 1-已发债
     */
    private  Integer issueBonds;


    /**
     * 状态
     */
    private Integer status;

    private  Integer  pageNum;

    private  Integer pageSize;
}
