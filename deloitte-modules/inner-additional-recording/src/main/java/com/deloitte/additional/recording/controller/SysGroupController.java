package com.deloitte.additional.recording.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.SysGroup;
import com.deloitte.additional.recording.service.SysGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (SysGroup)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:30
 */
@RestController
@RequestMapping("sysGroup")
public class SysGroupController {
    /**
     * 服务对象
     */
    @Resource
    private SysGroupService sysGroupService;

    
}
