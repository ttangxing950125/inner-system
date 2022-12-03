package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.BasEvdValrangeService;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (BasEvdValrange)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("basEvdValrange")
public class BasEvdValrangeController {
    /**
     * 服务对象
     */
    @Resource
    private BasEvdValrangeService basEvdValrangeService;
    /**
     * evdCode赋值
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/16 9:22
    */
    @RequestMapping("/setBasEvdValrange")
    public R setBasEvdValrange(){
        return basEvdValrangeService.setBasEvdValrange();
    }
}
