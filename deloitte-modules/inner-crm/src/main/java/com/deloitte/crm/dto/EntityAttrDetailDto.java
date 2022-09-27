package com.deloitte.crm.dto;

import com.deloitte.crm.domain.EntityAttrIntype;
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
@ApiModel(value = "BondsDetailDto", description = "该类为主体的单属性传输数据类")
public class EntityAttrDetailDto {

    /**
     * entity_attr的 name 主体属性名
     */
    @ApiModelProperty(value = "主体属性名")
    private String name;

    /**
     * entity_attr_value的 value 主体属性值
     */
    @ApiModelProperty(value = "主体属性值")
    private String value;

    /**
     * 主体属性的id entity_attr的Id
     */
    @ApiModelProperty(value = "主体属性的id entity_attr的Id")
    private Integer attrId;

    /**
     * 主体属性值的id entity_attr_value 的 Id
     */
    @ApiModelProperty(value = "主体属性值的id entity_attr_value 的 Id")
    private Integer attrValueId;

    /**
     * entity_attr的 multiple 表示主体的该属性能否拥有多个值
     */
    @ApiModelProperty(value = "entity_attr的 multiple 表示主体的该属性能否拥有多个值")
    private Boolean multiple;

    /**
     * entity_attr_intype 级联对象的顶级对象
     */
    @ApiModelProperty(value = "entity_attr_intype 级联对象的顶级对象")
    private EntityAttrIntype parent;

}
