package com.deloitte.data.platform.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.dto.ApplyDataConfigDto;
import com.deloitte.data.platform.dto.BaseDataConfigDto;
import com.deloitte.data.platform.dto.MiddleDataConfigDto;
import com.deloitte.data.platform.service.IDataUpdateLogService;
import com.deloitte.data.platform.service.IEvidenceModelService;
import com.deloitte.data.platform.service.IFinancialDataConfigService;
import com.deloitte.data.platform.service.IModelRationFactorService;
import com.deloitte.data.platform.vo.ApplyDataConfigVo;
import com.deloitte.data.platform.vo.BaseDataConfigVo;
import com.deloitte.data.platform.vo.DataUpdateInfoVo;
import com.deloitte.data.platform.vo.MiddleDataConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 基础数据字典
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Api(tags = "数据字典")
@RestController
@RequestMapping("/dataDict")
public class DataDictController {

    @Resource
    private IFinancialDataConfigService iFinancialDataConfigService;

    @Resource
    private IEvidenceModelService iEvidenceModelService;

    @Resource
    private IModelRationFactorService iModelRationFactorService;

    @Resource
    private IDataUpdateLogService iDataUpdateLogService;

    @ApiOperation("更新信息")
    @GetMapping("/updateInfo")
    public R<DataUpdateInfoVo> getBaseDataConfigUpdateInfo() {
        return R.ok(iDataUpdateLogService.getBaseDataConfigUpdateInfo());
    }

    @ApiOperation("基础层数据字典分页")
    @GetMapping("/baseData/page")
    public R<IPage<BaseDataConfigVo>> getBaseDataConfigPage(BaseDataConfigDto dto) {
        return R.ok(iFinancialDataConfigService.getFinancialBaseDataConfigPage(dto));
    }

    @ApiOperation("中间层数据字典分页")
    @GetMapping("/middleData/page")
    public R<IPage<MiddleDataConfigVo>> getMiddleDataConfigPage(MiddleDataConfigDto dto) {
        return R.ok(iEvidenceModelService.getMiddleDataConfigPage(dto));
    }

    @ApiOperation("指标层数据字典分页")
    @GetMapping("/applyData/page")
    public R<IPage<ApplyDataConfigVo>> getApplyDataConfigPage(ApplyDataConfigDto dto) {
        return R.ok(iModelRationFactorService.getApplyDataConfigPage(dto));
    }
}
