package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.PrsModelMasterService;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (PrsModelMaster)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("prsModelMaster")
public class PrsModelMasterController {
    /**
     * 服务对象
     */
    @Resource
    private PrsModelMasterService prsModelMasterService;

    /**
     * 获取所有敞口基础数据
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 15:54
    */
    @PostMapping("/getAllMaster")
    public R getAllMaster(){
        return prsModelMasterService.getAllMaster();
    }
}
