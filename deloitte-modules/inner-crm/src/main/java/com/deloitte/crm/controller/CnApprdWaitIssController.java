package com.deloitte.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.CnApprdWaitIss;
import com.deloitte.crm.service.CnApprdWaitIssService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

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
