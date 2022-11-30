package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.BasEvdTaskData;
import com.deloitte.additional.recording.mapper.BasEvdTaskDataMapper;
import com.deloitte.additional.recording.service.BasEvdTaskDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/30
 * @描述 BasEvdTaskData实体 业务处理层
 */
@Service
public class BasEvdTaskDataServiceImpl extends ServiceImpl<BasEvdTaskDataMapper, BasEvdTaskData> implements BasEvdTaskDataService {
    @Override
    public List<BasEvdTaskData> findByQualCode(String qualCode) {
        return lambdaQuery().eq(BasEvdTaskData::getQualCode, qualCode).list();
    }
}
