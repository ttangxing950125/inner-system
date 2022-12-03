package com.deloitte.data.platform.domian;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 德勤Evidence数据表
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "EvidenceData对象", description = "德勤Evidence数据表 ")
public class EvidenceData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Evidence编码")
    private String code;

    @ApiModelProperty(value = "Evidence值")
    private String value;

    @ApiModelProperty(value = "Evidence值")
    private BigDecimal changeRate;

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "报告期")
    private LocalDate reportDate;

    @ApiModelProperty(value = "是否通过系统质检 0 否，1 是")
    @TableField(value = "is_system_inspection")
    private Boolean systemInspection;
}
