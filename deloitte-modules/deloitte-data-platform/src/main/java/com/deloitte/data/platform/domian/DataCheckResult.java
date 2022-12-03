package com.deloitte.data.platform.domian;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据校验结果表
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="DataCheckResult对象", description="数据校验结果表 ")
public class DataCheckResult extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据校验模型ID")
    private Integer checkId;

    @ApiModelProperty(value = "报告期")
    private LocalDate reportDate;

    @ApiModelProperty(value = "是否通过系统质检 0 否，1 是")
    private String isSystemInspection;

    @ApiModelProperty(value = "是否通过人工质检 0 否，1 是")
    private String isArtificialInspection;

    @ApiModelProperty(value = "是否需要人工质检 0 否，1 是")
    private String isNeedArtificialInspection;

    @ApiModelProperty(value = "通过率(%)")
    private BigDecimal passingRatio;
}
