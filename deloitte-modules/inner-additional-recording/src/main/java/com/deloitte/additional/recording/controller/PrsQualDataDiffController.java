package com.deloitte.additional.recording.controller;

import com.deloitte.additional.recording.service.PrsQualDataDiffService;
import com.deloitte.additional.recording.vo.BasEvdTaskDataVO;
import com.deloitte.additional.recording.vo.qual.PrsQualDataDiffPageVO;
import com.deloitte.additional.recording.vo.qual.PrsQualDataPanelVO;
import com.deloitte.common.core.domain.MetaR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @创建人 tangx
 * @创建时间 2022/11/28
 * @描述
 */
@RestController
@RequestMapping("prsQualDataDiff")
@Api(tags = "指标差异控制层")
public class PrsQualDataDiffController {

    @Autowired
    private PrsQualDataDiffService prsQualDataDiffService;

    @GetMapping("listByQual")
    @ApiOperation("查询指标下的主体指标差异列表")
    public MetaR<List<PrsQualDataDiffPageVO>> listByQual(@ApiParam("指标code") @RequestParam("qualCode") String qualCode) {

        return MetaR.ok(prsQualDataDiffService.findByQual(qualCode));
    }


    @ApiOperation("分布面板")
    @GetMapping("distributionPanel")
    public MetaR<Map<String, PrsQualDataPanelVO>> distributionPanel(@ApiParam("指标code") @RequestParam("qualCode") String qualCode,
                                                                    @ApiParam("年份") @RequestParam("dataYear") String dataYear) {

        Map<String, PrsQualDataPanelVO> map = prsQualDataDiffService.distributionPanel(qualCode, dataYear);
        return MetaR.ok(map);
    }


    @ApiOperation("任务进度面板统计")
    @GetMapping("taskPanel")
    public MetaR<Map<String, BigDecimal>> progressPanel(@ApiParam("指标code") @RequestParam("qualCode") String qualCode,
                                                        @ApiParam("年份") @RequestParam("dataYear") String dataYear) {
        return MetaR.ok(prsQualDataDiffService.progressPanel(qualCode, dataYear));
    }

    @ApiOperation("基本面数据列表")
    @GetMapping("basePanel")
    public MetaR<List<BasEvdTaskDataVO>> basePanel(@ApiParam("指标code") @RequestParam("qualCode") String qualCode) {
        return MetaR.ok(prsQualDataDiffService.basePanel(qualCode));
    }
}
