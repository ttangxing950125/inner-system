package com.deloitte.crm.controller;



import com.deloitte.crm.service.ImplementStInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 实施ST(带帽)(ImplementStInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-14 17:50:05
 */
@RestController
@RequestMapping("implementStInfo")
public class ImplementStInfoController {
    /**
     * 服务对象
     */
    @Resource
    private ImplementStInfoService implementStInfoService;

    
}
