package com.deloitte.additional.recording.service.impl;

import com.deloitte.additional.recording.domain.BasRecordingLableEntity;
import com.deloitte.additional.recording.mapper.BasRecordingLableEntityMapper;
import com.deloitte.additional.recording.service.BasRecordingLableEntityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 中间补录层 标签和主体对应关系表 服务实现类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
@Service
public class BasRecordingLableEntityServiceImpl extends ServiceImpl<BasRecordingLableEntityMapper, BasRecordingLableEntity> implements BasRecordingLableEntityService {

}
