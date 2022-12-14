package com.deloitte.crm.controller;

import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.service.ICrmMasTaskService;
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
@RequestMapping("/masTask")
public class CrmMasTaskController extends BaseController
{
    @Autowired
    private ICrmMasTaskService crmMasTaskService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:masTask:list")
    @GetMapping("/list")
    public TableDataInfo list(CrmMasTask crmMasTask)
    {
        startPage();
        List<CrmMasTask> list = crmMasTaskService.selectCrmMasTaskList(crmMasTask);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:masTask:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrmMasTask crmMasTask)
    {
        List<CrmMasTask> list = crmMasTaskService.selectCrmMasTaskList(crmMasTask);
        ExcelUtil<CrmMasTask> util = new ExcelUtil<CrmMasTask>(CrmMasTask.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:masTask:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(crmMasTaskService.selectCrmMasTaskById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:masTask:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrmMasTask crmMasTask)
    {
        return toAjax(crmMasTaskService.insertCrmMasTask(crmMasTask));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:masTask:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrmMasTask crmMasTask)
    {
        return toAjax(crmMasTaskService.updateCrmMasTask(crmMasTask));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:masTask:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(crmMasTaskService.deleteCrmMasTaskByIds(ids));
    }
}
