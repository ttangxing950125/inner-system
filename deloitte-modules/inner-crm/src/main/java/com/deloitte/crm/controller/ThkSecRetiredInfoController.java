package com.deloitte.crm.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.ThkSecRetiredInfo;
import com.deloitte.crm.service.ThkSecRetiredInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 证券发行-港股-已退市证券一览(ThkSecRetiredInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-01 11:45:43
 */
@RestController
@RequestMapping("thkSecRetiredInfo")
public class ThkSecRetiredInfoController {
    /**
     * 服务对象
     */
    @Resource
    private ThkSecRetiredInfoService thkSecRetiredInfoService;

    
}
