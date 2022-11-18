package com.deloitte.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.EntityStockCnRel;
import com.deloitte.crm.service.EntityStockCnRelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (EntityStockCnRel)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:36
 */
@RestController
@RequestMapping("entityStockCnRel")
public class EntityStockCnRelController {
    /**
     * 服务对象
     */
    @Resource
    private EntityStockCnRelService entityStockCnRelService;


}
