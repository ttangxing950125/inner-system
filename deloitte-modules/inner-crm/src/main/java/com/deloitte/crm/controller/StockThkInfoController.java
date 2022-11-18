package com.deloitte.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.StockThkInfo;
import com.deloitte.crm.service.StockThkInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 股票信息表(StockThkInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:19
 */
@RestController
@RequestMapping("stockThkInfo")
public class StockThkInfoController {
    /**
     * 服务对象
     */
    @Resource
    private StockThkInfoService stockThkInfoService;


}
