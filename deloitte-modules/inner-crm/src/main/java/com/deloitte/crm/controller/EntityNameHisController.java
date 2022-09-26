package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.EntityNameHis;
import com.deloitte.crm.service.IEntityNameHisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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
@Api(tags="根据德勤code查询曾用名列表")
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
    @ApiOperation(value = "根据德勤code查询曾用名列表")
    @ApiImplicitParam(name="dqCode", value="德勤自动生成得唯一识别码", paramType = "query", example = "1", dataType = "String")
    @PostMapping("/getNameListByDqCoded")
    public R getNameListByDqCoded(String dqCode)
    {
        return R.ok(entityNameHisService.getNameListByDqCoded(dqCode));
    }
    /**
     * 查询政府主体曾用名列表
     *
     * @param param
     * @return R
     * @author 冉浩岑
     * @date 2022/9/26 11:16
     */
    @ApiOperation(value = "查询政府曾用名列表")
    @ApiImplicitParam(name="param", value="模糊查询得条件，匹配政府主体名称，政府主体代码，政府主体行政编码", paramType = "query", example = "Gv", dataType = "String")
    @PostMapping("/getGovHisNameList")
    public R getGovHisNameList(String param){
        if (ObjectUtils.isEmpty(param)){
            return R.fail("参数不能为空");
        }
        return R.ok(entityNameHisService.getGovHisNameList(param));
    }
    /**
     * 查询企业主体曾用名列表
     *
     * @param param
     * @return R
     * @author 冉浩岑
     * @date 2022/9/26 11:16
     */
    @ApiOperation(value = "查询企业主体曾用名列表")
    @ApiImplicitParam(
            name="param",
            value="模糊查询得条件，匹配企业主体名称，企业主体代码，企业主体社会统一识别码",
            paramType = "query",
            example = "te",
            dataType = "String")
    @PostMapping("/getEntityHisNameList")
    public R getEntityHisNameList(String param){
        if (ObjectUtils.isEmpty(param)){
            return R.fail("参数不能为空");
        }
        return R.ok(entityNameHisService.getEntityHisNameList(param));
    }
}
