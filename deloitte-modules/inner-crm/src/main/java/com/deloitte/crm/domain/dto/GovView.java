package com.deloitte.crm.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 冉浩岑
 * @date 2022/10/17 8:50
 */
@Data
@Accessors(chain = true)
public class GovView {
    /** 政府总计 */
    private Integer govTotle;
    /** 省级行政区 */
    private Integer province;
    /** 地市级行政区 */
    private Integer city;
    /** 县级行政区 */
    private Integer area;
    /** 经开高新区 */
    private Integer gx;
    /** 未设置区域 */
    private Integer noLevel;
}
