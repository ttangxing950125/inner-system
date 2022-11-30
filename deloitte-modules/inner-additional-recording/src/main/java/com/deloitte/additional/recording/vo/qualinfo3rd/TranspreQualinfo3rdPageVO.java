package com.deloitte.additional.recording.vo.qualinfo3rd;

import com.deloitte.common.core.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @创建人 tangx
 * @创建时间 2022/11/16
 * @描述 指标映射响应体
 */
@Data
@ApiModel("指标映射响应体")
public class TranspreQualinfo3rdPageVO implements Serializable {


    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("补录敞口id")
    private Integer masterId;

    @ApiModelProperty("补录版本id")
    private Integer versionId;

    @ApiModelProperty("补录版本名称")
    @Excel(name = "版本")
    private String versionName;

    @ApiModelProperty("补录敞口名称")
    @Excel(name = "敞口")
    private String masterName;

    @ApiModelProperty("补录指标code")
    @Excel(name = "指标code")
    private String plusQualcode;

    @ApiModelProperty("补录指标id")
    @Excel(name = "映射qual_id")
    private Integer plusQualid;

    @ApiModelProperty("值转换倍率，p*pt_times = t  (单位为%,带公式的数据库存的为小数值，不用乘以0.01)")
    @Excel(name = "倍率")
    private Double ptTimes;

    @ApiModelProperty("补录指标code")
    private String qualCode;

    @ApiModelProperty("补录指标名称")
    @Excel(name = "指标名称")
    private String qualName;

    @ApiModelProperty("中心指标id")
    private String tarQualid;

    @ApiModelProperty("中心指标名称")
    @Excel(name = "映射指标")
    private String tarQualname;

    @ApiModelProperty("1-定性，2-定量，3-政府")
    @Excel(name = "指标类型", readConverterExp = "1=定性指标,2=定量指标,3=政府指标")
    private String tarType;

    @ApiModelProperty("年份")
    private String dataYear;


    @Excel(name = "evidence")
    private String evdName;

    @Excel(name = "evidencecode")
    private String evdCode;

    @Excel(name = "映射单位")
    private String tarUnit;


    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;


}
