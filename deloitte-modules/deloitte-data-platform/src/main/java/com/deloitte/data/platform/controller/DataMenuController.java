package com.deloitte.data.platform.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.dto.MyLibraryTableDto;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.enums.HierarchyEnum;
import com.deloitte.data.platform.service.*;
import com.deloitte.data.platform.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据平台菜单
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Api(tags = "数据平台菜单")
@RestController
@RequestMapping("/dataMenu")
public class DataMenuController {
    @Resource
    private IDataPlatformConfigDictService iDataPlatformConfigDictService;

    @Resource
    private IEntityInfoService iEntityInfoService;

    @Resource
    private IModelRationFactorService iModelRationFactorService;

    @Resource
    private IModelEvidenceService iModelEvidenceService;

    @Resource
    private IDataPlatformMyLibraryTableService iDataPlatformMyLibraryTableService;

    @Resource
    private IFinancialDataConfigService iFinancialDataConfigService;

    @ApiOperation("顶上搜索")
    @GetMapping("/top")
    public R<List<TopDataMenuVo>> getTopDataMenu(@Validated TopDataMenuDto dto) {
        List<TopDataMenuVo> result = new ArrayList<>();
        dto.setLimitNum(20);
        if (HierarchyEnum.BASE.getCode().equals(dto.getType())){
            result = iDataPlatformConfigDictService.getTopDataMenu(dto);
        }else if(HierarchyEnum.MIDDLE.getCode().equals(dto.getType())){
            result = iModelEvidenceService.getTopDataMenu(dto);
        }else if(HierarchyEnum.APPLY.getCode().equals(dto.getType())){
            result = iModelRationFactorService.getTopDataMenu(dto);
        }else if(HierarchyEnum.ENTITY.getCode().equals(dto.getType())){
            result = iEntityInfoService.getTopDataMenu(dto);
        }else{
            dto.setLimitNum(5);
            List<TopDataMenuVo> result1 = iDataPlatformConfigDictService.getTopDataMenu(dto);
            List<TopDataMenuVo> result2 = iModelEvidenceService.getTopDataMenu(dto);
            List<TopDataMenuVo> result3 = iModelRationFactorService.getTopDataMenu(dto);
            List<TopDataMenuVo> result4 = iEntityInfoService.getTopDataMenu(dto);
            result.addAll(result1);
            result.addAll(result2);
            result.addAll(result3);
            result.addAll(result4);
        }
        return R.ok(result);
    }

    @ApiOperation("全部")
    @GetMapping("/all")
    public R<DataPlatformMenuVo> getAllDataMenu() {
        return R.ok(iDataPlatformConfigDictService.getAllDataMenu());
    }

    @ApiOperation("我的库表")
    @GetMapping("/myLibraryTable")
    public R<DataPlatformMenuVo> getMyLibraryTable() {
        return R.ok(iDataPlatformMyLibraryTableService.getMyLibraryTable());
    }

    @ApiOperation("新增我的库表")
    @PostMapping("/myLibraryTable")
    public R<Void> addMyLibraryTable(@RequestBody @Validated MyLibraryTableDto dto) {
        iDataPlatformMyLibraryTableService.addMyLibraryTable(dto);
        return R.ok();
    }

    @ApiOperation("删除我的库表")
    @DeleteMapping("/myLibraryTable/{id}")
    public R<Void> deleteMyLibraryTable(@PathVariable Integer id) {
        iDataPlatformMyLibraryTableService.deleteMyLibraryTable(id);
        return R.ok();
    }


}
