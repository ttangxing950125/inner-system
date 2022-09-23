package com.deloitte.crm.controller;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.deloitte.crm.dto.CrmWindTaskDto;
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
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.page.TableDataInfo;

/**
 * 角色1的每日任务，导入wind文件的任务Controller
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/windTask")
public class CrmWindTaskController extends BaseController
{
    @Autowired
    private ICrmWindTaskService crmWindTaskService;


    /**
     * 查询角色1的每日任务，导入wind文件的任务列表
     */
    @RequiresPermissions("crm:windTask:list")
    @GetMapping("/list")
    public TableDataInfo list(CrmWindTask crmWindTask)
    {
        startPage();
        List<CrmWindTask> list = crmWindTaskService.selectCrmWindTaskList(crmWindTask);
        return getDataTable(list);
    }

  /**
   *根据月份获取当月的任务信息
   *
   * @return AjaxResult
   * @author penTang
   * @date 2022/9/21 18:06
  */

    @PostMapping("/queryList")
    public AjaxResult getDataTable(@RequestBody String TaskDate) {
      return AjaxResult.success("查询成功",crmWindTaskService.selectCrmWindTaskByDate(TaskDate));
    }
    /**
     *根据指定日期查询任务完成度
     *
     * @param TaskDate
     * @return AjaxResult
     * @author penTang
     * @date 2022/9/22 10:45
    */
    @PostMapping("/queryTaskByDate")
    public AjaxResult getTaskCompleted(@RequestBody String TaskDate) {
       return AjaxResult.success("查询成功",crmWindTaskService.selectComTaskByDate(TaskDate));
    }
    /**
     *查询某一组的基础任务信息
     *
     * @param TaskDate
     * @param TaskCateId
     * @return AjaxResult
     * @author penTang
     * @date 2022/9/22 17:02
    */
    @PostMapping("/queryTaskByDate")
    public AjaxResult selectCrmWindTask(@RequestBody String TaskDate,String TaskCateId) {
        return AjaxResult.success("查询成功",crmWindTaskService.selectCrmWindTask(TaskDate,TaskCateId));
    }


    /**
     * 导出角色1的每日任务，导入wind文件的任务列表
     */
    @RequiresPermissions("crm:windTask:export")
    @Log(title = "角色1的每日任务，导入wind文件的任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrmWindTask crmWindTask)
    {
        List<CrmWindTask> list = crmWindTaskService.selectCrmWindTaskList(crmWindTask);
        ExcelUtil<CrmWindTask> util = new ExcelUtil<CrmWindTask>(CrmWindTask.class);
        util.exportExcel(response, list, "角色1的每日任务，导入wind文件的任务数据");
    }

    /**
     * 获取角色1的每日任务，导入wind文件的任务详细信息
     */
    @RequiresPermissions("crm:windTask:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(crmWindTaskService.selectCrmWindTaskById(id));
    }

    /**
     * 新增角色1的每日任务，导入wind文件的任务
     */
    @RequiresPermissions("crm:windTask:add")
    @Log(title = "角色1的每日任务，导入wind文件的任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrmWindTask crmWindTask)
    {
        return toAjax(crmWindTaskService.insertCrmWindTask(crmWindTask));
    }

    /**
     * 修改角色1的每日任务，导入wind文件的任务
     */
    @RequiresPermissions("crm:windTask:edit")
    @Log(title = "角色1的每日任务，导入wind文件的任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrmWindTask crmWindTask)
    {
        return toAjax(crmWindTaskService.updateCrmWindTask(crmWindTask));
    }

    /**
     * 删除角色1的每日任务，导入wind文件的任务
     */
    @RequiresPermissions("crm:windTask:remove")
    @Log(title = "角色1的每日任务，导入wind文件的任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(crmWindTaskService.deleteCrmWindTaskByIds(ids));
    }
}
