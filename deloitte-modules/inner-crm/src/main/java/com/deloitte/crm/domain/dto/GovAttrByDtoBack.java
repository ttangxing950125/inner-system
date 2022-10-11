package com.deloitte.crm.domain.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 地方政府-更多指标-主体范围    返回对象
 * 
 * @author 冉浩岑
 * @date 2022-10-11
 */
@Data
@Accessors(chain = true)
public class GovAttrByDtoBack implements Serializable
{

    List<Map<String,String>>mapList;

    private Integer pageSize;

    private Integer pageNum;

    /** 城市规模 */
    private List<GovRangeValue> govScale;
    /** 城市分级 */
    private List<GovRangeValue> govGrading;


    /** 省级行政区 */
    private List<GovRangeValue> isProvince;
    /** 地级行政区 */
    private List<GovRangeValue> isCity;
    /** 县级行政区 */
    private List<GovRangeValue> isCounty;
    /** 经开高新区 */
    private List<GovRangeValue> isJKGX;


    /** 八大经济区 *///    八大经济区 ——> 经济区归属
    private List<GovRangeValue> eightER ;
    /** 19个城市群 *///    19个城市群 ——> 城市群归属
    private List<GovRangeValue> nineteenCity;


    /** 百强县 */
    private Integer hundred;
    /** 国家中心城市 *///    国家中心城市 ——> 是否为国家中心城市
    private Integer CCity;
    /** 省会城市 *///    省会城市 ——> 是否为省会城市
    private Integer provincial;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}

