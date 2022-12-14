package com.deloitte.crm.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 冉浩岑
 * @date 2022/10/17 8:50
 */
@Data
@Accessors(chain = true)
public class EntityListView {
    /** 总计 */
    private Long totle;
    /** 存续 */
    private Long live;
    /** 退市 */
    private Long dead;
}
