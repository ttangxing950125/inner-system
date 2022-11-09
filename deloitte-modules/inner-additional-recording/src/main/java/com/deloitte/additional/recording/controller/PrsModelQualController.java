package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.vo.VersionMasterEvdVo;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (PrsModelQual)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("prsModelQual")
public class PrsModelQualController {
    /**
     * 服务对象
     */
    @Resource
    private PrsModelQualService prsModelQualService;

    /**
     * 分页查询全部指标
     *
     * @param versionMasterEvdVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 16:56
    */
    @PostMapping("/getAllQualOfPage")
    public R getAllQualOfPage(@RequestBody VersionMasterEvdVo versionMasterEvdVo){
        return prsModelQualService.getAllQualOfPage(versionMasterEvdVo);
    }
}
