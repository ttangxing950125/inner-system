package com.deloitte.crm.controller;



import com.deloitte.crm.service.BondDelIssService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 新债发行-推迟或取消发行债券(BondDelIss)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 12:11:32
 */
@RestController
@RequestMapping("/bondDelIss")
public class BondDelIssController {
    /**
     * 服务对象
     */
    @Resource
    private BondDelIssService bondDelIssService;

}
