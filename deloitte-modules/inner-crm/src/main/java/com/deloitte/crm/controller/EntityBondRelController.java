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
import com.deloitte.crm.domain.EntityBondRel;
import com.deloitte.crm.service.IEntityBondRelService;
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
@RequestMapping("/entityBondRel")
public class EntityBondRelController extends BaseController
{
    @Autowired
    private IEntityBondRelService entityBondRelService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:entityBondRel:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityBondRel entityBondRel)
    {
        startPage();
        List<EntityBondRel> list = entityBondRelService.selectEntityBondRelList(entityBondRel);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:entityBondRel:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntityBondRel entityBondRel)
    {
        List<EntityBondRel> list = entityBondRelService.selectEntityBondRelList(entityBondRel);
        ExcelUtil<EntityBondRel> util = new ExcelUtil<EntityBondRel>(EntityBondRel.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:entityBondRel:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(entityBondRelService.selectEntityBondRelById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:entityBondRel:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EntityBondRel entityBondRel)
    {
        return toAjax(entityBondRelService.insertEntityBondRel(entityBondRel));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:entityBondRel:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntityBondRel entityBondRel)
    {
        return toAjax(entityBondRelService.updateEntityBondRel(entityBondRel));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:entityBondRel:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(entityBondRelService.deleteEntityBondRelByIds(ids));
    }
}
