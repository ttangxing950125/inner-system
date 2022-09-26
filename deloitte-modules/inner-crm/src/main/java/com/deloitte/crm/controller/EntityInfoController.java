package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.EntityInfo;

import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.EntityInfoByDto;

import com.deloitte.crm.dto.EntityDto;
import com.deloitte.crm.dto.EntityInfoDto;
import com.deloitte.crm.service.IEntityInfoService;
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
@RequestMapping("/entityInfo")
@Api(tags = "企业主体查询修改相关数据")
public class EntityInfoController extends BaseController {
    @Autowired
    private IEntityInfoService entityInfoService;

    /**
     * 统计整体企业主体情况
     *
     * @return R
     * @author penTang
     * @date 2022/9/22 22:41
     */
    @PostMapping("/entityInfoList")
    @ApiOperation(value = "{统计整体企业主体情况}", response = EntityInfoDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query"

            )
    })

    public R getList() {
        return R.ok(entityInfoService.getEntityInfo());
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:entityInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityInfo entityInfo) {
        startPage();
        List<EntityInfo> list = entityInfoService.selectEntityInfoList(entityInfo);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:entityInfo:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntityInfo entityInfo) {
        List<EntityInfo> list = entityInfoService.selectEntityInfoList(entityInfo);
        ExcelUtil<EntityInfo> util = new ExcelUtil<EntityInfo>(EntityInfo.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:entityInfo:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(entityInfoService.selectEntityInfoById(id));
    }

    /**
     * @param entityDto
     * @return
     * @author 正杰
     * @date 2022/9/22
     * 新增【确定该主体是新增后,填写具体要新增主体的信息】
     */
    @RequiresPermissions("crm:entityInfo:add")
    @Log(title = "【确定该主体是新增后,填写具体要新增主体的信息】", businessType = BusinessType.INSERT)
    @ApiImplicitParam(name = "entityInfoDto", value = "包含表中entity_info所有字段以及 haveCreditCode oldName 额外两个字段", paramType = "body")
    @PostMapping("/add")
    public AjaxResult addEntity(@RequestBody EntityDto entityDto) {
        //TODO 新增主体
        return toAjax(entityInfoService.insertEntityInfo(entityDto));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:entityInfo:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody EntityInfo entityInfo) {
        return toAjax(entityInfoService.updateEntityInfo(entityInfo));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:entityInfo:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(entityInfoService.deleteEntityInfoByIds(ids));
    }

    /**
     * 企业主体批量修改
     *
     * @param entityInfoList
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 15:24
     */
    @ApiOperation(value = "企业主体批量修改")
    @ApiImplicitParam(name = "entityInfoList", value = "包含表中entity_info的所有字段", paramType = "body", example = "", dataTypeClass = EntityInfo.class)
    @PostMapping("/updateInfoList")
    public R updateInfoList(List<EntityInfo> entityInfoList) {
        return R.ok(entityInfoService.updateInfoList(entityInfoList));
    }

    /**
     * 查询企业名称，或者编码，是否重复
     *
     * @param entityInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "查询企业名称，或者编码，是否重复")
    @ApiImplicitParam(name = "entityInfo", value = "包含表中eentity_info的所有字段", paramType = "body", example = "", dataTypeClass = EntityInfo.class)
    @PostMapping("/checkEntity")
    public R checkEntity(@RequestBody EntityInfo entityInfo) {
        return R.ok(entityInfoService.checkEntity(entityInfo));
    }

    /**
     * 企业主体分页查询
     *
     * @param entityInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "企业主体分页查询")
    @ApiImplicitParam(name = "entityInfo", value = "包含表中eentity_info的所有字段", paramType = "body", example = "", dataTypeClass = EntityInfoByDto.class)
    @PostMapping("/getInfoList")
    public R getInfoList(@RequestBody EntityInfoByDto entityInfo) {
        return entityInfoService.getInfoList(entityInfo);
    }

    /**
     * 新增企业主体的曾用名
     *
     * @param entityInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 8:44
     */
    @ApiOperation(value = "新增企业主体的曾用名")
    @ApiImplicitParam(name = "entityInfo", value = "包含表中entity_info的所有字段", paramType = "body", example = "", dataTypeClass = EntityInfo.class)
    @PostMapping("/addOldName")
    public R addOldName(@RequestBody EntityInfo entityInfo) {
        return entityInfoService.addOldName(entityInfo);
    }

    /**
     * 修改企业主体的曾用名
     *
     * @param dqCode
     * @param oldName
     * @param newOldName
     * @param status
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/25 13:22
     */
    @ApiOperation(value = "修改企业主体的曾用名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dqCode", value = "德勤统一识别码", paramType = "query", example = "1", dataType = "String"),
            @ApiImplicitParam(name = "oldName", value = "原本的曾用名", paramType = "query", example = "原始曾用名", dataType = "String"),
            @ApiImplicitParam(name = "newOldName", value = "修改后的曾用名", paramType = "query", example = "新的曾用名", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "是否停用曾用名", paramType = "query", example = "新的曾用名", dataType = "String")
    })
    @PostMapping("/updateOldName")
    public R updateOldName(String dqCode, String oldName, String newOldName, String status) {
        return entityInfoService.updateOldName(dqCode, oldName, newOldName, status);
    }

    /**
     * 根据 dqCode 查询企业主体
     *
     * @param entityInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 8:59
     */
    @ApiOperation(value = "根据 dqCode 查询企业主体")
    @ApiImplicitParam(name = "entityInfo", value = "包含表中eentity_info的所有字段", paramType = "body", example = "", dataTypeClass = EntityInfo.class)
    @PostMapping("/getInfoDetail")
    public R getInfoDetail(@RequestBody EntityInfo entityInfo) {
        return entityInfoService.getInfoDetail(entityInfo);
    }

    /**
     * 分页查询全部上市主体
     *
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 10:56
     */
    @ApiOperation(value = "分页查询全部上市主体")
    @ApiImplicitParam(name = "entityAttrDto", value = "包含表中entity_info的所有字段和分页参数 pageSize pageNum", paramType = "body", example = "", dataTypeClass = EntityAttrByDto.class)
    @PostMapping("/getListEntityByPage")
    public R getListEntityByPage(@RequestBody EntityAttrByDto entityAttrDto) {
        return R.ok(entityInfoService.getListEntityByPage(entityAttrDto));
    }

}
