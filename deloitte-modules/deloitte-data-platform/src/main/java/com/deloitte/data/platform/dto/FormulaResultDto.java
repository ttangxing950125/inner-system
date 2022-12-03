package com.deloitte.data.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 公式计算结果
 *
 * @author XY
 * @date 2022/11/18
 */
@Data
@ApiModel("公式计算结果")
public class FormulaResultDto {

    @ApiModelProperty(value = "公式编码")
    private String formulaCode;

    @ApiModelProperty(value = "公式计算结果")
    private String resultValue;

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "上报日期")
    private LocalDate reportDate;

    @ApiModelProperty(value = "原始公式")
    private String primalFormula;

    @ApiModelProperty(value = "翻译后的公式")
    private String translateFormula;

    @ApiModelProperty(value = "参考枚举类：HierarchyEnum")
    private Integer hierarchy;

    @ApiModelProperty(value = "变动率上限")
    private BigDecimal changeRateUpper;
}
