package com.deloitte.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.ThkSecIssInfo;
import com.deloitte.crm.service.ThkSecIssInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 证券发行-股票发行-聆讯信息一览(ThkSecIssInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:34
 */
@RestController
@RequestMapping("thkSecIssInfo")
public class ThkSecIssInfoController {
    /**
     * 服务对象
     */
    @Resource
    private ThkSecIssInfoService thkSecIssInfoService;


}
