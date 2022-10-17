package com.deloitte.crm.controller;



import com.deloitte.crm.service.UndoStInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 撤销ST(摘帽)(UndoStInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-14 17:50:05
 */
@RestController
@RequestMapping("undoStInfo")
public class UndoStInfoController {
    /**
     * 服务对象
     */
    @Resource
    private UndoStInfoService undoStInfoService;

    
}
