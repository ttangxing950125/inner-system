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

    /** 百强县 */
    private Integer hundred;
    /** 城市规模 */
    private List<Integer> govScale;
    /** 城市分级 */
    private List<Integer> govGrading;
    /** 省级行政区 */
    private Integer isProvince;
    /** 地级行政区 */
    private Integer isCity;
    /** 县级行政区 */
    private Integer isCounty;
    /** 经开高新区 */
    private Integer isJKGX;
    /** 八大经济区 *///    八大经济区 ——> 经济区归属
    private Integer eightER ;
    /** 19个城市群 *///    19个城市群 ——> 城市群归属
    private Integer nineteenCity;
    /** 国家中心城市 *///    国家中心城市 ——> 是否为国家中心城市
    private Integer CCity;
    /** 省会城市 *///    省会城市 ——> 是否为省会城市
    private Integer provincial;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
