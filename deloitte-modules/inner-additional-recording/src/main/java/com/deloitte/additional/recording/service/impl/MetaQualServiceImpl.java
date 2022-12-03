package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.MetaEvdData;
import com.deloitte.additional.recording.domain.MetaQualData;
import com.deloitte.additional.recording.dto.GetMetaQualDatasDto;
import com.deloitte.additional.recording.mapper.MetaEvdDataMapper;
import com.deloitte.additional.recording.mapper.MetaQualDataMapper;
import com.deloitte.additional.recording.service.MetaEvdDataService;
import com.deloitte.additional.recording.service.MetaQualDataService;
import com.deloitte.common.security.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/16/11:30
 * @Description:
 */
@Service("metaQualServiceImpl")
public class MetaQualServiceImpl extends ServiceImpl<MetaQualDataMapper, MetaQualData> implements MetaQualDataService {
    @Resource
    private MetaQualDataMapper metaQualDataMapper;
    @Override
    public IPage<MetaQualData> getMetaQualDatasByPage(Page<MetaQualData> page, GetMetaQualDatasDto param) {
       return metaQualDataMapper.getMetaQualDatasByPage(page,param);
    }
}
