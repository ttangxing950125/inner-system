package com.deloitte.crm.controller;


import com.deloitte.crm.service.CnApprdWaitIssService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * IPO-审核通过尚未发行(CnApprdWaitIss)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:46
 */
@RestController
@RequestMapping("cnApprdWaitIss")
public class CnApprdWaitIssController {
    /**
     * 服务对象
     */
    @Resource
    private CnApprdWaitIssService cnApprdWaitIssService;


}
