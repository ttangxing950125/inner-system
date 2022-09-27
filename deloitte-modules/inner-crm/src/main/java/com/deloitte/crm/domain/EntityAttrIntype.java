package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 正杰
 * @description: EntityAttrIntype
 * @date 2022/9/27
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "BondsDetailDto", description = "entity_attr_intype")
public class EntityAttrIntype {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Integer id;

    /**
     * 关联 entity_attr 的 id
     */
    @ApiModelProperty(value = "关联 entity_attr 的 id")
    private Integer attrId;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Integer pId;

    /**
     * 属性值
     */
    @ApiModelProperty(value = "属性值")
    private String value;

    /**
     * 属性的子集
     */
    @ApiModelProperty(value = "属性的子集")
    @TableField(exist = false)
    private List<EntityAttrIntype> children;

}
