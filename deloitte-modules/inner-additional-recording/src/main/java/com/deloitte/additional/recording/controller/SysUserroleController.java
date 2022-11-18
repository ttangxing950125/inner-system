package com.deloitte.additional.recording.controller;



import com.deloitte.additional.recording.service.SysUserroleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysUserrole)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:24
 */
@RestController
@RequestMapping("sysUserrole")
public class SysUserroleController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserroleService sysUserroleService;

    
}
