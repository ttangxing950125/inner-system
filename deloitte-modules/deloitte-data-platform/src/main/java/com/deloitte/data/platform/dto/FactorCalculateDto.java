package com.deloitte.data.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 指标计算入参
 *
 * @author XY
 * @date 2022/11/19
 */
@Data
@ApiModel("指标计算入参")
public class FactorCalculateDto implements Serializable {

    @ApiModelProperty(value = "根据 year、type、quarter 计算出来的值", hidden = true)
    private LocalDate reportDate;

    @ApiModelProperty(value = "年份", required = true)
    @NotNull(message = "年份不能为空")
    private Integer year;

    @ApiModelProperty(value = "类型 默认年报（年报，季报）", notes = "（预留）暂时未使用", hidden = true)
    private String type;

    @ApiModelProperty(value = "季度（）", notes = "（预留）暂时未使用", hidden = true)
    private Integer quarter;

    @ApiModelProperty(value = "业务场景")
    private String businessScene;

    @ApiModelProperty(value = "业务线")
    private String businessLine;

    @ApiModelProperty(value = "敞口")
    private String industryWhitewash;

    @ApiModelProperty(value = "主体Code")
    private String entityCode;

    @ApiModelProperty(value = "指标Code，如果填写该值，会忽略 业务场景、业务线、敞口 字段")
    private String factorCode;
}
