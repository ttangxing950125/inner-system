package com.deloitte.crm.domain.dto;

import com.alibaba.fastjson.JSON;
import com.deloitte.crm.dto.MoreIndex;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 【请填写功能名称】对象 entity_attr
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Data
public class EntityAttrByDto implements Serializable
{

    private List<MoreIndex>mapList;


    private Integer pageSize;

    private Integer pageNum;

    /**
     * 0.公募债券
     */
    private Integer publicType;

    /**
     * 1.私募债券
     */
    private Integer privateType;

    /**
     * ABS
     */
    private Integer abs;
    /**
     * 集合债券
     */
    private Integer coll;

    /**
     * 港股
     */
    private Integer stockThk;

    /**
     * 内地股
     */
    private Integer stockCn;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
