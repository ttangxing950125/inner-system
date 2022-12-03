package com.deloitte.data.platform.domian;

import com.baomidou.mybatisplus.annotation.TableField;
import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Evidence模型配置
 *
 * @author XY
 * @date 2022/11/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ModelEvidence对象", description = "Evidence模型配置")
public class ModelEvidence extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Evidence名称")
    @TableField(value = "evidence_name")
    private String evidenceName;

    @ApiModelProperty(value = "Evidence编码")
    @TableField(value = "evidence_code")
    private String evidenceCode;

    @ApiModelProperty(value = "Evidence公式")
    @TableField(value = "evidence_formula")
    private String evidenceFormula;

    @ApiModelProperty(value = "Evidence公式描述")
    @TableField(value = "evidence_formula_describe")
    private String evidenceFormulaDescribe;

    @ApiModelProperty(value = "Evidence状态 0 禁用，1 启用")
    @TableField(value = "evidence_status")
    private String evidenceStatus;

    @ApiModelProperty(value = "单位  1 元，2 万元，3 亿元，4 数值，5 文本，6 百分比")
    @TableField(value = "unit")
    private String unit;

    @ApiModelProperty(value = "Evidence精度")
    @TableField(value = "accuracy")
    private Integer accuracy;

    @ApiModelProperty(value = "Evidence变动率上限")
    @TableField(value = "change_rate_upper")
    private BigDecimal changeRateUpper;

    @ApiModelProperty(value = "Evidence值阈")
    @TableField(value = "threshold_value")
    private String thresholdValue;

    @ApiModelProperty(value = "异常值处理 JSON字符串")
    @TableField(value = "abnormal_value_handle")
    private String abnormalValueHandle;

}
