package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.BasEvdTaskInfo;
import com.deloitte.additional.recording.dto.BasEvdTaskInfoStatusCountDto;
import com.deloitte.additional.recording.mapper.BasEvdTaskInfoMapper;
import com.deloitte.additional.recording.service.BasEvdTaskInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/29
 * @描述 BasEvdTaskInfo实体 业务处理层
 */
@Service
public class BasEvdTaskInfoServiceImpl extends ServiceImpl<BasEvdTaskInfoMapper, BasEvdTaskInfo> implements BasEvdTaskInfoService {
    @Override
    public Integer taskCount(String qualCode, String dataYear) {
        return this.baseMapper.taskCount(qualCode,dataYear);
    }

    @Override
    public List<BasEvdTaskInfoStatusCountDto> taskCountByStatus(String qualCode, String dataYear) {
        return this.baseMapper.taskCountByStatus(qualCode,dataYear);
    }
}
