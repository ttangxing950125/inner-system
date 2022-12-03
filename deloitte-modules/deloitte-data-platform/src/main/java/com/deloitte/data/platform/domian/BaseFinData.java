package com.deloitte.data.platform.domian;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 德勤基础数据表
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "BaseFinData对象", description = "德勤基础数据表 ")
public class BaseFinData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "财报编码")
    private String code;

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "wind值")
    private String windValue;

    @ApiModelProperty(value = "同花顺值")
    private String flushValue;

    @ApiModelProperty(value = "ocr值")
    private String ocrValue;

    @ApiModelProperty(value = "推荐值")
    private String suggestValue;

    @ApiModelProperty(value = "推荐值来源  1 wind数据库，2 wind客户端，3 同花顺，4 OCR")
    private String suggestSource;

    @ApiModelProperty(value = "变动率")
    private BigDecimal changeRate;
 
    @ApiModelProperty(value = "是否超过阈值 0 否，1 是")
    @TableField(value = "is_threshold_exceeded")
    private Boolean thresholdExceeded;

    @ApiModelProperty(value = "是否通过系统质检 0 否，1 是")
    @TableField(value = "system_inspection")
    private Boolean systemInspection;

    @ApiModelProperty(value = "是否通过人工质检 0 否，1 是")
    @TableField(value = "is_artificial_inspection")
    private Boolean artificialInspection;

    @ApiModelProperty(value = "是否人工补录 0 否，1 是")
    @TableField(value = "is_artificial_recording")
    private Boolean artificialRecording;

    @ApiModelProperty(value = "是否通过勾稽校验 0 否，1 是")
    @TableField(value = "is_data_check")
    private Boolean dataCheck;

    @ApiModelProperty(value = "报告期")
    private LocalDate reportDate;

    @ApiModelProperty(value = "公告日期")
    private LocalDate announcementDate;

}
