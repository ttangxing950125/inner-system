package com.deloitte.data.platform.domian;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 指标值表 通过跑批计算出指标结果存入该表
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FinFactorValue对象", description = "指标值表 通过跑批计算出指标结果存入该表")
public class FinFactorValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "指标编码")
    private String factorCode;

    @ApiModelProperty(value = "报告期")
    private LocalDate reportDate;

    @ApiModelProperty(value = "指标结果")
    private BigDecimal factorValue;

    @ApiModelProperty(value = "变动率")
    private BigDecimal changeRate;

    @ApiModelProperty(value = "是否通过系统质检 0 否，1 是")
    @TableField(value = "is_system_inspection")
    private Boolean systemInspection;
}
