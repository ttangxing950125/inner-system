package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.BasRecordingTaskInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.dto.RecordingTaskInfoGetInfoReqDto;
import com.deloitte.additional.recording.dto.RecordingTaskSubmitDto;
import com.deloitte.common.core.domain.R;

import java.util.List;

/**
 * <p>
 * 中间补录层任务表 服务类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
public interface BasRecordingTaskInfoService extends IService<BasRecordingTaskInfo> {
    /**
     * 获取补录任务列表
     * @param requestParam
     * @return
     */
    Page<BasRecordingTaskInfo> getTaskInfoByParamPage(RecordingTaskInfoGetInfoReqDto requestParam);

    Object chooseDistribution();

    R taskSubmit(List<RecordingTaskSubmitDto> submitDto);
}
