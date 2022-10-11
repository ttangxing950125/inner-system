package com.deloitte.crm.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.CnDelistInfo;
import com.deloitte.crm.service.CnDelistInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (CnDelistInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-11 18:46:11
 */
@RestController
@RequestMapping("cnDelistInfo")
public class CnDelistInfoController {
    /**
     * 服务对象
     */
    @Resource
    private CnDelistInfoService cnDelistInfoService;

    
}
