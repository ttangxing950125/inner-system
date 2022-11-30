package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @创建人 tangx
 * @创建时间 2022/11/30
 * @描述 表  bas_evd_task_data 实体
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("bas_evd_task_data")
public class BasEvdTaskData extends BaseEntity {


    /**
     * 指标code
     */
    private  String qualCode;
    /**
     * 基本面数据值
     */
    private String baseData;
    /**
     * 缺失值
     */
    private Integer loseValue;
    /**
     * 最大值
     */
    private Integer maxValue;
    /**
     * 最小值
     */
    private Integer minValue;
    /**
     * 中位数
     */
    private Integer midValue;
    /**
     * 众数
     */
    private Integer modeValue;
    /**
     * 方差
     */
    private Integer variance;
    /**
     * 标准差
     */
    private Integer standardDeviation;
    /**
     *
     */
    @TableField("percent_5")
    private String percent5;
    /**
     *
     */
    @TableField("percent_25")
    private String percent25;
    /**
     *
     */
    @TableField("percent_50")
    private String percent50;
    /**
     *
     */
    @TableField("percent_75")
    private String percent75;
    /**
     *
     */
    @TableField("percent_95")
    private String percent95;

}
