package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.service.ICrmSupplyTaskService;
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
@RequestMapping("/supplyTask")
public class CrmSupplyTaskController extends BaseController {
    @Autowired
    private ICrmSupplyTaskService crmSupplyTaskService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:supplyTask:list")
    @GetMapping("/list")
    public TableDataInfo list(CrmSupplyTask crmSupplyTask) {
        startPage();
        List<CrmSupplyTask> list = crmSupplyTaskService.selectCrmSupplyTaskList(crmSupplyTask);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:supplyTask:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrmSupplyTask crmSupplyTask) {
        List<CrmSupplyTask> list = crmSupplyTaskService.selectCrmSupplyTaskList(crmSupplyTask);
        ExcelUtil<CrmSupplyTask> util = new ExcelUtil<CrmSupplyTask>(CrmSupplyTask.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:supplyTask:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(crmSupplyTaskService.selectCrmSupplyTaskById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:supplyTask:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrmSupplyTask crmSupplyTask) {
        return toAjax(crmSupplyTaskService.insertCrmSupplyTask(crmSupplyTask));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:supplyTask:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrmSupplyTask crmSupplyTask) {
        return toAjax(crmSupplyTaskService.updateCrmSupplyTask(crmSupplyTask));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:supplyTask:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(crmSupplyTaskService.deleteCrmSupplyTaskByIds(ids));
    }

    @PostMapping("/getTaskStatistics")
    public R getTaskStatistics() {
        return R.ok(crmSupplyTaskService.getTaskStatistics());
    }
}
