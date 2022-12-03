package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 指标等级查询参数工具类
 * @author 冉浩岑
 * @date 2022/11/09 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class QualFactorVo {
    /** 挡位属性 */
    private String factorItem;
    /** 挡位值 */
    private String factorValue;
    /** 挡位id */
    private Integer id;
}
