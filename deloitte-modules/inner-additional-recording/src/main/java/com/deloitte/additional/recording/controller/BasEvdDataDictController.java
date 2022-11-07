package com.deloitte.additional.recording.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.BasEvdDataDict;
import com.deloitte.additional.recording.service.BasEvdDataDictService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (BasEvdDataDict)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:38
 */
@RestController
@RequestMapping("basEvdDataDict")
public class BasEvdDataDictController {
    /**
     * 服务对象
     */
    @Resource
    private BasEvdDataDictService basEvdDataDictService;

    
}
