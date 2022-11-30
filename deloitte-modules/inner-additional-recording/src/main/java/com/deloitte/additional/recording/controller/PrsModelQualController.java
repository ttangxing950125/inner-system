package com.deloitte.additional.recording.controller;


import com.deloitte.additional.recording.service.PrsModelQualService;
import com.deloitte.additional.recording.vo.VersionMasterEvdVo;
import com.deloitte.additional.recording.vo.qual.PrsQualDataSelectVO;
import com.deloitte.common.core.domain.MetaR;
import com.deloitte.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PrsModelQual)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@RestController
@RequestMapping("prsModelQual")
@Api(tags = "指标-控制层")
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
    public R getAllQualOfPage(@RequestBody VersionMasterEvdVo versionMasterEvdVo) {
        return prsModelQualService.getAllQualOfPage(versionMasterEvdVo);
    }

    @ApiOperation("获取选定敞口下的指标下拉选择框")
    @GetMapping("selectListbyVm")
    public MetaR<List<PrsQualDataSelectVO>> selectList(@ApiParam("版本id") @RequestParam("versionId") Integer versionId,
                                                       @ApiParam("敞口code") @RequestParam("modelCode") String modelCode) {
        return MetaR.ok(prsModelQualService.selectByMasterAndVersion(versionId, modelCode));
    }
}
