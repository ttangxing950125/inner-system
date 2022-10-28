package com.deloitte.crm.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.CrmTypeInfo;
import com.deloitte.crm.service.CrmTypeInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (CrmTypeInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-28 13:11:01
 */
@RestController
@RequestMapping("crmTypeInfo")
public class CrmTypeInfoController {
    /**
     * 服务对象
     */
    @Resource
    private CrmTypeInfoService crmTypeInfoService;

    
}
