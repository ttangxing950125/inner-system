package com.deloitte.additional.recording.vo.evd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @创建人 tangx
 * @创建时间 2022/12/2
 * @描述 应用层管理-指标列表- evd详情响应体
 */
@Data
@ApiModel("应用层管理-指标列表- evd详情响应体")
public class BasEvdInfoDetailVO {

    @ApiModelProperty("evdCode")
    private String code;

    @ApiModelProperty("evd 名称")
    private String name;
}
