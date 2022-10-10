package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.dto.GovInfoDto;
import com.deloitte.crm.service.IGovInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 【请填写功能名称】Controller
 *
 * @author deloitte
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/govInfo")
@Api(tags = "政府主体查询修改相关数据")

public class GovInfoController extends BaseController {
    @Autowired
    private IGovInfoService govInfoService;


    /**
     * 统计政府信息
     *
     * @return GovInfoDto
     * @author penTang
     * @date 2022/9/22 23:21
     */
    @PostMapping("/govList")
    @ApiOperation(value = "{统计政府信息}", response = GovInfoDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query"

            )
    })
    public R getGovInfo() {
        return R.ok(govInfoService.getGovInfo());

    }

    /**
     * 导出政府信息
     *
     * @return GovInfoDto
     * @author penTang
     * @date 2022/9/22 23:21
     */
    @PostMapping("/govExport")
    @ApiOperation(value = "{导出政府信息}", response = GovInfoDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "entityAttrDto", value = "包含表中gov_info的所有字段"

            )
    })
    public R importGovInfo(EntityAttrByDto entityAttrDto) {
        govInfoService.ExportEntityGov(entityAttrDto);
        return R.ok();
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:govInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(GovInfo govInfo) {
        startPage();
        List<GovInfo> list = govInfoService.selectGovInfoList(govInfo);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:govInfo:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GovInfo govInfo) {
        List<GovInfo> list = govInfoService.selectGovInfoList(govInfo);
        ExcelUtil<GovInfo> util = new ExcelUtil<GovInfo>(GovInfo.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:govInfo:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(govInfoService.selectGovInfoById(id));
    }

    /**
     * 新增地方政府
     */
    @RequiresPermissions("crm:govInfo:add")
    @Log(title = "【新增地方政府】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody GovInfo govInfo) {
        return toAjax(govInfoService.insertGovInfo(govInfo));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:govInfo:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GovInfo govInfo) {
        return toAjax(govInfoService.updateGovInfo(govInfo));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:govInfo:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(govInfoService.deleteGovInfoByIds(ids));
    }

    /**
     * 政府主体批量修改
     *
     * @param govInfoList
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 15:24
     */
    @ApiOperation(value = "政府主题批量修改")
    @ApiImplicitParam(name = "govInfoList", value = "可包含表 gov_info 的所有字段", paramType = "body", example = "", dataTypeClass = GovInfo.class)
    @PostMapping("/updateInfoList")
    public R updateInfoList(List<GovInfo> govInfoList) {
        return R.ok(govInfoService.updateInfoList(govInfoList));
    }

    /**
     * 查询政府名称，或者编码，是否重复
     *
     * @param govInfo
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "查询政府名称，或者编码，是否重复")
    @ApiImplicitParam(name = "govInfo", value = "包含表中egov_info的所有字段", paramType = "body", example = "", dataTypeClass = GovInfo.class)
    @PostMapping("/checkGov")
    public R checkGov(@RequestBody GovInfo govInfo) {
        return R.ok(govInfoService.checkGov(govInfo));
    }

    /**
     * 政府主体分类查询
     *
     * @param type  政府主体类型 1、地方政府2、地方主管部门3、其他
     * @param param 筛选条件
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "政府主体分类查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "政府主体类型 1、地方政府2、地方主管部门3、其他", paramType = "query", example = "", dataType = "Integer"),
            @ApiImplicitParam(name = "param", value = "param 筛选条件", paramType = "query", example = "", dataType = "String")
    })
    @PostMapping("/getInfoList")
    public R getInfoList(Integer type, String param) {
        return govInfoService.getInfoList(type, param);
    }

    /**
     * 政府主体分类概览
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "政府主体分类概览")
    @PostMapping("/getOverviewByGroup")
    public R getOverviewByGroup() {
        return R.ok(govInfoService.getOverviewByGroup());
    }

    /**
     * 新增政府主体的曾用名
     *
     * @param govInfo
     * @return R
     * @author 冉浩岑
     * @date 2022/9/23 8:44
     */
    @ApiOperation(value = "新增政府主体的曾用名")
    @ApiImplicitParam(name = "govInfo", value = "包含表中gov_info的所有字段", paramType = "body", example = "", dataTypeClass = GovInfo.class)
    @PostMapping("/addOldName")
    public R addOldName(@RequestBody GovInfo govInfo) {
        return govInfoService.addOldName(govInfo);
    }

    /**
     * 修改,停用政府主体的曾用名
     *
     * @param dqCode
     * @param oldName
     * @param newOldName
     * @param status
     * @return R
     * @author 冉浩岑
     * @date 2022/9/25 13:22
     */
    @ApiOperation(value = "修改,停用政府主体的曾用名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dqCode", value = "德勤统一识别码", paramType = "query", example = "1", dataType = "String"),
            @ApiImplicitParam(name = "oldName", value = "原本的曾用名", paramType = "query", example = "原始曾用名", dataType = "String"),
            @ApiImplicitParam(name = "newOldName", value = "修改后的曾用名", paramType = "query", example = "新的曾用名", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "是否停用曾用名", paramType = "query", example = "新的曾用名", dataType = "String")
    })
    @PostMapping("/updateOldName")
    public R updateOldName(String dqCode, String oldName, String newOldName, String status) {
        return govInfoService.updateOldName(dqCode, oldName, newOldName, status);
    }

    /**
     * 根据 dqCode 查询政府主体
     *
     * @param govInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 8:59
     */
    @ApiOperation(value = "根据 dqCode 查询政府主体")
    @ApiImplicitParam(name = "govInfo", value = "包含表中gov_info的所有字段", paramType = "body", example = "", dataTypeClass = GovInfo.class)
    @PostMapping("/getInfoDetail")
    public R getInfoDetail(@RequestBody GovInfo govInfo) {
        return govInfoService.getInfoDetail(govInfo);
    }

    /**
     * 分页查询全部政府主体
     *
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 10:56
     */
    @ApiOperation(value = "分页查询全部政府主体")
    @ApiImplicitParam(name = "entityAttrDto", value = "包含表中gov_info的所有字段和分页参数 pageSize pageNum", paramType = "body", example = "", dataTypeClass = EntityAttrByDto.class)
    @PostMapping("/getListEntityByPage")
    public R getListEntityByPage(@RequestBody EntityAttrByDto govAttrDto) {
        return R.ok(govInfoService.getListEntityByPage(govAttrDto));
    }

    /**
     * 政府主体总概览
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/28 10:56
     */
    @ApiOperation(value = "政府主体总概览")
    @PostMapping("/getOverview")
    public R getOverview() {
        return R.ok(govInfoService.getOverview());
    }

    /**
     * 导出导出全部企业主体Excel表
     *
     * @param govAttrDto
     * @return R
     * @author penTang
     * @date 2022/9/25 17:48
     */
    @ApiOperation(value = "导出全部企业主体Excel表")
    @ApiImplicitParam(name = "entityAttrDto", value = "包含表中gov_info的所有字段", paramType = "body", example = "", dataTypeClass = EntityAttrByDto.class)
    @PostMapping("/getListEntity")
    public R ImportListEntity(@RequestBody EntityAttrByDto govAttrDto) {
        return R.ok(govInfoService.getListEntityByPage(govAttrDto));
    }


    /**
     * 获取省市级数据
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/29 15:49
     */
    @ApiOperation(value = "获取省市级数据")
    @ApiImplicitParam(name = "preGovCode", value = "父级Code", paramType = "body", example = "GV10110", dataType = "Integer")
    @PostMapping("/getGovLevel")
    public R getGovLevel(String preGovCode) {
        return R.ok(govInfoService.getGovLevel(preGovCode));
    }
}
