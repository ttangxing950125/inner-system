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
public class GovAttrByDto implements Serializable
{

    List<Map<String,String>>mapList;

    private Integer pageSize;

    private Integer pageNum;
    /**
     * 公募债券
    */
    private Integer isPublic;
    /**
     * 私募债券
     */
    private Integer isPrivate;
    /**
     * ABS
     */
    private Integer isABS;
    /**
     * 集合债券
     */
    private Integer isMix;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
