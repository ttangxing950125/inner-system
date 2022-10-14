package com.deloitte.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.EntityInfoLogs;
import com.deloitte.crm.service.EntityInfoLogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

/**
 * (EntityInfoLogs)表控制层
 * 上市企业-新增主体
 *
 * @author lanyxp
 * @since 2022-10-10 13:55:12
 */
@RestController
@RequestMapping("/entityInfoLogs")
@Api(tags = "企业主体-历史记录")
public class EntityInfoLogsController extends BaseController {

    @Resource
    private EntityInfoLogsService entityInfoLogsService;

    @ApiOperation(value = "查询 企业主体股票-历史记录")
//    @RequiresPermissions("crm:entityInfoLogs:list")
    @RequestMapping("/list/{type}")
    public R<Object> list(@PathVariable("type") String type, HttpServletRequest request, HttpServletResponse response) {
        return (R<Object>) entityInfoLogsService.findAllByType(type);
    }

    @ApiOperation(value = "撤销停用")
//    @RequiresPermissions("crm:entityInfoLogs:cancel")
    @RequestMapping("/cancel/{id}")
    public R<Object>  cancel(@PathVariable("id") Integer id){
        return (R<Object>) entityInfoLogsService.cancel(id);
    }



}
