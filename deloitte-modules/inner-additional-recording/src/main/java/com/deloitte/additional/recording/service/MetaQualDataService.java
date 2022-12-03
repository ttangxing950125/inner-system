package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.MetaQualData;
import com.deloitte.additional.recording.dto.GetMetaQualDatasDto;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/16/11:09
 * @Description:
 */
public interface MetaQualDataService extends IService<MetaQualData> {

    IPage<MetaQualData> getMetaQualDatasByPage(Page<MetaQualData> page, GetMetaQualDatasDto param);
}
