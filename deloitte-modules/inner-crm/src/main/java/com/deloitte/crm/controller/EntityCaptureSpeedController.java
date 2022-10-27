package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.service.EntityCaptureSpeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/27/14:31
 * @Description:
 */
@RestController
@RequestMapping("/entityCaptureSpeed")
public class EntityCaptureSpeedController {
    @Resource
    private EntityCaptureSpeedService entityCaptureSpeedService;

    /**
     * @param entityNameOrCode
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/search/{entityNameOrCode}")
    public R search(@PathVariable("entityNameOrCode") String entityNameOrCode, HttpServletRequest request, HttpServletResponse response) {
       return entityCaptureSpeedService.search(entityNameOrCode);
    }

}
