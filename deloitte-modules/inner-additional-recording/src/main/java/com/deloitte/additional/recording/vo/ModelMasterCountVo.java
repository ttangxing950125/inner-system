package com.deloitte.additional.recording.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 王大鹏
 * 分组查询敞口数量的实体类
 */
@Data
public class ModelMasterCountVo implements Serializable {

    /**
     *敞口的编码
     */
    private String modelCode;

    /**
     *敞口的名称
     */
    private String modelName;

    /**
     *敞口的数量
     */
    private Integer count;
}
