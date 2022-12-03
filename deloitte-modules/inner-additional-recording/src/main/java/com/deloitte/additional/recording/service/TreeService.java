package com.deloitte.additional.recording.service;

import com.deloitte.common.core.domain.R;

/**
 * @author 冉浩岑
 * @date 2022/11/22 9:53
 */
public interface TreeService {
    /**
     * 树性结构统计
     *
     * @param type 查询类型 0.版本 1.敞口 2.指标 3.evd
     * @param value  参数 Id
     * @param selYearValue  年份 版本年份
     * @return R
     * @author 冉浩岑
     * @date 2022/11/21 10:01
     */
    R treeDataList(Integer type, String value, String selYearValue);
}
