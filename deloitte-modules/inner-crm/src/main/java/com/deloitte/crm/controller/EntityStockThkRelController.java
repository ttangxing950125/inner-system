package com.deloitte.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.EntityStockThkRel;
import com.deloitte.crm.service.EntityStockThkRelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (EntityStockThkRel)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:18
 */
@RestController
@RequestMapping("entityStockThkRel")
public class EntityStockThkRelController {
    /**
     * 服务对象
     */
    @Resource
    private EntityStockThkRelService entityStockThkRelService;


}
