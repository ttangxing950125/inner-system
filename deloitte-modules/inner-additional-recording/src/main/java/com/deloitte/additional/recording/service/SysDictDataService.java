package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.system.api.domain.SysDictData;

import java.util.List;
import java.util.Map;

/**
 * 字典表接口
 *
 * @author 冉浩岑
 * @date 2022/11/7 17:17
*/
public interface SysDictDataService extends IService<SysDictData> {
    /**
     * 查询可查询年份
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:28
     */
    R getYear();
    /**
     * 查询可查询数据源
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:28
     */
    R getDataSource();
    /**
     * 查询可显示类型
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:28
     */
    R getShowType();

    /**
     * 获取月份 下拉框专用方法
     * @return
     */
    List<Map<String, Object>> finAllsysDictData();
}
