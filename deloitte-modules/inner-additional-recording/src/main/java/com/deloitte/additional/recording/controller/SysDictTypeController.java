package com.deloitte.additional.recording.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.SysDictType;
import com.deloitte.additional.recording.service.SysDictTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (SysDictType)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:35
 */
@RestController
@RequestMapping("sysDictType")
public class SysDictTypeController {
    /**
     * 服务对象
     */
    @Resource
    private SysDictTypeService sysDictTypeService;

    
}
