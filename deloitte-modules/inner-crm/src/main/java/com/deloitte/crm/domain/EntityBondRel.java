package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 【请填写功能名称】对象 entity_bond_rel
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Data
@Accessors(chain=true)
public class EntityBondRel
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 主体code */
    @Excel(name = "主体code")
    private String entityCode;

    /** 债券code */
    @Excel(name = "债券code")
    private String bdCode;

    /** 关系状态 */
    @Excel(name = "关系状态")
    private Integer status;

    /** 新发行人名称 */
    @Excel(name = "新发行人名称")
    private String newEntityName;

    /** 新发行人code */
    @Excel(name = "新发行人code")
    private String newEntityCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

    @TableField(exist = false)
    private EntityInfo entityInfo;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
