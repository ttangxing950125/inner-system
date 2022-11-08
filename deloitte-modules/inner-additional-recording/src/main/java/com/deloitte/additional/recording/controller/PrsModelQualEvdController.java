package com.deloitte.additional.recording.controller;



import com.deloitte.additional.recording.service.PrsModelQualEvdService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (PrsModelQualEvd)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("prsModelQualEvd")
public class PrsModelQualEvdController {
    /**
     * 服务对象
     */
    @Resource
    private PrsModelQualEvdService prsModelQualEvdService;

    /**
     * 映射 prs_model_qual_factor，prs_model_qual_evd 外部链接 CODE
     *
     * @return void
     * @author 冉浩岑
     * @date 2022/11/8 14:47
    */
    @PostMapping("/set")
    public void setValue(){
        prsModelQualEvdService.setValue();
    }
    
}
