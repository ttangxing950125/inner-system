package com.deloitte.additional.recording.service.impl;

import com.deloitte.additional.recording.domain.BaseFinDataRecording;
import com.deloitte.additional.recording.mapper.BaseFinDataRecordingMapper;
import com.deloitte.additional.recording.service.BaseFinDataRecordingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 德勤基础数据表  服务实现类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-26
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class BaseFinDataRecordingServiceImpl extends ServiceImpl<BaseFinDataRecordingMapper, BaseFinDataRecording> implements BaseFinDataRecordingService {

}
