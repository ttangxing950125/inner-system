package com.deloitte.crm.controller;

import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.EntityNameHis;
import com.deloitte.crm.service.IEntityNameHisService;
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
@RequestMapping("/his")
public class EntityNameHisController extends BaseController
{
    @Autowired
    private IEntityNameHisService entityNameHisService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:his:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityNameHis entityNameHis)
    {
        startPage();
        List<EntityNameHis> list = entityNameHisService.selectEntityNameHisList(entityNameHis);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:his:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntityNameHis entityNameHis)
    {
        List<EntityNameHis> list = entityNameHisService.selectEntityNameHisList(entityNameHis);
        ExcelUtil<EntityNameHis> util = new ExcelUtil<EntityNameHis>(EntityNameHis.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:his:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(entityNameHisService.selectEntityNameHisById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:his:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EntityNameHis entityNameHis)
    {
        return toAjax(entityNameHisService.insertEntityNameHis(entityNameHis));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:his:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntityNameHis entityNameHis)
    {
        return toAjax(entityNameHisService.updateEntityNameHis(entityNameHis));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:his:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(entityNameHisService.deleteEntityNameHisByIds(ids));
    }
    /**
     * 根据德勤code查询曾用名列表
     *
     * @param dqCode
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 23:50
    */
    @PostMapping("/getNameListByDqCoded")
    public AjaxResult getNameListByDqCoded(String dqCode)
    {
        return AjaxResult.success(entityNameHisService.getNameListByDqCoded(dqCode));
    }
}
