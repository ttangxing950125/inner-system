package com.deloitte.crm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 正杰
 * @description: BondsDetailDto
 * @date 2022/9/25
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "BondsDetailDto", description = "id为 entity_attr的attr_id name为 entity_attr的name entity_attr_value的value")
public class BondsDetailDto {

    /**
     * entity_attr的attr_id
     */
    @ApiModelProperty(value = "entity_attr的关联id")
    private Integer id;

    /**
     * entity_attr的name
     */
    @ApiModelProperty(value = "债卷属性名")
    private String name;

    /**
     * entity_attr_value的value
     */
    @ApiModelProperty(value = "债卷属性名")
    private String value;

}
