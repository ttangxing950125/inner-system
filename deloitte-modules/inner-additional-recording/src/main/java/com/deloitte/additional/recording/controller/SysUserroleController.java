package com.deloitte.additional.recording.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.SysUserrole;
import com.deloitte.additional.recording.service.SysUserroleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

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
