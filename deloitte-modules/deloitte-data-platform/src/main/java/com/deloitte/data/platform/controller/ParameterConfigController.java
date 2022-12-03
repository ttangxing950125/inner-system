package com.deloitte.data.platform.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.service.IModelEvidenceService;
import com.deloitte.data.platform.vo.DataPlatformMenuVo;
import com.deloitte.data.platform.vo.ParameterConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 公式
 *
 * @author zhengyingxiang
 * @date 2022/11/21
 */
@Api(tags = "参数配置")
@RestController
@RequestMapping("/parameterConfig")
public class ParameterConfigController {

    @Autowired
    private IModelEvidenceService modelEvidenceService;

    @ApiOperation("参数配置表")
    @GetMapping("/list")
    public R<IPage<ParameterConfigVo.ListVo>> getParameterConfigListPage(ParameterConfigDto.ListDto dto) {
        return R.ok(modelEvidenceService.getParameterConfigListPage(dto));
    }

    @ApiOperation("新增/修改参数配置(基础层)")
    @PostMapping("/addOrUpdateBase")
    public R addOrUpdateBase(@RequestBody ParameterConfigDto.UpdateBaseDto dto) {
        return R.ok(modelEvidenceService.addOrUpdateBase(dto));
    }

    @ApiOperation("新增/修改参数配置(中间层)")
    @PostMapping("/addOrUpdateMiddle")
    public R addOrUpdateMiddle(@RequestBody ParameterConfigDto.UpdateMiddleDto dto) {
        return R.ok(modelEvidenceService.addOrUpdateMiddle(dto));
    }

    @ApiOperation("新增/修改参数配置(指标层)")
    @PostMapping("/addOrUpdateApply")
    public R addOrUpdateApply(@RequestBody ParameterConfigDto.UpdateApplyDto dto) {
        return R.ok(modelEvidenceService.addOrUpdateApply(dto));
    }

    @ApiOperation("质检规则列表")
    @GetMapping("/modelDataCheckList")
    public R<IPage<ParameterConfigVo.ModelDataCheckListVo>> getModelDataCheckListPage(ParameterConfigDto.modelDataCheckListPageDto dto) {
        return R.ok(modelEvidenceService.getModelDataCheckListPage(dto));
    }

    @ApiOperation("质检规则修改/新增")
    @GetMapping("/updateOrAdd")
    public R updateOrAdd(ParameterConfigDto.UpdateOrAddDto dto) {
        return R.ok(modelEvidenceService.updateOrAdd(dto));
    }

    @ApiOperation("质检规则校验")
    @GetMapping("/checkData")
    public R<Boolean> checkData(ParameterConfigDto.CheckDataDto dto) {
        return R.ok(modelEvidenceService.checkData(dto));
    }

    @ApiOperation("获取质检规则菜单")
    @GetMapping("/getBaseConfigDict")
    public R<DataPlatformMenuVo> getBaseConfigDict() {
        return R.ok(modelEvidenceService.getBaseConfigDictSubordinate());
    }

}
