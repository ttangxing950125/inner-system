package com.deloitte.additional.recording.service;

import com.deloitte.additional.recording.domain.BasRecordingLabel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.dto.BasRecordingLabelReqDto;
import com.deloitte.common.core.domain.R;

/**
 * <p>
 * 中间补录层标签表 服务类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
public interface BasRecordingLabelService extends IService<BasRecordingLabel> {

    R getEcordingLabelList(BasRecordingLabelReqDto reqDto);

    R editEcordingLabel(Integer taskId);
}
