package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.PrsProjectVersionsMapper;
import com.deloitte.additional.recording.domain.PrsProjectVersions;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (PrsProjectVersions)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Slf4j
@Service("prsProjectVersionsService")
public class PrsProjectVersionsServiceImpl extends ServiceImpl<PrsProjectVersionsMapper, PrsProjectVersions> implements PrsProjectVersionsService {
    @Resource
    private PrsProjectVersionsMapper prsProjectVersionsMapper;

    @Override
    public List<String> findAllPrsProjectVersions() {
        List<PrsProjectVersions> prsProjectVersions = prsProjectVersionsMapper.selectList(new LambdaQueryWrapper<PrsProjectVersions>().eq(PrsProjectVersions::getStatus, 1));
        List<String> collect = prsProjectVersions.parallelStream().filter(e -> StringUtils.isNotEmpty(e.getName())).map(PrsProjectVersions::getName).distinct().collect(Collectors.toList());
        return collect;
    }
}
