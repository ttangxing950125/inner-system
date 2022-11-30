package com.deloitte.additional.recording.vo.qual;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @创建人 tangx
 * @创建时间 2022/11/29
 * @描述 指标对应主体分布面板响应体
 */
@ApiModel("指标对应主体分布面板响应体")
@Data
public class PrsQualDataPanelVO {

    @ApiModelProperty("主体数")
    private long entityCount;

    @ApiModelProperty("占比")
    private BigDecimal rate;
}
