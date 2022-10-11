package com.deloitte.crm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 单个返回值对象
 *
 * @author 冉浩岑
 * @date 2022/10/11 17:21
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class GovRangeValue{
    //属性名
    private String name;
    //属性值
    private Object value;
}