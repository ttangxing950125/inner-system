package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.dto.EntityInfoByFinDto;
import com.deloitte.additional.recording.service.FinancialTaskService;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 三表补录详情审核页面
 *
 */
@RestController
@RequestMapping("/financialTask")
public class FinancialTaskController {
    @Resource
    private FinancialTaskService financialTaskService;

    /**
     * 三表补录详情审核页面
     * @param taskId       任务id
     * @param entityCode   主体编码
     * @param reportDate   报告日期
     * @param dataTypeCode
     * @param response    响应体
     * @param request    请求体
     * @return
     */
    @RequestMapping("/getTaskDetail")
    public R getTaskDetail(Integer taskId, String entityCode, String reportDate, String dataTypeCode, HttpServletResponse response, HttpServletRequest request) {
        Optional.ofNullable(taskId).orElseThrow(() -> new ServiceException("任务ID不可以为空"));
        return financialTaskService.getTaskDetail(taskId, entityCode, reportDate, dataTypeCode);
    }

    /**
     *查询三表一注的列表
     *
     * @return R
     * @author penTang
     * @date 2022/11/29 13:46
     */
    @PostMapping("/getFinaTaskView")
    public R getFinTask(@RequestBody EntityInfoByFinDto entityInfoByFinDto){

        return financialTaskService.getFinalEntityView(entityInfoByFinDto);
    }

}
