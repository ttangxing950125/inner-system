package com.deloitte.data.platform.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.data.platform.dto.*;
import com.deloitte.data.platform.service.IBaseFinDataService;
import com.deloitte.data.platform.service.IEvidenceDataService;
import com.deloitte.data.platform.service.IFinFactorValueService;
import com.deloitte.data.platform.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 数据提取
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Api(tags = "数据提取")
@RestController
@RequestMapping("/dataExtraction")
public class DataExtractionController {

    @Resource
    private IBaseFinDataService iBaseFinDataService;

    @Resource
    private IEvidenceDataService iEvidenceDataService;

    @Resource
    private IFinFactorValueService iFinFactorValueService;

    @ApiOperation("基础层数据结果")
    @GetMapping("/baseData/page")
    public R<IPage<BaseDataExtractionVo>> getBaseDataExtractionPage(BaseDataDto dto) {
        return R.ok(iBaseFinDataService.getBaseDataExtractionPage(dto));
    }

    @ApiOperation("基础层数据详情")
    @GetMapping("/baseDataDetail/page")
    public R<IPage<DataExtractionDetailVo>> getDataExtractionDetailPage(BaseDataDto dto) {
        return R.ok(iBaseFinDataService.getDataExtractionDetailPage(dto));
    }

    @ApiOperation("导出基础层数据结果")
    @PostMapping("/baseData/export")
    public R<Void> baseDataExport(HttpServletResponse response, @Validated BaseDataExportDto dto) {
        iBaseFinDataService.baseDataExport(response,dto);
        return R.ok();
    }

    @ApiOperation("导出基础层数据详情")
    @PostMapping("/baseDataDetail/export")
    public R<Void> baseDataDetailExport(HttpServletResponse response, @Validated BaseDataDetailExportDto dto) {
        iBaseFinDataService.baseDataDetailExport(response,dto);
        return R.ok();
    }

    @ApiOperation("中间层数据结果")
    @GetMapping("/middleData/page")
    public R<IPage<MiddleDataExtractionVo>> getMiddleDataExtractionPage(MiddleDataDto dto) {
        return R.ok(iEvidenceDataService.getMiddleDataExtractionPage(dto));
    }

    @ApiOperation("导出中间层数据结果")
    @PostMapping("/middleData/export")
    public R<Void> middleDataExport(HttpServletResponse response, @Validated MiddleDataExportDto dto) {
        iEvidenceDataService.middleDataExport(response,dto);
        return R.ok();
    }

    @ApiOperation("中间层数据详情")
    @GetMapping("/middleDataDetail/page")
    public R<IPage<MiddleDataExtractionDetailVo>> getMiddleDataExtractionDetailPage(MiddleDataDto dto) {
        return R.ok(iEvidenceDataService.getMiddleDataExtractionDetailPage(dto));
    }

    @ApiOperation("导出中间层数据详情")
    @PostMapping("/middleDataDetail/export")
    public R<Void> middleDataDetailExport(HttpServletResponse response, MiddleDataDetailExportDto dto) {
        iEvidenceDataService.middleDataDetailExport(response,dto);
        return R.ok();
    }

    @ApiOperation("指标层数据结果")
    @GetMapping("/applyData/page")
    public R<IPage<ApplyDataExtractionVo>> getApplyDataExtractionPage(ApplyDataDto dto) {
        return R.ok(iFinFactorValueService.getApplyDataExtractionPage(dto));
    }

    @ApiOperation("导出指标层数据结果")
    @PostMapping("/applyData/export")
    public R<Void> applyDataExport(HttpServletResponse response,@Validated ApplyDataExportDto dto) {
        iFinFactorValueService.applyDataExport(response,dto);
        return R.ok();
    }

    @ApiOperation("指标层数据详情")
    @GetMapping("/applyDataDetail/page")
    public R<IPage<ApplyDataExtractionDetailVo>> getApplyDataExtractionDetailPage(ApplyDataDto dto) {
        return R.ok(iFinFactorValueService.getApplyDataExtractionDetailPage(dto));
    }

    @ApiOperation("导出指标层数据详情")
    @PostMapping("/applyDataDetail/export")
    public R<Void> applyDataDetailExport(HttpServletResponse response, @Validated ApplyDataDetailExportDto dto) {
        iFinFactorValueService.applyDataDetailExport(response,dto);
        return R.ok();
    }
}
