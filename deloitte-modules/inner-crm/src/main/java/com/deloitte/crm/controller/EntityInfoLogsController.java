package com.deloitte.crm.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.EntityInfoLogs;
import com.deloitte.crm.service.EntityInfoLogsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (EntityInfoLogs)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-10 13:55:12
 */
@RestController
@RequestMapping("entityInfoLogs")
public class EntityInfoLogsController {
    /**
     * 服务对象
     */
    @Resource
    private EntityInfoLogsService entityInfoLogsService;

    
}
