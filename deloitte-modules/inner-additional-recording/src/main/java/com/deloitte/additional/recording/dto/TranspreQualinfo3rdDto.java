package com.deloitte.additional.recording.dto;

import lombok.Data;

/**
 * @创建人 tangx
 * @创建时间 2022/11/16
 * @描述 指标映射 -dto
 */
@Data
public class TranspreQualinfo3rdDto {

    /**
     * id
     */
    private Integer id;

    /**
     * 版本名称
     */
    private String versionName;

    /**
     * 敞口名称
     */
    private String masterName;

    /**
     * 指标名称
     */
    private String qualName;
    /**
     * 补录敞口id
     */
    private Integer masterId;
    /**
     * 补录版本id
     */
    private Integer versionId;

    /**
     * 补录指标code
     */
    private String plusQualcode;
    /**
     * 补录指标id
     */
    private Integer plusQualid;
    /**
     * 值转换倍率，p*pt_times = t  (单位为%,带公式的数据库存的为小数值，不用乘以0.01)
     */
    private Double ptTimes;
    /**
     * 补录指标code
     */
    private String qualCode;

    /**
     * 中心指标id
     */
    private String tarQualid;
    /**
     * 中心指标名称
     */
    private String tarQualname;
    /**
     * 1-定性，2-定量，3-政府
     */
    private String tarType;
    /**
     * 年份
     */
    private String dataYear;

    /**
     * 单位
     */
    private String unit;

    /**
     *
     */
    private String evdName;

    private String evdCode;
}
