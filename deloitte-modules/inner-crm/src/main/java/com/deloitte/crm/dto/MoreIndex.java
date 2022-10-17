package com.deloitte.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 更多指标查询工具
 * @author 冉浩岑
 * @date 2022/10/16 8:55
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MoreIndex {
    //响应数据
    /** 响应查询得到指标名称-表头 */
    private String key;
    /** 查询得到指标值 */
    private String value;

    //查询参数
    /** 传入添加查询得到指标名称-表头 */
    private String name;
    /** 传入添加查询得到指标id */
    private String id;
}
