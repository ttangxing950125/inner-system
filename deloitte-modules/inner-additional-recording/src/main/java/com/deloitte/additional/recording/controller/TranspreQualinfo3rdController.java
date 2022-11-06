package com.deloitte.additional.recording.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.TranspreQualinfo3rd;
import com.deloitte.additional.recording.service.TranspreQualinfo3rdService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (TranspreQualinfo3rd)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@RestController
@RequestMapping("transpreQualinfo3rd")
public class TranspreQualinfo3rdController {
    /**
     * 服务对象
     */
    @Resource
    private TranspreQualinfo3rdService transpreQualinfo3rdService;

    
}
