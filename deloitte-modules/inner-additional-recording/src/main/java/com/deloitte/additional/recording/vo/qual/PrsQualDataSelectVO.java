package com.deloitte.additional.recording.vo.qual;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @创建人 tangx
 * @创建时间 2022/11/23
 * @描述 指标下拉选择列表
 */
@ApiModel("指标下拉选择列表响应体")
@Data
public class PrsQualDataSelectVO {

    @ApiModelProperty("指标id")
    private Integer id;

    @ApiModelProperty("指标名称")
    private String qualName;

    @ApiModelProperty("指标code")
    private String qualCode;
}
