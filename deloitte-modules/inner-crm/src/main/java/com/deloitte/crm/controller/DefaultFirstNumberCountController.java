package com.deloitte.crm.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.DefaultFirstNumberCount;
import com.deloitte.crm.service.DefaultFirstNumberCountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (DefaultFirstNumberCount)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-09 10:50:32
 */
@RestController
@RequestMapping("defaultFirstNumberCount")
public class DefaultFirstNumberCountController {
    /**
     * 服务对象
     */
    @Resource
    private DefaultFirstNumberCountService defaultFirstNumberCountService;

    
}
