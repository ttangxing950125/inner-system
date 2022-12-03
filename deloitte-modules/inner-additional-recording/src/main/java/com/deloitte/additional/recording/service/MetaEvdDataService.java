package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.MetaEvdData;
import com.deloitte.additional.recording.dto.GetMetaEvdDatasDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/16/11:10
 * @Description:
 */
public interface MetaEvdDataService extends IService<MetaEvdData> {

    IPage<MetaEvdData> getMetaEvdDatasByPage(Page<MetaEvdData> page, GetMetaEvdDatasDto requestParam);
}
