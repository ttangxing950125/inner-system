package com.deloitte.additional.recording.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.BasEvdData;
import com.deloitte.additional.recording.service.BasEvdDataService;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (BasEvdData)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:36
 */
@RestController
@RequestMapping("basEvdData")
public class BasEvdDataController {
    /**
     * 服务对象
     */
    @Resource
    private BasEvdDataService basEvdDataService;

}
