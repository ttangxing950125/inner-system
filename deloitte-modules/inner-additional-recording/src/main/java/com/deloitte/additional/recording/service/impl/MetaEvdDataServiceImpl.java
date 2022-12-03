package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.MetaEvdData;
import com.deloitte.additional.recording.dto.GetMetaEvdDatasDto;
import com.deloitte.additional.recording.mapper.MetaEvdDataMapper;
import com.deloitte.additional.recording.service.MetaEvdDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/16/11:11
 * @Description:
 */
@Service("metaEvdDataServiceImpl")
public class MetaEvdDataServiceImpl extends ServiceImpl<MetaEvdDataMapper, MetaEvdData> implements MetaEvdDataService {
    @Resource
    private MetaEvdDataMapper evdDataMapper;

    @Override
    public IPage<MetaEvdData> getMetaEvdDatasByPage(Page<MetaEvdData> page, GetMetaEvdDatasDto requestParam) {
        IPage<MetaEvdData> pageResult = evdDataMapper.getMetaEvdDatasByPage(page, requestParam);
        return pageResult;
    }
}
