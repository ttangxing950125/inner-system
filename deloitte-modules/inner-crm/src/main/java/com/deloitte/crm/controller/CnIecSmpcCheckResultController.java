package com.deloitte.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.CnIecSmpcCheckResult;
import com.deloitte.crm.service.CnIecSmpcCheckResultService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * IPO-发审委上市委审核结果(CnIecSmpcCheckResult)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:48
 */
@RestController
@RequestMapping("cnIecSmpcCheckResult")
public class CnIecSmpcCheckResultController {
    /**
     * 服务对象
     */
    @Resource
    private CnIecSmpcCheckResultService cnIecSmpcCheckResultService;


}
