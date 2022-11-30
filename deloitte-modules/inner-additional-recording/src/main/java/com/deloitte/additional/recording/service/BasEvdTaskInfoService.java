package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.BasEvdTaskInfo;
import com.deloitte.additional.recording.dto.BasEvdTaskInfoStatusCountDto;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/29
 * @描述
 */
public interface BasEvdTaskInfoService  extends IService<BasEvdTaskInfo> {
    /**
     * 统计任务总量
     * @param qualCode 指标code
     * @param dataYear 年份
     * @return Integer
     */
    Integer taskCount(String qualCode, String dataYear);

    List<BasEvdTaskInfoStatusCountDto> taskCountByStatus(String qualCode, String dataYear);
}
