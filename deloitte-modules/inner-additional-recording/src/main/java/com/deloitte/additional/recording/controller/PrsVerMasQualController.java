package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.PrsVerMasQualService;
import com.deloitte.additional.recording.vo.MasterEvdVo;
import com.deloitte.additional.recording.vo.VersionMasterEvdBackVo;
import com.deloitte.common.core.domain.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PrsVerMasQual)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@RestController
@RequestMapping("prsVerMasQual")
public class PrsVerMasQualController {
    /**
     * 服务对象
     */
    @Resource
    private PrsVerMasQualService prsVerMasQualService;

    /**
     * 增加配置
     *
     * @param masterEvdVo
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 17:27
    */
    @PostMapping("/addMasterEvd")
    public R addMasterEvd(@RequestBody MasterEvdVo masterEvdVo){
        return prsVerMasQualService.addMasterEvd(masterEvdVo);
    }
    /**
     * 批量删除
     *
     * @param list
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 17:27
     */
    @PostMapping("/deleteMasterEvd")
    public R deleteMasterEvd(@RequestBody List<VersionMasterEvdBackVo> list){
        return prsVerMasQualService.deleteMasterEvd(list);
    }
}
