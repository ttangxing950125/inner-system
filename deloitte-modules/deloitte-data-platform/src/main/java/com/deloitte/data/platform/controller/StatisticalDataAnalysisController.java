package com.deloitte.data.platform.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.dto.StatisticalDataAnalysisDto;
import com.deloitte.data.platform.service.*;
import com.deloitte.data.platform.vo.StatisticalDataAnalysisVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据统计分析
 *
 * @author zhengyingxiang
 * @date 2022/11/11 13:48:35
 */
@Api(tags = "数据统计分析2")
@RestController
@RequestMapping("/statisticalDataAnalysis")
public class StatisticalDataAnalysisController {

    @Resource
    private IBaseFinDataService iBaseFinDataService;

    @Resource
    private IEntityInfoService entityInfoService;

    @Resource
    private IDataPlatformConfigDictService iDataPlatformConfigDictService;

    @ApiOperation("总览/业务场景/单个主体/单个字段")
    @GetMapping("/overview/list")
    public R<IPage<StatisticalDataAnalysisVo.OverviewVo>> overview(StatisticalDataAnalysisDto.OverviewDto dto) {
        return R.ok(iBaseFinDataService.overview(dto));
    }

    @ApiOperation("数据来源覆盖度")
    @GetMapping("/overview/sourceCoverage")
    public R<StatisticalDataAnalysisVo.CoverageVo> overviewSourceCoverage(StatisticalDataAnalysisDto.OverviewDto dto) {
        return R.ok(iBaseFinDataService.overviewSourceCoverage(dto));
    }

    @ApiOperation("使用场景列表")
    @GetMapping("/overview/getDataMenu")
    public R<List<StatisticalDataAnalysisVo.GetDataMenuVo>> getDataMenu(StatisticalDataAnalysisDto.GetDataMenuDto dto) {
        return R.ok(iBaseFinDataService.getDataMenu(dto));
    }

    @ApiOperation("查询有数据的最近三条年份")
    @GetMapping("/overview/years")
    public R<List<String>> years(StatisticalDataAnalysisDto.GetDataMenuDto dto) {
        return R.ok(iBaseFinDataService.years(dto));
    }

    @ApiOperation("客户列表")
    @GetMapping("/businessScenario/customerPage")
    public R<IPage<StatisticalDataAnalysisVo.CustomerListVo>> customerPage(StatisticalDataAnalysisDto.CustomerListDto dto) {
        return R.ok(entityInfoService.customerPage(dto));
    }

    @ApiOperation("缺失率统计")
    @GetMapping("/businessScenario/missRate")
    public R<StatisticalDataAnalysisVo.BusinessScenarioMissRateVo> businessScenarioMissRate(StatisticalDataAnalysisDto.BusinessScenarioDto dto) {
        return R.ok(iBaseFinDataService.businessScenarioMissRate(dto));
    }

    @ApiOperation("数据缺失率总占比统计")
    @GetMapping("/businessScenario/missRateAll")
    public R<StatisticalDataAnalysisVo.BusinessScenarioMissRateAllVo> missRateAll(StatisticalDataAnalysisDto.BusinessScenarioDto dto) {
        return R.ok(iBaseFinDataService.missRateAll(dto));
    }

    @ApiOperation("补录总占比统计")
    @GetMapping("/businessScenario/recordingAll")
    public R<StatisticalDataAnalysisVo.RecordingAllVo> recordingAll(StatisticalDataAnalysisDto.BusinessScenarioDto dto) {
        return R.ok(iBaseFinDataService.recordingAll(dto));
    }

    @ApiOperation("人工补录字段情况-条形图表")
    @GetMapping("/businessScenario/artificialRecordingBar")
    public R<StatisticalDataAnalysisVo.ArtificialRecordingBarVo> artificialRecordingBar(StatisticalDataAnalysisDto.BusinessScenarioDto dto) {
        return R.ok(iBaseFinDataService.artificialRecordingBar(dto));
    }

    @ApiOperation("人工补录字段情况-圆形图表")
    @GetMapping("/businessScenario/artificialRecordingCircular")
    public R<StatisticalDataAnalysisVo.ArtificialRecordingCircularVo> artificialRecordingCircular(StatisticalDataAnalysisDto.BusinessScenarioDto dto) {
        return R.ok(iBaseFinDataService.artificialRecordingCircular(dto));
    }

}
