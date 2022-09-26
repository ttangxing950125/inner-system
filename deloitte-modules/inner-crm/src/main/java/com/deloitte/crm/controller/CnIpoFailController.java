package com.deloitte.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.CnIpoFail;
import com.deloitte.crm.service.CnIpoFailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * IPO-发行失败(CnIpoFail)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:49
 */
@RestController
@RequestMapping("cnIpoFail")
public class CnIpoFailController {
    /**
     * 服务对象
     */
    @Resource
    private CnIpoFailService cnIpoFailService;


}
