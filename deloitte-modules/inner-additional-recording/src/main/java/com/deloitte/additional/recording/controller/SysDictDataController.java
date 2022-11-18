package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.SysDictDataService;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 查询可查询年份
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:28
    */
    @PostMapping("/getYear")
    public R getYear(){
        return sysDictDataService.getYear();
    }
    /**
     * 查询可查询数据源
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:28
     */
    @PostMapping("/getDataSource")
    public R getDataSource(){
        return sysDictDataService.getDataSource();
    }
    /**
     * 查询可显示类型
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:28
     */
    @PostMapping("/getShowType")
    public R getShowType(){
        return sysDictDataService.getShowType();
    }
}
