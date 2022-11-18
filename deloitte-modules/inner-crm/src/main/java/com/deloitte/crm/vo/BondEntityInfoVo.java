package com.deloitte.crm.vo;

import com.deloitte.crm.domain.EntityInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author 正杰
 * @description: BondInfoEditVo
 * @date 2022/9/28
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BondEntityInfoVo {

    /**
     * 表示 三张表中的某条数据的 id
     */
    @ApiModelProperty(value = "id")
    @NotNull(message = "id值不能为空")
    private Integer id;

    /**
     * 表名称
     */
    @ApiModelProperty(value = "表名称 1.ENTITY_INFO 2.BOND_INFO 3.ENTITY_ATTR_VALUE")
    private Integer table;

    /**
     * 是否允许修改
     */
    @ApiModelProperty(value = "该数据是否允许修改")
    private Boolean enableEdite = true;

    /**
     * table=> entity_attr 的 name
     */
    @ApiModelProperty(value = "属性中文名")
    private String name;

    /**
     * 属性字段名
     */
    @ApiModelProperty(value = "属性字段名")
    private String filedName;

    /**
     * table=> entity_attr_value 的 value
     */
    private String value;

    /**
     * 没有查到具体值时 返回参数
     * @param name
     */
    public BondEntityInfoVo(String name,String filedName){
        this.name = name;
        this.filedName = filedName;
        enableEdite = false;
    }

    /**
     * 组装实体 entity_info 与 bond_info
     */
    public BondEntityInfoVo(BondEntityInfoVo temp,Boolean enableEdite,String name,String filedName,String value){
        this.id = temp.getId();
        this.table = temp.getTable();
        this.enableEdite = enableEdite;
        this.name = name;
        this.filedName = filedName;
        this.value = value;
    }

}
