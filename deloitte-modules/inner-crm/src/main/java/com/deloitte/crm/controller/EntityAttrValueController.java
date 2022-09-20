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
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.service.IEntityAttrValueService;
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
@RequestMapping("/value")
public class EntityAttrValueController extends BaseController
{
    @Autowired
    private IEntityAttrValueService entityAttrValueService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:value:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityAttrValue entityAttrValue)
    {
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
    public void export(HttpServletResponse response, EntityAttrValue entityAttrValue)
    {
        List<EntityAttrValue> list = entityAttrValueService.selectEntityAttrValueList(entityAttrValue);
        ExcelUtil<EntityAttrValue> util = new ExcelUtil<EntityAttrValue>(EntityAttrValue.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:value:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(entityAttrValueService.selectEntityAttrValueById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:value:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EntityAttrValue entityAttrValue)
    {
        return toAjax(entityAttrValueService.insertEntityAttrValue(entityAttrValue));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:value:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntityAttrValue entityAttrValue)
    {
        return toAjax(entityAttrValueService.updateEntityAttrValue(entityAttrValue));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:value:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(entityAttrValueService.deleteEntityAttrValueByIds(ids));
    }
}
