package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.SysDictDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 版本控制层
 *
 * @author 冉浩岑
 * @date 2022/11/7 17:16
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
