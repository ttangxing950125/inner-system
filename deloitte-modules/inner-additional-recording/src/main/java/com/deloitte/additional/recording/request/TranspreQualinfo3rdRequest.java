package com.deloitte.additional.recording.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @创建人 tangx
 * @创建时间 2022/11/17
 * @描述 接受前端请求体
 */
@Data
@ApiModel("映射指标请求体接收体")
public class TranspreQualinfo3rdRequest implements Serializable {

    @ApiModelProperty("id")
    public Integer id;

    @ApiModelProperty("补录敞口id")
    public Integer plusModelid;
    @ApiModelProperty("补录版本id")
    public Integer plusVersion;
    @ApiModelProperty("补录指标id")
    public Integer plusQualid;
    @ApiModelProperty("补录指标code")
    public String plusQualcode;
    @ApiModelProperty("补录evd_id，定性为0，定量要去关联出来")
    public  Integer plusRuleid;
    @ApiModelProperty("值转换倍率，p*pt_times = t  (单位为%,带公式的数据库存的为小数值，不用乘以0.01)")
    public Double ptTimes;
    @ApiModelProperty("版本的前缀")
    public String prefix;
    @ApiModelProperty(" 中心指标名称")
    public String tarQualname;
    @ApiModelProperty("中心指标id")
    public String tarQualid;
    @ApiModelProperty(" 1-定性，2-定量，3-政府")
    public String tarType;
    @ApiModelProperty("目标表拼接单位")
    public String tarUnit;
    @ApiModelProperty("年份")
    public String dataYear;

    @ApiModelProperty("敞口名称")
    private String userModel;

    @ApiModelProperty("单位")
    private String plusUnit;
}
