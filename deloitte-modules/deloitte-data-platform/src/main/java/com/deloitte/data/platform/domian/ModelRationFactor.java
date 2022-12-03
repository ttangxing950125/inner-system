package com.deloitte.data.platform.domian;

import com.baomidou.mybatisplus.annotation.TableField;
import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 定量指标模型
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ModelRationFactor对象", description="定量指标模型 ")
public class ModelRationFactor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "指标名称")
    private String factorName;

    @ApiModelProperty(value = "指标编码")
    private String factorCode;

    @ApiModelProperty(value = "指标公式")
    private String factorFormula;

    @ApiModelProperty(value = "指标公式描述")
    private String factorFormulaDescribe;

    @ApiModelProperty(value = "指标状态 0 启用，1 禁用")
    private String factorStatus;

    @ApiModelProperty(value = "指标精度")
    private Integer accuracy;

    @ApiModelProperty(value = "变动率上限")
    private BigDecimal changeRateUpper;

    @ApiModelProperty(value = "值阈")
    private String thresholdValue;

    @ApiModelProperty(value = "单位  1 元，2 万元，3 亿元，4 数值，5 文本，6 百分比")
    private String unit;

    @ApiModelProperty(value = "异常值处理 JSON字符串")
    @TableField(value = "abnormal_value_handle")
    private String abnormalValueHandle;
//
//    @ApiModelProperty(value = "业务线 来源于data_platform_config_dict表，父类为business_line")
//    private String businessLine;
//
//    @ApiModelProperty(value = "财报粉饰敞口")
//    private String industryWhitewash;

}
