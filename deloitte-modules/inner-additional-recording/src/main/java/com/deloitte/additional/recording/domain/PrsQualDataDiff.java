package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @创建人 tangx
 * @创建时间 2022/11/28
 * @描述 企业指标值(历史指标值记录表)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("prs_qual_data_diff")
public class PrsQualDataDiff extends BaseEntity {


    /**
     * 指标code
     */
    private String qualCode;

    /**
     * 主体code
     */
    private String entityCode;
    /**
     * 指标值
     */
    private String qualValue;
    /**
     * 年份
     */
    private String timeValue;
    /**
     *
     */
    private BigDecimal zScore;
    /**
     * 今年指标值减去去年指标值的差
     */
    private BigDecimal lastDiff;
    /**
     * 本年指标值减去前三年均值
     */
    private BigDecimal avgDiff;
    /**
     * 本年指标值减去去年指标值
     */
    @TableField("t_1")
    private BigDecimal t1;
    /**
     * 本年指标值减去前年指标值
     */
    @TableField("t_2")
    private BigDecimal t2;
    /**
     * 本年指标值减去大前年指标值
     */
    @TableField("t_3")
    private BigDecimal t3;
}
