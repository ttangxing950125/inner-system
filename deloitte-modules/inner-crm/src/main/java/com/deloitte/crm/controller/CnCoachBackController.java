package com.deloitte.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.CnCoachBack;
import com.deloitte.crm.service.CnCoachBackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * IPO-辅导备案(CnCoachBack)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:47
 */
@RestController
@RequestMapping("cnCoachBack")
public class CnCoachBackController {
    /**
     * 服务对象
     */
    @Resource
    private CnCoachBackService cnCoachBackService;


}
