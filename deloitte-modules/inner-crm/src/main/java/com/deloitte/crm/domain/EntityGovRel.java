package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.deloitte.common.core.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 【请填写功能名称】对象 entity_gov_rel
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Data
@Accessors(chain = true)
public class EntityGovRel implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 城投主体code */
    @Excel(name = "城投主体code")
    private String entityCode;

    /** 德勤政府code */
    @Excel(name = "德勤政府code")
    private String dqGovCode;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}







