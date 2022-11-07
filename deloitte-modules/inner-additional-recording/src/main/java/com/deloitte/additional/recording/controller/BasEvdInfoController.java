package com.deloitte.additional.recording.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.BasEvdInfo;
import com.deloitte.additional.recording.service.BasEvdInfoService;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (BasEvdInfo)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("basEvdInfo")
public class BasEvdInfoController {
    /**
     * 服务对象
     */
    @Resource
    private BasEvdInfoService basEvdInfoService;


    @GetMapping("/findAll")
    public R<List<BasEvdInfo>> findAll(){
        return R.ok(basEvdInfoService.list());
    }
}
