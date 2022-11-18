package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.PrsVersionMasterService;
import com.deloitte.additional.recording.vo.VersionMasterEvdVo;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (PrsVersionMaster)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@RestController
@RequestMapping("prsVersionMaster")
public class PrsVersionMasterController {
    /**
     * 服务对象
     */
    @Resource
    private PrsVersionMasterService prsVersionMasterService;

    /**
     * 根据版本和敞口查询对应指标
     *
     * @param versionMasterEvdVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 16:08
     */
    @PostMapping("/getVersionEvdByMasters")
    public R getVersionEvdByMasters(@RequestBody VersionMasterEvdVo versionMasterEvdVo) {
        return prsVersionMasterService.getVersionEvdByMasters(versionMasterEvdVo);
    }
}
