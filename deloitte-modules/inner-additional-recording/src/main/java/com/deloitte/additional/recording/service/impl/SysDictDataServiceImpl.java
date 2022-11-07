package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.SysDictDataMapper;
import com.deloitte.additional.recording.service.SysDictDataService;
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

    @Override
    public List<Map<String, Object>> finAllsysDictData() {
        List<Map<String, Object>> objectMaps = new ArrayList<>();
        List<SysDictData> list = mapper.selectList(new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getDictLabel, "产品年份").eq(SysDictData::getStatus, 0));
        HashMap<String, Object> results = Maps.newHashMap();
        list.stream().forEach(e -> results.put(e.getDictValue(), e.getIsDefault()));
        objectMaps.add(results);
        return objectMaps;
    }
}
