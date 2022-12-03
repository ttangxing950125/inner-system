package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.mapper.SysDictDataMapper;
import com.deloitte.additional.recording.service.SysDictDataService;
import com.deloitte.common.core.domain.R;
import com.deloitte.system.api.domain.SysDictData;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典表服务层
 *
 * @author 冉浩岑
 * @date 2022/11/7 17:09
 */
@Service("sysDictDataService")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {
    @Resource
    private SysDictDataMapper mapper;

    /**
     * 查询可查询年份
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:28
     */
    @Override
    public R getYear() {
        return R.ok(mapper.selectList(new QueryWrapper<SysDictData>().lambda().eq(SysDictData::getDictType, Common.SEARCH_YEAR)));
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
        return R.ok(mapper.selectList(new QueryWrapper<SysDictData>().lambda().eq(SysDictData::getDictType, Common.DATA_SOURCE)));
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
        return R.ok(mapper.selectList(new QueryWrapper<SysDictData>().lambda().eq(SysDictData::getDictType, Common.SHOW_TYPE)));
    }

    /**
     * 统计-数据清单模块 获取年份 从数据字典表中获取
     *
     * @return
     * @see com.deloitte.system.api.domain.SysDictData
     */
    @Override
    public List<Map<String, Object>> finAllsysDictData() {
        List<Map<String, Object>> objectMaps = new ArrayList<>();
        List<SysDictData> list = mapper.selectList(new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getDictType, Common.DICTTYPE_SEARCH_YEAR).eq(SysDictData::getStatus, 0));
        HashMap<String, Object> results = Maps.newHashMap();
        list.stream().forEach(e -> results.put(e.getDictValue(), e.getIsDefault()));
        objectMaps.add(results);
        return objectMaps;
    }

    /**
     * 根据类型查询字典
     *
     * @param type 吴鹏鹏
     * @return
     */
    @Override
    public List<SysDictData> findByType(String type) {
        return baseMapper.selectList(new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getDictType, type).eq(SysDictData::getStatus,0));
    }

    /**
     * 根据类型查询字典表默认值
     *
     * @param type 吴鹏鹏
     * @return
     */
    @Override
    public SysDictData findByTypeDefault(String type) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<SysDictData>()
                        .eq(SysDictData::getDictType, type)
                        .eq(SysDictData::getIsDefault, "Y")
        );
    }

    @Override
    public SysDictData findByValueAndType(String userId, String type) {
        return lambdaQuery().eq(SysDictData::getDictValue, userId).eq(SysDictData::getDictType, type).one();
    }
}
