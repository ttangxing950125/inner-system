package com.deloitte.additional.recording.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @创建人 tangx
 * @创建时间 2022/11/17
 * @描述 定量 定性 DTO
 */
@Data
public class ModerQuanAndQualFactorDto implements Serializable {

    /**
     * id
     */
    private Integer id;
    /**
     * 指标对应CODE
     */
    private String code;
    /**
     * 敞口CODE
     */
    private String modelCode;
    /**
     * 类型：1定性 2 定量
     */
    private String type;
    /**
     * 敞口CODE
     */
    private String modelMaster;
}
