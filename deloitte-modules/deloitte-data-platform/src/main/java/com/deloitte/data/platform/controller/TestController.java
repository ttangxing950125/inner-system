package com.deloitte.data.platform.controller;


import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.service.IModelRationFactorService;
import com.deloitte.data.platform.service.IBaseFinDataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 德勤基础数据表  前端控制器
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private IBaseFinDataService iBaseFinDataService;

    @Resource
    private IModelRationFactorService iModelRationFactorService;


    @GetMapping("/test1/{id}")
    public R<Object> test1(@PathVariable Integer id) {
        return R.ok(iBaseFinDataService.getById(id));
    }

    @GetMapping("/test2/{id}")
    public R<Object> test2(@PathVariable Integer id) {
        return R.ok(iModelRationFactorService.getById(id));
    }

}
