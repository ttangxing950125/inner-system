package com.deloitte.crm.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.ModelMaster;
import com.deloitte.crm.service.IModelMasterService;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/modelMaster")
public class ModelMasterController extends BaseController
{
    @Autowired
    private IModelMasterService modelMasterService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:modelMaster:list")
    @GetMapping("/list")
    public TableDataInfo list(ModelMaster modelMaster)
    {
        startPage();
        List<ModelMaster> list = modelMasterService.selectModelMasterList(modelMaster);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:modelMaster:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ModelMaster modelMaster)
    {
        List<ModelMaster> list = modelMasterService.selectModelMasterList(modelMaster);
        ExcelUtil<ModelMaster> util = new ExcelUtil<ModelMaster>(ModelMaster.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:modelMaster:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(modelMasterService.selectModelMasterById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:modelMaster:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ModelMaster modelMaster)
    {
        return toAjax(modelMasterService.insertModelMaster(modelMaster));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:modelMaster:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ModelMaster modelMaster)
    {
        return toAjax(modelMasterService.updateModelMaster(modelMaster));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:modelMaster:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(modelMasterService.deleteModelMasterByIds(ids));
    }
}
