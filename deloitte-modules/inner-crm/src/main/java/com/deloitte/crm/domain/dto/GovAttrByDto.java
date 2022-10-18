package com.deloitte.crm.domain.dto;

import com.alibaba.fastjson.JSON;
import com.deloitte.crm.dto.MoreIndex;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Data
public class GovAttrByDto implements Serializable
{
    private List<MoreIndex>mapList;

    private Integer pageSize;

    private Integer pageNum;

    /** 城市规模 */
    private List<String> govScale;
    /** 城市分级 */
    private List<String> govGrading;


    /** 省级行政区 */
    private List<String> isProvince;
    /** 地级行政区 */
    private List<String> isCity;
    /** 县级行政区 */
    private List<String> isCounty;
    /** 经开高新区 */
    private List<String> isJKGX;


    /** 八大经济区 *///    八大经济区 ——> 经济区归属
    private List<String> eightER ;
    /** 19个城市群 *///    19个城市群 ——> 城市群归属
    private List<String> nineteenCity;

    /** 百强县 */
    private Integer hundred;
    /** 国家中心城市 *///    国家中心城市 ——> 是否为国家中心城市
    private Integer CCity;
    /** 省会城市 *///    省会城市 ——> 是否为省会城市
    private Integer provincial;

    private GovAttrByDto govAttrByDto;
    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
