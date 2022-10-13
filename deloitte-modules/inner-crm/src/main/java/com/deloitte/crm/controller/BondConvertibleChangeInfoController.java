package com.deloitte.crm.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.BondConvertibleChangeInfo;
import com.deloitte.crm.service.BondConvertibleChangeInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 可交换转债发行预案(BondConvertibleChangeInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-13 13:39:43
 */
@RestController
@RequestMapping("bondConvertibleChangeInfo")
public class BondConvertibleChangeInfoController {
    /**
     * 服务对象
     */
    @Resource
    private BondConvertibleChangeInfoService bondConvertibleChangeInfoService;

    
}
