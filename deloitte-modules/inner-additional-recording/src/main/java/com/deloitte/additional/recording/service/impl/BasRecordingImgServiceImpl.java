package com.deloitte.additional.recording.service.impl;

import com.deloitte.additional.recording.domain.BasRecordingImg;
import com.deloitte.additional.recording.mapper.BasRecordingImgMapper;
import com.deloitte.additional.recording.service.BasRecordingImgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-28
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class BasRecordingImgServiceImpl extends ServiceImpl<BasRecordingImgMapper, BasRecordingImg> implements BasRecordingImgService {

}
