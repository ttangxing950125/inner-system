package com.deloitte.data.platform.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.dto.BaseDataDto;
import com.deloitte.data.platform.dto.CodeEntityDto;
import com.deloitte.data.platform.service.IBaseEntityDataUpdateLogService;
import com.deloitte.data.platform.service.IBaseFinDataService;
import com.deloitte.data.platform.dto.EntityInfoDto;
import com.deloitte.data.platform.service.IEntityInfoService;
import com.deloitte.data.platform.vo.BaseFinDataVo;
import com.deloitte.data.platform.vo.CodeEntityVo;
import com.deloitte.data.platform.vo.DataUpdateInfoVo;
import com.deloitte.data.platform.vo.EntityInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据质检
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Api(tags = "数据质检")
@RestController
@RequestMapping("/dataInspection")
public class DataInspectionController {

    @Resource
    private IBaseFinDataService iBaseFinDataService;

    @Resource
    private IBaseEntityDataUpdateLogService iBaseEntityDataUpdateLogService;

    @Resource
    private IEntityInfoService iEntityInfoService;

    @ApiOperation("数据质检")
    @GetMapping("/entityInfo/page")
    public R<IPage<EntityInfoVo>> getEntityInfoPage(@Validated EntityInfoDto dto) {
        return R.ok(iEntityInfoService.getEntityInfoPage(dto));
    }

    @ApiOperation("更新信息")
    @GetMapping("/updateInfo")
    public R<DataUpdateInfoVo> getBaseDataConfigUpdateInfo() {
        return R.ok(iBaseEntityDataUpdateLogService.getBaseDataConfigUpdateInfo());
    }

    @ApiOperation("质检结果")
    @GetMapping("/baseFinData/page")
    public R<IPage<BaseFinDataVo>> getBaseFinDataPage(BaseDataDto dto) {
        return R.ok(iBaseFinDataService.getBaseFinDataPage(dto));
    }

    @ApiOperation("字段代码结果")
    @GetMapping("/codeEntity/page")
    public R<IPage<CodeEntityVo>> getCodeEntityPage(@Validated CodeEntityDto dto) {
        return R.ok(iBaseFinDataService.getCodeEntityPage(dto));
    }
    @ApiOperation("通过人工质检状态")
    @PutMapping("/isArtificialInspection/{year}/{dataId}")
    public R<Void> updateIsArtificialInspection(@PathVariable String year,@PathVariable Integer dataId) {
        iBaseFinDataService.updateIsArtificialInspection(year,dataId);
        return R.ok();
    }

    @ApiOperation("获取基础数据的最近三年")
    @GetMapping("/years")
    public R<List<String>> getBaseFinDataYears() {
        return R.ok(iBaseFinDataService.getBaseFinDataYears());
    }
}
