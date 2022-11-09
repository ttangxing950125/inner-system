package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 版本查询指标返回信息工具类
 * @author 冉浩岑
 * @date 2022/11/09 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class VersionMasterEvdBackVo {
    /** 敞口名称 */
    private String masterName;
    /** 敞口编码 */
    private String masterCode;
    /** 指标名称 */
    private String qualName;
    /** 指标编码 */
    private String qualCode;

}
