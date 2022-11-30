package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.PrsModelMasterService;
import com.deloitte.additional.recording.vo.master.PrsModelMasterSelectVO;
import com.deloitte.common.core.domain.MetaR;
import com.deloitte.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PrsModelMaster)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("prsModelMaster")
@Api(tags = "敞口控制层")
public class PrsModelMasterController {
    /**
     * 服务对象
     */
    @Resource
    private PrsModelMasterService prsModelMasterService;

    /**
     * 获取所有敞口基础数据
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/11/9 15:54
    */
    @PostMapping("/getAllMaster")
    public R getAllMaster(){
        return prsModelMasterService.getAllMaster();
    }

    @GetMapping("selectList")
    @ApiOperation("下拉选择列表")
    public MetaR<List<PrsModelMasterSelectVO>> selectList(@ApiParam("版本id")@RequestParam(value = "versionId",required = false) Integer versionId){

        return MetaR.ok( prsModelMasterService.selectList(versionId));
    }


}
