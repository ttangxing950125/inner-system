package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @创建人 tangx
 * @创建时间 2022/12/2
 * @描述 bas_evd_data_task 表实体
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("bas_evd_data_task")
public class BasEvdDataTask extends BaseEntity {

    /**
     * evdcode
     */
    private String evdCode;
    /**
     * 基本面数据值
     */
    private String baseData;
    /**
     * 缺失值
     */
    private String loseValue;
    /**
     * 最大值
     */
    private String maxValue;
    /**
     * 最小值
     */
    private String minValue;
    /**
     * 中位数
     */
    private String midValue;
    /**
     * 众数
     */
    private String modeValue;
    /**
     * 方差
     */
    private String variance;
    /**
     * 标准差
     */
    private String standardDeviation;
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


    public BasEvdDataTask init(String evdCode, String baseData, String loseValue, String maxValue, String minValue, String midValue, String modeValue, String variance, String standardDeviation, String percent5, String percent25, String percent50, String percent75, String percent95) {
        this.evdCode = evdCode;
        this.baseData = baseData;
        this.loseValue = loseValue;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.midValue = midValue;
        this.modeValue = modeValue;
        this.variance = variance;
        this.standardDeviation = standardDeviation;
        this.percent5 = percent5;
        this.percent25 = percent25;
        this.percent50 = percent50;
        this.percent75 = percent75;
        this.percent95 = percent95;
        Date date = new Date();
        this.setCreated(date);
        this.setUpdated(date);
        return this;
    }
}
