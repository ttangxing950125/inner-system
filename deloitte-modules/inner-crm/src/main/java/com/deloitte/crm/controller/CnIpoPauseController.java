package com.deloitte.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.CnIpoPause;
import com.deloitte.crm.service.CnIpoPauseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * IPO-发行暂缓(CnIpoPause)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:54
 */
@RestController
@RequestMapping("cnIpoPause")
public class CnIpoPauseController {
    /**
     * 服务对象
     */
    @Resource
    private CnIpoPauseService cnIpoPauseService;


}
