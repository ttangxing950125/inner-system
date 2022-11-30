package com.deloitte.additional.recording.vo.qual;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @创建人 tangx
 * @创建时间 2022/11/28
 * @描述 指标差异分页响应体
 */
@Data
@ApiModel("指标差异分页响应体")
public class PrsQualDataDiffPageVO {

    @ApiModelProperty("id")
    private Integer id;
    /**
     * 指标code
     */
    @ApiModelProperty("指标code")
    private String qualCode;

    @ApiModelProperty("主体数量")
    private Integer entityCount;

    @ApiModelProperty("主体名称")
    private String entityName;

    /**
     * 指标值
     */
    @ApiModelProperty("指标值")
    private String qualValue;
    /**
     * 年份
     */
    @ApiModelProperty("年份")
    private String timeValue;
    /**
     *
     */
    @ApiModelProperty("zScore")
    private BigDecimal zScore;
    /**
     * 今年指标值减去去年指标值的差
     */
    @ApiModelProperty("今年指标值减去去年指标值的差")
    private BigDecimal lastDiff;
    /**
     * 本年指标值减去前三年均值
     */
    @ApiModelProperty("本年指标值减去前三年均值")
    private BigDecimal avgDiff;
    /**
     * 本年指标值减去去年指标值
     */
    @ApiModelProperty("本年指标值减去去年指标值")
    private BigDecimal t1;
    /**
     * 本年指标值减去前年指标值
     */
    @ApiModelProperty("本年指标值减去前年指标值")
    private BigDecimal t2;
    /**
     * 本年指标值减去大前年指标值
     */
    @ApiModelProperty("本年指标值减去大前年指标值")
    private BigDecimal t3;
}
