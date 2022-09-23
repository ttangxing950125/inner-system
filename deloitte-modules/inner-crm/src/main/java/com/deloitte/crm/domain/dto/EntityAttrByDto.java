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

    List<Integer>ids;
    List<Map<String,String>>mapList;

    private Integer pageSize;

    private Integer pageNum;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
