package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.ModelTaskMapper;
import com.deloitte.additional.recording.domain.ModelTask;
import com.deloitte.additional.recording.service.ModelTaskService;
import org.springframework.stereotype.Service;

/**
 * 任务表，补录平台的耗时操作全部扔到任务里面来执行(ModelTask)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Service("modelTaskService")
public class ModelTaskServiceImpl extends ServiceImpl<ModelTaskMapper, ModelTask> implements ModelTaskService {

}
