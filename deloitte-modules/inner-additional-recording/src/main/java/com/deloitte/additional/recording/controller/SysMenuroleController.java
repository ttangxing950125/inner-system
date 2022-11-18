package com.deloitte.additional.recording.controller;



import com.deloitte.additional.recording.service.SysMenuroleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysMenurole)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:25
 */
@RestController
@RequestMapping("sysMenurole")
public class SysMenuroleController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuroleService sysMenuroleService;

    
}
