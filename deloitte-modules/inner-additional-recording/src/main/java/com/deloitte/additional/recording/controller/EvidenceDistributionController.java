package com.deloitte.additional.recording.controller;

import com.alibaba.excel.EasyExcel;
import com.deloitte.additional.recording.dto.EvidenceBatchDto;
import com.deloitte.additional.recording.dto.EvidenceDistributionPageDto;
import com.deloitte.additional.recording.dto.KpIInfoByQuryDto;
import com.deloitte.additional.recording.service.BasEvdInfoService;
import com.deloitte.additional.recording.service.BasEvdTaskInfoService;
import com.deloitte.additional.recording.vo.ExcelBatchCallBackVo;
import com.deloitte.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/evidence")
@Api("evidenct列表")
public class EvidenceDistributionController {

    @Resource
    private  BasEvdInfoService basEvdInfoService;
    @Resource
    private BasEvdTaskInfoService basEvdTaskInfoService;

    @GetMapping("/getEvidenceDistributionList")
    @ApiOperation(value = "查询evidenct列表")
    public R getEvidenceDistributionList(@RequestBody EvidenceDistributionPageDto distributionPageDto){
     return    basEvdInfoService.getEvidenceDistributionList(distributionPageDto);
    }

    @PutMapping("/updatEevidenceBatch")
    @ApiOperation(value = "evidence批量分配")
    public R updateevidenceBatch(@RequestBody EvidenceBatchDto evidenceBatchDto){
        return basEvdTaskInfoService.updatEevidenceBatch(evidenceBatchDto);
    }

    @PutMapping("/updatEevidenceThrough")
    @ApiOperation(value = "evidence批量通过")
    public R updatEevidenceThrough(@RequestBody EvidenceBatchDto evidenceBatchDto){
        return basEvdTaskInfoService.updatEevidenceThrough(evidenceBatchDto);
    }

    @ApiOperation(value = "excel上传批量打回")
    @PostMapping("/excel")
    public R excelBatchCallBack(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        basEvdTaskInfoService.excelBatchCallBack(file,response);
        return R.ok(null);
    }

    @PostMapping("/WorkResult")
    public R getWorkResult(KpIInfoByQuryDto evidenceBatchDto){
        return basEvdTaskInfoService.getKpiInfoView(evidenceBatchDto);
    }








}
