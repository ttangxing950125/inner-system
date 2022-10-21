package com.deloitte.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/10/21 10:41
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class GovInfoDetailVo {
    private String key;
    private List<String> value;
}
