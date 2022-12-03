package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 基础质量
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiddleQualityVo implements Serializable {

    @ApiModelProperty(value = "字段代码")
    private String code;

    @ApiModelProperty(value = "字段中文名称")
    private String name;

    @ApiModelProperty(value = "数据时间")
    private String reportDate;

    @ApiModelProperty(value = "精度")
    private Double accuracy;

    @ApiModelProperty(value = "值域")
    private String thresholdValue;

    @ApiModelProperty(value = "迁徙率")
    private String migrationRate;

    @ApiModelProperty(value = "是否通过系统质检")
    private String isSystemInspection;
}
