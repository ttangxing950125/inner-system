package com.deloitte.crm.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 正杰
 * @description: GovNode
 * @date 2022/10/29
 */
@Data
@Accessors(chain = true)
public class GovNode {

    @ApiModelProperty(value = "政府主体官方名称")
    private String govName;

    @ApiModelProperty(value = "子集")
    private GovNode children;

}
