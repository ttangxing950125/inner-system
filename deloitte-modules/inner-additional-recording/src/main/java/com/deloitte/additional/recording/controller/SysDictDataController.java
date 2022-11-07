package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.impl.SysDictDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (BasEvdDataDict)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:38
 */
@RestController
@RequestMapping("/sysDictData")
public class SysDictDataController {
    /**
     * 服务对象
     */
    @Resource
    private SysDictDataService sysDictDataService;

    
}
