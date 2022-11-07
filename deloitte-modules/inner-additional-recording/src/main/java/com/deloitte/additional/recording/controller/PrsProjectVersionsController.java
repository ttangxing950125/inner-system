package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.PrsProjectVersionsService;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (PrsProjectVersions)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("prsProjectVersions")
public class PrsProjectVersionsController {
    /**
     * 服务对象
     */
    @Resource
    private PrsProjectVersionsService prsProjectVersionsService;

    /**
     * 查询版本配置列表
     *
     * @param year   年份
     * @param status 状态
     * @param param  搜索内容
     * @param pageNum  页码
     * @param pageSize  页面size
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 16:32
     */
    @PostMapping("/getPrsProjectVersions")
    public R getPrsProjectVersions(String year, String status, String param,Integer pageNum,Integer pageSize) {

        return R.ok(prsProjectVersionsService.getPrsProjectVersions(year, status, param,pageNum,pageSize));
    }
}
