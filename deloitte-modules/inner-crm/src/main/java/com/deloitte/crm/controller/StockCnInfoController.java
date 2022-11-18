package com.deloitte.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.StockCnInfo;
import com.deloitte.crm.service.StockCnInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * a股信息表，大陆股票(StockCnInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:35
 */
@RestController
@RequestMapping("stockCnInfo")
public class StockCnInfoController {
    /**
     * 服务对象
     */
    @Resource
    private StockCnInfoService stockCnInfoService;


}
