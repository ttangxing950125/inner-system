package com.deloitte.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 冉浩岑
 * @date 2022/10/20 17:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ParentLevelVo {
    private String name ;
    private Object value;
}
