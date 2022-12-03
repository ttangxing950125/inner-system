package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsQualDataTask;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/30
 * @描述
 */
public interface PrsQualDataTaskService extends IService<PrsQualDataTask> {


    /**
     * 查询指标下的基本数据面列表
     * @param qualCode 指标code
     * @return
     */
    List<PrsQualDataTask> findByQualCode(String qualCode);

    PrsQualDataTask calculationTask(String qualCode,String dataYear);
}
