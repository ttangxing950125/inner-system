package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 指标与敞口关系
 *
 * @author XY
 * @date 2022/11/23
 */
@Data
public class FactorOrmVo implements Serializable {

    @ApiModelProperty(value = "指标编码")
    private String code;

    @ApiModelProperty(value = "公式")
    private String formula;

    @ApiModelProperty(value = "敞口")
    private String industryWhitewash;

    @ApiModelProperty(value = "单位  1 元，2 万元，3 亿元，4 数值，5 文本，6 百分比")
    private String unit;

    @ApiModelProperty(value = "精度")
    private Integer accuracy;

    @ApiModelProperty(value = "异常值处理 JSON字符串")
    private String abnormalValueHandle;

    @ApiModelProperty(value = "变动率上限")
    private BigDecimal changeRateUpper;

    @ApiModelProperty(value = "值阈")
    private String thresholdValue;

    @ApiModelProperty(value = "参考枚举类：HierarchyEnum", hidden = true)
    private Integer hierarchy;


}
