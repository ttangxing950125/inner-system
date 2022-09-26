package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import io.swagger.models.auth.In;

import java.io.Serializable;

/**
 * 【请填写功能名称】对象 entity_attr_value
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class EntityAttrValue implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** entity_attr的id */
    @Excel(name = "entity_attr的id")
    private Long attrId;

    /** 主体表或者政府表的德勤code */
    @Excel(name = "主体表或者政府表的德勤code")
    private String entityCode;

    /** 属性值 */
    @Excel(name = "属性值")
    private String value;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setAttrId(Long attrId) 
    {
        this.attrId = attrId;
    }

    public Long getAttrId() 
    {
        return attrId;
    }
    public void setEntityCode(String entityCode) 
    {
        this.entityCode = entityCode;
    }

    public String getEntityCode() 
    {
        return entityCode;
    }
    public void setValue(String value) 
    {
        this.value = value;
    }

    public String getValue() 
    {
        return value;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
