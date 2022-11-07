package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.mapper.SysDictDataMapper;
import com.deloitte.additional.recording.service.SysDictDataService;
import com.deloitte.common.core.domain.R;
import com.deloitte.system.api.domain.SysDictData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 字典表服务层
 *
 * @author 冉浩岑
 * @date 2022/11/7 17:09
*/
@Service("sysDictDataService")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Resource
    private SysDictDataMapper sysDictDataMapper;

    /**
     * 查询可查询年份
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:28
     */
    @Override
    public R getYear() {
        return R.ok(sysDictDataMapper.selectList(new QueryWrapper<SysDictData>().lambda().eq(SysDictData::getDictType, Common.SEARCH_YEAR)));
    }
    /**
     * 查询可查询数据源
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:28
     */
    @Override
    public R getDataSource() {
        return R.ok(sysDictDataMapper.selectList(new QueryWrapper<SysDictData>().lambda().eq(SysDictData::getDictType, Common.DATA_SOURCE)));
    }
    /**
     * 查询可显示类型
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:28
     */
    @Override
    public R getShowType() {
        return R.ok(sysDictDataMapper.selectList(new QueryWrapper<SysDictData>().lambda().eq(SysDictData::getDictType, Common.SHOW_TYPE)));
    }
}
