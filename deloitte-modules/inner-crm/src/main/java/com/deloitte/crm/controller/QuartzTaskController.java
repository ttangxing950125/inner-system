package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.quartz.QuartzTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/01/10:11
 * @Description:
 */
@Slf4j
@RequestMapping("/quartz")
@RestController
public class QuartzTaskController {
    @Resource
    private QuartzTask quartzTask;

    @GetMapping("/manual")
    public R manualQuartzTask(HttpServletResponse response, HttpServletRequest request) {
        log.info(">>>>>自动生成定时任务开始..................");
        quartzTask.StartRuleTask();
        log.info(">>>>>自动生成定时任务结束..................");
        return R.ok();
    }

}
