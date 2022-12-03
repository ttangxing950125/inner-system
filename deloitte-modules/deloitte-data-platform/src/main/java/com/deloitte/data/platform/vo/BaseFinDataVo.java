package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 基础数据字典
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseFinDataVo implements Serializable {

    @ApiModelProperty(value = "字段代码")
    private String code;

    @ApiModelProperty(value = "字段中文名称")
    private String name;

    @ApiModelProperty(value = "数据时间")
    private String reportDate;

    @ApiModelProperty(value = "精度")
    private double accuracy;

    @ApiModelProperty(value = "值域")
    private String thresholdValue;

    @ApiModelProperty(value = "数据优先级")
    private String dataPriority;

    @ApiModelProperty(value = "推荐数据是否缺失 0 否，1 是")
    private String isDataMiss;

    @ApiModelProperty(value = "是否超过阈值  0 否，1 是")
    private String isThresholdExceeded;

    @ApiModelProperty(value = "是否通过系统质检  0 否，1 是")
    private String isSystemInspection;

    @ApiModelProperty(value = "是否通过人工质检  0 否，1 是")
    private String isArtificialInspection;

    @ApiModelProperty(value = "是否人工补录  0 否，1 是")
    private String isArtificialRecording;

    @ApiModelProperty(value = "数据Id")
    private Long dataId;

    @ApiModelProperty(value = "年份")
    private String year;
}