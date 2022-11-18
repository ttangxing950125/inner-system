package com.deloitte.crm.domain.dto;

import com.deloitte.crm.domain.GovInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 冉浩岑
 * @date 2022/10/13 14:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GovInfoDetails {
    /**
     * 主体基本信息，主体行政区划
     */
    private GovInfo govInfo;

    /**
     * 关联政府
     */
    private GovInfo relationGov;

    /**
     * 关联企业
     */
    private Long relationEntity;

}
