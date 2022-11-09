package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 版本查询指标参数工具类
 * @author 冉浩岑
 * @date 2022/11/09 16:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class VersionMasterEvdVo {
    /** 页面大小，默认10 */
    private Integer pageSize=10;
    /** 页码，默认1 */
    private Integer pageNum=1;
    /** 版本Id */
    private Integer prjId;
    /** 敞口Id集合 */
    private List<String> modelCodes;
}
