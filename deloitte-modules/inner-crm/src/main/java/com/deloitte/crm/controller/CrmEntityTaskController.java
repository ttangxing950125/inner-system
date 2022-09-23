package com.deloitte.crm.controller;

import java.util.List;
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
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.page.TableDataInfo;

/**
 * 角色7，根据导入的数据新增主体的任务Controller
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/entityTask")
public class CrmEntityTaskController extends BaseController
{
    @Autowired
    private ICrmEntityTaskService crmEntityTaskService;

    /**
     * 查询角色7，根据导入的数据新增主体的任务列表
     */
    @RequiresPermissions("crm:entityTask:list")
    @GetMapping("/list")
    public TableDataInfo list(CrmEntityTask crmEntityTask)
    {
        startPage();
        List<CrmEntityTask> list = crmEntityTaskService.selectCrmEntityTaskList(crmEntityTask);
        return getDataTable(list);
    }

    /**
     * 导出角色7，根据导入的数据新增主体的任务列表
     */
    @RequiresPermissions("crm:entityTask:export")
    @Log(title = "角色7，根据导入的数据新增主体的任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrmEntityTask crmEntityTask)
    {
        List<CrmEntityTask> list = crmEntityTaskService.selectCrmEntityTaskList(crmEntityTask);
        ExcelUtil<CrmEntityTask> util = new ExcelUtil<CrmEntityTask>(CrmEntityTask.class);
        util.exportExcel(response, list, "角色7，根据导入的数据新增主体的任务数据");
    }

    /**
     * 获取角色7，根据导入的数据新增主体的任务详细信息
     */
    @RequiresPermissions("crm:entityTask:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(crmEntityTaskService.selectCrmEntityTaskById(id));
    }

    /**
     * 新增角色7，根据导入的数据新增主体的任务
     */
    @RequiresPermissions("crm:entityTask:add")
    @Log(title = "角色7，根据导入的数据新增主体的任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrmEntityTask crmEntityTask)
    {
        return toAjax(crmEntityTaskService.insertCrmEntityTask(crmEntityTask));
    }

    /**
     * 修改角色7，根据导入的数据新增主体的任务
     */
    @RequiresPermissions("crm:entityTask:edit")
    @Log(title = "角色7，根据导入的数据新增主体的任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrmEntityTask crmEntityTask)
    {
        return toAjax(crmEntityTaskService.updateCrmEntityTask(crmEntityTask));
    }

    /**
     * 删除角色7，根据导入的数据新增主体的任务
     */
    @RequiresPermissions("crm:entityTask:remove")
    @Log(title = "角色7，根据导入的数据新增主体的任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(crmEntityTaskService.deleteCrmEntityTaskByIds(ids));
    }

}
