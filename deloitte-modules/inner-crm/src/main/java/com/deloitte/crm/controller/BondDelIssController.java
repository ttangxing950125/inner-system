package com.deloitte.crm.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.BondDelIss;
import com.deloitte.crm.service.BondDelIssService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 新债发行-推迟或取消发行债券(BondDelIss)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 12:11:32
 */
@RestController
@RequestMapping("bondDelIss")
public class BondDelIssController {
    /**
     * 服务对象
     */
    @Resource
    private BondDelIssService bondDelIssService;

    
}
