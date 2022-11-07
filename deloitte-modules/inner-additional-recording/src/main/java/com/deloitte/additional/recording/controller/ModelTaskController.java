package com.deloitte.additional.recording.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.ModelTask;
import com.deloitte.additional.recording.service.ModelTaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 任务表，补录平台的耗时操作全部扔到任务里面来执行(ModelTask)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("modelTask")
public class ModelTaskController {
    /**
     * 服务对象
     */
    @Resource
    private ModelTaskService modelTaskService;

    
}
