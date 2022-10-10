package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 【请填写功能名称】对象 entity_master
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Data
@Accessors(chain = true)
public class EntityMaster
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** model_master的master_code */
    @Excel(name = "model_master的master_code")
    private String masterCode;

    /** entity_info的entity_code */
    @Excel(name = "entity_info的entity_code")
    private String entityCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date update = new Date();

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
