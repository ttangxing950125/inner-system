package com.deloitte.additional.recording.vo.qualinfo3rd;

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
    private String versionName;

    @ApiModelProperty("补录敞口名称")
    private String masterName;

    @ApiModelProperty("补录指标code")
        private String plusQualcode;

    @ApiModelProperty("补录指标id")
    private Integer plusQualid;

    @ApiModelProperty("值转换倍率，p*pt_times = t  (单位为%,带公式的数据库存的为小数值，不用乘以0.01)")
    private Double ptTimes;

    @ApiModelProperty("补录指标code")
    private String qualCode;

    @ApiModelProperty("补录指标名称")
    private String qualName;

    @ApiModelProperty("中心指标id")
    private String tarQualid;

    @ApiModelProperty("中心指标名称")
        private String tarQualname;

    @ApiModelProperty("1-定性，2-定量，3-政府")
    private String tarType;

    @ApiModelProperty("年份")
    private String dataYear;

}
