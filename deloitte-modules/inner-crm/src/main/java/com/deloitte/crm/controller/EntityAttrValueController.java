package com.deloitte.crm.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.vo.EntityByIondVo;
import com.deloitte.crm.vo.EntityStockInfoVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.dto.EntityAttrValueDto;
import com.deloitte.crm.service.IEntityAttrValueService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Controller
 *
 * @author deloitte
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/value")
public class EntityAttrValueController extends BaseController {
    @Autowired
    private IEntityAttrValueService entityAttrValueService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:value:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityAttrValue entityAttrValue) {
        startPage();
        List<EntityAttrValue> list = entityAttrValueService.selectEntityAttrValueList(entityAttrValue);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:value:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntityAttrValue entityAttrValue) {
        List<EntityAttrValue> list = entityAttrValueService.selectEntityAttrValueList(entityAttrValue);
        ExcelUtil<EntityAttrValue> util = new ExcelUtil<EntityAttrValue>(EntityAttrValue.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:value:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(entityAttrValueService.selectEntityAttrValueById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:value:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EntityAttrValue entityAttrValue) {
        return toAjax(entityAttrValueService.insertEntityAttrValue(entityAttrValue));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:value:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntityAttrValue entityAttrValue) {
        return toAjax(entityAttrValueService.updateEntityAttrValue(entityAttrValue));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:value:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(entityAttrValueService.deleteEntityAttrValueByIds(ids));
    }

    /**
     * 根据entityCode补充录入副表信息
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/25 13:56
     */
    @ApiOperation(value = "根据entityCode补充录入副表信息")
    @ApiImplicitParam(name = "valueList", value = "增改数据", paramType = "body", example = "", dataTypeClass = EntityAttrValueDto.class)
    @PostMapping("/addEntityAttrValues")
//    public R addEntityAttrValues(List<EntityAttrValue> valueList) {
    public R addEntityAttrValues(@RequestBody EntityAttrValue value) {
        List<EntityAttrValue> valueList = new ArrayList<>();
        valueList.add(value);
        return R.ok(entityAttrValueService.addEntityAttrValues(valueList));
    }


    @PostMapping("/createBE")
    @ApiOperation(value = "新增债券主体")
    @ApiImplicitParam(name = "entityByIondVo", value = "", paramType = "body", example = "", dataTypeClass = EntityByIondVo.class)
    /**
     *新增债券主体
     *
     * @param entityByIondVo
     * @return R
     * @author penTang
     * @date 2022/9/27 20:40
     */
    public R createBondEntity(@Validated @RequestBody EntityByIondVo entityByIondVo) {

        return entityAttrValueService.createBondEntity(entityByIondVo);

    }


    @PostMapping("/createST")
    @ApiOperation(value = "新增股票主体")
    @ApiImplicitParam(name = "entityStockInfoVo", value = "", paramType = "body", example = "", dataTypeClass = EntityStockInfoVo.class)
    /**
     *创建股票主体(根据股票状态 A -A股 G -港股)
     *
     * @param entityStockInfoVo
     * @return R
     * @author penTang
     * @date 2022/9/28 23:09
     */
    public R createStockEntity(@Validated @RequestBody EntityStockInfoVo entityStockInfoVo) {
        if (ObjectUtils.equals(entityStockInfoVo.getStockType(), "A")) {
            return entityAttrValueService.createStockEntity(entityStockInfoVo);
        } else if (ObjectUtils.equals(entityStockInfoVo.getStockType(), "G")) {
            return entityAttrValueService.createStockEntityG(entityStockInfoVo);
        }
        return null;
    }




    /**
     * 根据entityCode补充录入副表信息
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/25 13:56
     */
    @ApiOperation(value = "根据 entityCode 补充录入副表信息")
    @ApiImplicitParam(name = "map", value = "增改数据", paramType = "body", example = "", dataTypeClass = HashMap.class)
    @PostMapping("/addEntityAttrValuesNew")
//    public R addEntityAttrValues(List<EntityAttrValue> valueList) {
    public R addEntityAttrValuesNew(@RequestBody Map<String,String> valueMap) {
        return R.ok(entityAttrValueService.addEntityAttrValuesNew(valueMap));
    }
}
