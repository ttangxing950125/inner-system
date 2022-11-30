package com.deloitte.additional.recording.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @创建人 tangx
 * @创建时间 2022/11/30
 * @描述 表  bas_evd_task_data 响应体
 */
@Data
@ApiModel("基本面数据列表响应体")
public class BasEvdTaskDataVO {


    @ApiModelProperty("id")
    private Integer id;
    /**
     * 指标code
     */
    @ApiModelProperty("指标code")
    private String qualCode;
    /**
     * 基本面数据值
     */
    @ApiModelProperty("基本面数据值")
    private String baseData;
    /**
     * 缺失值
     */
    @ApiModelProperty("缺失值")
    private Integer loseValue;
    /**
     * 最大值
     */
    @ApiModelProperty("最大值")
    private Integer maxValue;
    /**
     * 最小值
     */
    @ApiModelProperty("最小值")
    private Integer minValue;
    /**
     * 中位数
     */
    @ApiModelProperty("中位数")
    private Integer midValue;
    /**
     * 众数
     */
    @ApiModelProperty("众数")
    private Integer modeValue;
    /**
     * 方差
     */
    @ApiModelProperty("方差")
    private Integer variance;
    /**
     * 标准差
     */
    @ApiModelProperty("标准差")
    private Integer standardDeviation;
    /**
     *
     */
    @ApiModelProperty("5%")
    private String percent5;
    /**
     *
     */
    @ApiModelProperty("25%")
    private String percent25;
    /**
     *
     */
    @ApiModelProperty("50%")
    private String percent50;
    /**
     *
     */
    @ApiModelProperty("75%")
    private String percent75;
    /**
     *
     */
    @ApiModelProperty("95%")
    private String percent95;

}
