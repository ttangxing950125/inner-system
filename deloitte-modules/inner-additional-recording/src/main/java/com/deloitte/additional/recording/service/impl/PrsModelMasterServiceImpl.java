package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.PrsProjectVersions;
import com.deloitte.additional.recording.mapper.PrsModelMasterMapper;
import com.deloitte.additional.recording.domain.PrsModelMaster;
import com.deloitte.additional.recording.service.PrsModelMasterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * (PrsModelMaster)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("prsModelMasterService")
public class PrsModelMasterServiceImpl extends ServiceImpl<PrsModelMasterMapper, PrsModelMaster> implements PrsModelMasterService {
    @Resource
    private PrsModelMasterMapper prsModelMasterMapper;

    /**
     * @return
     */
    @Override
    public List<Map<String, Object>> finAllPrsModelMaster() {
        CopyOnWriteArrayList<Map<String, Object>> datas = new CopyOnWriteArrayList<>();
        Map<String, Object> maps = new HashMap<>();
        prsModelMasterMapper.selectList(new LambdaQueryWrapper<PrsModelMaster>().eq(PrsModelMaster::getStatus, 1)).parallelStream().filter(e -> StringUtils.isNotEmpty(e.getName())).forEach(e -> {
            maps.put("id", e.getId());
            maps.put("modelCode", e.getModelCode());
            maps.put("name", e.getName());
            datas.add(maps);
        });
        return datas;
    }
}
