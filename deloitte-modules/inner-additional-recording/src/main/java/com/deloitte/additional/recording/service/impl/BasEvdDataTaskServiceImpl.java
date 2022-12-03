package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.BasEvdDataTask;
import com.deloitte.additional.recording.mapper.BasEvdDataTaskMapper;
import com.deloitte.additional.recording.service.BasEvdDataService;
import com.deloitte.additional.recording.service.BasEvdDataTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @创建人 tangx
 * @创建时间 2022/12/2
 * @描述 BasEvdDataTask实体业务层
 */
@Service
public class BasEvdDataTaskServiceImpl extends ServiceImpl<BasEvdDataTaskMapper, BasEvdDataTask> implements BasEvdDataTaskService {


    @Autowired
    public BasEvdDataService basEvdDataService;
}
