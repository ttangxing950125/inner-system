package com.deloitte.crm.domain.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】对象 entity_attr
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Data
public class EntityAttrByDto implements Serializable
{

    List<Map<String,String>>mapList;

    private Integer pageSize;

    private Integer pageNum;
    /**
     * 0.公募债券 1.私募债券
    */
    private Integer raiseType;
    /**
     * ABS
     */
    private Integer abs;
    /**
     * 集合债券
     */
    private Integer coll;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
