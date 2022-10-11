package com.deloitte.crm.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.EntityFinancial;
import com.deloitte.crm.service.EntityFinancialService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (EntityFinancial)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-11 17:11:57
 */
@RestController
@RequestMapping("entityFinancial")
public class EntityFinancialController {
    /**
     * 服务对象
     */
    @Resource
    private EntityFinancialService entityFinancialService;

    
}
