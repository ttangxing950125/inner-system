package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 指标Evd查询参数工具类
 * @author 冉浩岑
 * @date 2022/11/09 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class QualEvdVo {
    /** 指标code */
    private String code;
    /** evd显示类型 */
    private String dispType;
    /** evd显示类型 */
    private String disp_type;
    /** evd名称 */
    private String name;
}
