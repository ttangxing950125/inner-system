package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.EntityMaster;
import com.deloitte.crm.service.IEntityMasterService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/entityMaster")
public class EntityMasterController extends BaseController
{
    @Autowired
    private IEntityMasterService entityMasterService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:entityMaster:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityMaster entityMaster)
    {
        startPage();
        List<EntityMaster> list = entityMasterService.selectEntityMasterList(entityMaster);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:entityMaster:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntityMaster entityMaster)
    {
        List<EntityMaster> list = entityMasterService.selectEntityMasterList(entityMaster);
        ExcelUtil<EntityMaster> util = new ExcelUtil<EntityMaster>(EntityMaster.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:entityMaster:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(entityMasterService.selectEntityMasterById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:entityMaster:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EntityMaster entityMaster)
    {
        return toAjax(entityMasterService.insertEntityMaster(entityMaster));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:entityMaster:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntityMaster entityMaster)
    {
        return toAjax(entityMasterService.updateEntityMaster(entityMaster));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:entityMaster:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(entityMasterService.deleteEntityMasterByIds(ids));
    }

    /**
     * 角色345修改行业划分
     *
     * @param entityMaster
     * @return void
     * @author 冉浩岑
     * @date 2022/10/12 9:51
     */
    @ApiOperation(value = "角色345修改行业划分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityCode", value = "主体编码", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "reportType", value = "报告类型", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "listType", value = "财报列示类型", paramType = "body", example = "", dataType = "String")
    })
    @PostMapping("/addEntityeMsg")
    public R addEntityeMasterMsg(@RequestBody EntityMaster entityMaster) {
        entityMasterService.addEntityeMasterMsg(entityMaster);
        return R.ok();
    }
}
