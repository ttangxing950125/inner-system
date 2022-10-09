package com.deloitte.crm.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.DefaultMoneyTotal;
import com.deloitte.crm.service.DefaultMoneyTotalService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (DefaultMoneyTotal)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-09 10:50:32
 */
@RestController
@RequestMapping("defaultMoneyTotal")
public class DefaultMoneyTotalController {
    /**
     * 服务对象
     */
    @Resource
    private DefaultMoneyTotalService defaultMoneyTotalService;

    
}
