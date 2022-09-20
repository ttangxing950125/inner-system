package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 entity_attr
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class EntityAttr extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** entity_attr_cate表中的id */
    @Excel(name = "entity_attr_cate表中的id")
    private Long attrCateId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String attrCateName;

    /** 属性名 */
    @Excel(name = "属性名")
    private String name;

    /** 字段类型，数据类型 */
    @Excel(name = "字段类型，数据类型")
    private String dataType;

    /** 数据来源 */
    @Excel(name = "数据来源")
    private String source;

    /** 数据来源，sys_dict_data   attr_source */
    @Excel(name = "数据来源，sys_dict_data   attr_source")
    private Long sourceId;

    /** 是否允许多个值 */
    @Excel(name = "是否允许多个值")
    private Integer multiple;

    /** 1 - 企业主体属性 2 - 政府主体属性 */
    @Excel(name = "1 - 企业主体属性 2 - 政府主体属性")
    private Long attrType;

    /** 说明/判断依据 */
    @Excel(name = "说明/判断依据")
    private String remarks;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAttrCateId(Long attrCateId) 
    {
        this.attrCateId = attrCateId;
    }

    public Long getAttrCateId() 
    {
        return attrCateId;
    }
    public void setAttrCateName(String attrCateName) 
    {
        this.attrCateName = attrCateName;
    }

    public String getAttrCateName() 
    {
        return attrCateName;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setDataType(String dataType) 
    {
        this.dataType = dataType;
    }

    public String getDataType() 
    {
        return dataType;
    }
    public void setSource(String source) 
    {
        this.source = source;
    }

    public String getSource() 
    {
        return source;
    }
    public void setSourceId(Long sourceId) 
    {
        this.sourceId = sourceId;
    }

    public Long getSourceId() 
    {
        return sourceId;
    }
    public void setMultiple(Integer multiple) 
    {
        this.multiple = multiple;
    }

    public Integer getMultiple() 
    {
        return multiple;
    }
    public void setAttrType(Long attrType) 
    {
        this.attrType = attrType;
    }

    public Long getAttrType() 
    {
        return attrType;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setCreated(Date created) 
    {
        this.created = created;
    }

    public Date getCreated() 
    {
        return created;
    }
    public void setUpdated(Date updated) 
    {
        this.updated = updated;
    }

    public Date getUpdated() 
    {
        return updated;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
