package com.deloitte.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *
 * 键值对工具类
 * @author 冉浩岑
 * @date 2022/10/19 16:56
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class NameValueVo {
    /** 展示名称 */
    private String name;
    /** 展示值 */
    private String value;
}
