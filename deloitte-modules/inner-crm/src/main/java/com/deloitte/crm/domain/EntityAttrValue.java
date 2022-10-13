package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 【请填写功能名称】对象 entity_attr_value
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
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

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
