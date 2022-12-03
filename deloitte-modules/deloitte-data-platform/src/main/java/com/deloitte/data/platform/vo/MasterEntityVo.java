package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 敞口与实体关系
 *
 * @author XY
 * @date 2022/11/23
 */
@Data
public class MasterEntityVo implements Serializable {

    @ApiModelProperty(value = "主体名称")
    private String entityName;

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "业务场景")
    private String businessScene;

    @ApiModelProperty(value = "业务线")
    private String businessLine;

    @ApiModelProperty(value = "敞口")
    private String industryWhitewash;

}
