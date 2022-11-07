package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.ModelTask;

/**
 * 任务表，补录平台的耗时操作全部扔到任务里面来执行(ModelTask)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface ModelTaskService extends IService<ModelTask> {

}
