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
    /** 公募债券 */
    private Integer gov_level;
    /** 百强县 */
    private Integer hundred;
    /** 城市规模 */
    private Integer gov_scale;
    /** 城市分级 */
    private Integer gov_grading;
//    省级行政区
//            经开高新区
//    地级行政区
//            八大经济区
//    县级行政区
//19个城市群
//        国家中心城市
//    省会城市

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
