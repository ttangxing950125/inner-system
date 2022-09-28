package com.deloitte.crm.dto;

import com.deloitte.crm.domain.EntityAttr;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 补充信息返回
 *
 * @author 冉浩岑
 * @date 2022/9/28 9:24
*/
@ApiModel(value = "EbntityCrmSupplyTaskDto",description = "补充信息返回")
@Data
@Accessors(chain = true)
public class EntitySupplyBack {
    @ApiModelProperty(value = "企业基本信息")
    private EntityInfo entityInfo;
    @ApiModelProperty(value = "企业属性信息")
    private List<EntityAttr> entityAttrList;
    @ApiModelProperty(value = "角色身份")
    private Integer roleId;
    @ApiModelProperty(value = "属性值信息")
    private List<EntityAttrValue> attrValueList;
}
