package com.deloitte.additional.recording.controller;



import com.deloitte.additional.recording.service.EntityInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (EntityInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("entityInfo")
public class EntityInfoController {
    /**
     * 服务对象
     */
    @Resource
    private EntityInfoService entityInfoService;

}
