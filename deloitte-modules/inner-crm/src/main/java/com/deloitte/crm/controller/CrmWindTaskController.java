package com.deloitte.crm.controller;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.vo.WindTaskDetailsVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 角色1的每日任务，导入wind文件的任务Controller
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/windTask")
@Api(tags = "导入wind数据相关接口")
public class CrmWindTaskController extends BaseController
{
    @Autowired
    private ICrmWindTaskService crmWindTaskService;


    @GetMapping("/findTaskDetails")
    @ApiOperation(value = "吴鹏——查询某一天角色1某个分类的wind任务", response = WindTaskDetailsVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    //参数名
                    name = "taskCateId",
                    //参数描述
                    value = "crm_wind_dict中的cate_id，也就是任务大类",
                    //参数出现的地方 query 表单数据
                    //path 路径
                    //body applicationjson
                    paramType = "path",
                    //示例值
                    example = "2"
            ),
            @ApiImplicitParam(
                    //参数名
                    name = "taskDate",
                    //参数描述
                    value = "需要查询任务的日期 yyyy-MM-dd",
                    //参数出现的地方 query 表单数据
                    //path 路径
                    //body applicationjson
                    paramType = "path",
                    //示例值
                    example = "2022-09-25"
            )
    })
    public R<List<WindTaskDetailsVo>> findTaskDetails(Integer taskCateId, String taskDate){
        return R.ok(crmWindTaskService.findTaskDetails(taskCateId, taskDate));
    }


    /**
     * 角色1执行上传文件任务
     * @param taskId
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/doTask/{taskId}")
    @ApiOperation(value = "吴鹏——进行wind任务", response = WindTaskDetailsVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    //参数名
                    name = "taskId",
                    //参数描述
                    value = "crm_wind_task表中的id，也就是当前上传文件的任务",
                    //参数出现的地方 query 表单数据
                    //path 路径
                    //body applicationjson
                    paramType = "path",
                    //示例值
                    example = "13"
            ),
            @ApiImplicitParam(
                    paramType = "form", dataType="file", name = "file",
                    dataTypeClass=MultipartFile.class, required = true,
                    //参数描述
                    value = "上传的文件"
            )
    })
    @ApiResponse(code = 200, message = "返回200就代表任务创建成功在后台执行了")
    public AjaxResult doTask(@PathVariable("taskId") Long taskId, @RequestPart(required = true) MultipartFile file) throws Exception {


        return AjaxResult.success(crmWindTaskService.doTask(taskId, file));
    }


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
    @ApiOperation(value = "{根据指定TaskDate}")
    @ApiImplicitParam(name = "TaskDate",value = "根据指定TaskDate查询数据",required = true,paramType = "body")
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
    @PostMapping("/getTaskByDate")
    @ApiOperation(value = "{根据指定TaskDate}")
    @ApiImplicitParam(name = "TaskDate",value = "根据指定TaskDate查询任务完成度",required = true,paramType = "body")
    public AjaxResult getTaskCompleted(String TaskDate) {
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
    @ApiOperation(value = "{根据指定TaskDate,指定的TaskCateId}")
    @ApiImplicitParam(name = "TaskDate",value = "根据指定TaskDate查询数据,指定的TaskCateId",required = true,paramType = "body")
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
