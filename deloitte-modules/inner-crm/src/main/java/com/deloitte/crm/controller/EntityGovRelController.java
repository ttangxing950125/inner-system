package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.EntityGovRel;
import com.deloitte.crm.domain.dto.EntityGovRelDto;
import com.deloitte.crm.service.IEntityGovRelService;
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
@RequestMapping("/entityGovRel")

public class EntityGovRelController extends BaseController
{
    @Autowired
    private IEntityGovRelService entityGovRelService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:entityGovRel:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityGovRel entityGovRel)
    {
        startPage();
        List<EntityGovRel> list = entityGovRelService.selectEntityGovRelList(entityGovRel);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:entityGovRel:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntityGovRel entityGovRel)
    {
        List<EntityGovRel> list = entityGovRelService.selectEntityGovRelList(entityGovRel);
        ExcelUtil<EntityGovRel> util = new ExcelUtil<EntityGovRel>(EntityGovRel.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:entityGovRel:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(entityGovRelService.selectEntityGovRelById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:entityGovRel:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EntityGovRel entityGovRel)
    {
        return toAjax(entityGovRelService.insertEntityGovRel(entityGovRel));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:entityGovRel:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntityGovRel entityGovRel)
    {
        return toAjax(entityGovRelService.updateEntityGovRel(entityGovRel));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:entityGovRel:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(entityGovRelService.deleteEntityGovRelByIds(ids));
    }

    /**
     * 根据 dqCode 查询城投主体数量
     *
     * @param dqCode
     * @return R
     * @author 冉浩岑
     * @date 2022/9/23 8:59
     */
    @ApiOperation(value = "根据 dqCode 查询城投主体数量")
    @ApiImplicitParam(name = "dqCode", value = "包含表中gov_info的所有字段", paramType = "body", example = "", dataType = "String")
    @PostMapping("/getEntityGovCount")
    public R getEntityGovCount(String dqCode) {
        return R.ok(entityGovRelService.getEntityGovCount(dqCode));
    }


    /**
     * 城投机构根据entityCode补充录入副表信息
     *
     * @param entityGovRel
     * @return R
     * @author 冉浩岑
     * @date 2022/10/12 9:56
    */
    @ApiOperation(value = "城投机构根据entityCode补充录入副表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityCode", value = "主体编码", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "shareMethod", value = "持股方式", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "support", value = "支持力度", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "judgment", value = "支持力度判断依据", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "shareRatio", value = "持股比例", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "shareRatioYear", value = "持股比例年份", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "remarks", value = "备注", paramType = "body", example = "", dataType = "String")
    })
    @PostMapping("/addGovEntitySubtableMsg")
    public R addGovEntitySubtableMsg(@RequestBody EntityGovRelDto entityGovRel) {
        entityGovRelService.addGovEntitySubtableMsg(entityGovRel);
        return R.ok();
    }
}
