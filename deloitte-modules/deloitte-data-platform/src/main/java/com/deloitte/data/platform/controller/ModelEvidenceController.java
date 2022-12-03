package com.deloitte.data.platform.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.dto.ModelEvidenceDto;
import com.deloitte.data.platform.service.IModelEvidenceService;
import com.deloitte.data.platform.vo.ModelEvidenceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * 公式
 *
 * @author zhengyingxiang
 * @date 2022/11/20
 */
@Api(tags = "数据配置")
@RestController
@RequestMapping("/modelEvidence")
public class ModelEvidenceController {

    @Autowired
    private IModelEvidenceService modelEvidenceService;


    @ApiOperation("数据配置列表")
    @GetMapping("/list")
    public R<IPage<ModelEvidenceVo.ListVo>> getModelEvidenceListPage(ModelEvidenceDto.ListDto dto) {
        return R.ok(modelEvidenceService.getModelEvidenceListPage(dto));
    }

    @ApiOperation("财务数据配置列表")
    @GetMapping("/financialDataConfigList")
    public R<IPage<ModelEvidenceVo.FinancialDataConfigListVo>> getFinancialDataConfigList(ModelEvidenceDto.FinancialDataConfigListDto dto) {
        return R.ok(modelEvidenceService.getFinancialDataConfigList(dto));
    }

    @ApiOperation("新增/修改数据配置")
    @PostMapping("/addOrUpdate")
    public R add(@RequestBody ModelEvidenceDto.AddDto dto) {
        return R.ok(modelEvidenceService.add(dto));
    }

    @ApiOperation("删除数据配置")
    @DeleteMapping("/delete")
    public R deleteById(ModelEvidenceDto.DeleteDto dto) {
        return R.ok(modelEvidenceService.deleteById(dto));
    }

    @ApiOperation("获取配置详情")
    @GetMapping("/detail")
    public R<ModelEvidenceVo.ListVo> detail(ModelEvidenceDto.DeleteDto dto) {
        return R.ok(modelEvidenceService.detail(dto));
    }

    @ApiOperation("计算")
    @GetMapping("/calculation")
    public R<IPage<ModelEvidenceVo.CalculationVo>> calculation(ModelEvidenceDto.CalculationDto dto) throws ParseException {
        return R.ok(modelEvidenceService.calculation(dto));
    }

}
