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
    /** 上市总计 */
    private Long listTotle;
    /** 上市存续 */
    private Long listLive;
    /** 已经退市 */
    private Long listDie;
}
