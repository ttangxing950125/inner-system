package com.deloitte.crm.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.ImportTaskErrorLog;
import com.deloitte.crm.service.ImportTaskErrorLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (ImportTaskErrorLog)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 18:33:44
 */
@RestController
@RequestMapping("importTaskErrorLog")
public class ImportTaskErrorLogController {
    /**
     * 服务对象
     */
    @Resource
    private ImportTaskErrorLogService importTaskErrorLogService;

    
}
