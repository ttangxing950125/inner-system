package com.deloitte.crm.vo;

import com.deloitte.crm.domain.EntityBondRel;
import com.deloitte.crm.domain.EntityInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author 正杰
 * @description: EntityAndBondInfo
 * @date 2022/9/25
 */
@Data
@Accessors(chain = true)
public class EntityAndBondInfoVo {

    /**
     * 主体信息
     */
    @ApiModelProperty(value="主体基本信息")
    private EntityInfo entityInfo;

    /**
     * 新发行信息
     */
    @ApiModelProperty(value="主体与债券关系")
    private EntityBondRel entityBondRel;

    /**
     * key => name 分为 entityAttr 和 entityAttrCate
     * value => value 为对应的对象
     */
    @ApiModelProperty(value="债卷属性与值")
    private List<Map<String,Object>> entityAttrNameValue;

}
