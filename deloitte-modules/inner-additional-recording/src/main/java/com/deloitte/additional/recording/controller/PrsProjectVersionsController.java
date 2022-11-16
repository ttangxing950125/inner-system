package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.domain.PrsProjectVersions;
import com.deloitte.additional.recording.service.PrsProjectVersionsService;
import com.deloitte.common.core.domain.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PrsProjectVersions)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("/prsProjectVersions")
@Api(tags = "版本-控制器")
public class PrsProjectVersionsController {
    /**
     * 服务对象
     */
    @Resource
    private PrsProjectVersionsService prsProjectVersionsService;

    /**
     * 查询版本配置列表
     *
     * @param year     年份
     * @param status   状态
     * @param name     版本名称
     * @param pageNum  页码
     * @param pageSize 页面size
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 16:32
     */
    @PostMapping("/getPrsProjectVersions")
    public R getPrsProjectVersions(String year, String status, String name, Integer pageNum, Integer pageSize) {
        return R.ok(prsProjectVersionsService.getPrsProjectVersions(year, status, name, pageNum, pageSize));
    }

    /**
     * 一键禁用
     *
     * @param ids 版本id列表
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:51
     */
    @PostMapping("/updateStatusToDownByIds")
    public R updateStatusToDownByIds(@RequestBody List<Integer> ids) {
        return R.ok(prsProjectVersionsService.updateStatusToDownByIds(ids));
    }

    /**
     * 一键启用
     *
     * @param ids 版本id列表
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 17:51
     */
    @PostMapping("/updateStatusToUpByIds")
    public R updateStatusToUpByIds(@RequestBody List<Integer> ids) {
        return R.ok(prsProjectVersionsService.updateStatusToUpByIds(ids));
    }

    /**
     * 新增版本
     *
     * @param prsProjectVersions
     * @return R
     * @author 冉浩岑
     * @date 2022/11/7 18:00
     */
    @PostMapping("/insertPrsProjectVersions")
    public R insertPrsProjectVersions(@RequestBody PrsProjectVersions prsProjectVersions) {
        return prsProjectVersionsService.insertPrsProjectVersions(prsProjectVersions);
    }


}
