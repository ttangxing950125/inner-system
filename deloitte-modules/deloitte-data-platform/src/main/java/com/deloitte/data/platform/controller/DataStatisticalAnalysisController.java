package com.deloitte.data.platform.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.dto.*;
import com.deloitte.data.platform.service.*;
import com.deloitte.data.platform.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 数据统计分析
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Api(tags = "数据统计分析")
@RestController
@RequestMapping("/dataStatistical")
public class DataStatisticalAnalysisController {

    @Resource
    private IBaseFinDataService iBaseFinDataService;

    @Resource
    private IDataCheckResultService iDataCheckResultService;

    @Resource
    private IDataCheckResultItemService iDataCheckResultItemService;

    @Resource
    private IEvidenceModelService iEvidenceModelService;

    @Resource
    private IModelRationFactorService iModelRationFactorService;

    @ApiOperation("基础层质检结果")
    @GetMapping("/baseQuality/page")
    public R<IPage<BaseQualityVo>> getBaseQualityPage(BaseQualityDto dto) {
        return R.ok(iBaseFinDataService.getBaseQualityPage(dto));
    }

    @ApiOperation("勾稽关系")
    @GetMapping("/hookArticulation/page")
    public R<IPage<HookArticulationVo>> getHookArticulationPage(HookArticulationDto dto) {
        return R.ok(iDataCheckResultService.getHookArticulationPage(dto));
    }

    @ApiOperation("勾稽关系详情")
    @GetMapping("/hookArticulationDetail/page")
    public R<HookArticulationDetailVo> getHookArticulationDetailPage(HookArticulationDetailDto dto) {
        return R.ok(iDataCheckResultItemService.getHookArticulationDetailPage(dto));
    }

    @ApiOperation("中间层质检结果")
    @GetMapping("/middleQuality/page")
    public R<IPage<MiddleQualityVo>> getMiddleQualityPage(MiddleQualityDto dto) {
        return R.ok(iEvidenceModelService.getMiddleQualityPage(dto));
    }

    @ApiOperation("指标层质检结果")
    @GetMapping("/applyQuality/page")
    public R<IPage<ApplyQualityVo>> getApplyQualityPage(ApplyQualityDto dto) {
        return R.ok(iModelRationFactorService.getApplyQualityPage(dto));
    }
}
