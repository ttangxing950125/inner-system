package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.EntityAttr;
import com.deloitte.crm.service.IEntityAttrService;
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
@RequestMapping("/attr")
public class EntityAttrController extends BaseController
{
    @Autowired
    private IEntityAttrService entityAttrService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:attr:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityAttr entityAttr)
    {
        startPage();
        List<EntityAttr> list = entityAttrService.selectEntityAttrList(entityAttr);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:attr:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntityAttr entityAttr)
    {
        List<EntityAttr> list = entityAttrService.selectEntityAttrList(entityAttr);
        ExcelUtil<EntityAttr> util = new ExcelUtil<EntityAttr>(EntityAttr.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:attr:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(entityAttrService.selectEntityAttrById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:attr:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EntityAttr entityAttr)
    {
        return toAjax(entityAttrService.insertEntityAttr(entityAttr));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:attr:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntityAttr entityAttr)
    {
        return toAjax(entityAttrService.updateEntityAttr(entityAttr));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:attr:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(entityAttrService.deleteEntityAttrByIds(ids));
    }


    /**
     * 分组查询全部---构造父子级关系
     *
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 10:56
    */
    @PostMapping("/getAllByGroup")
    public R getAllByGroup()
    {
        return entityAttrService.getAllByGroup();
    }
    /**
     * 根据dqCode查询详细信息
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/25 13:56
     */
    @PostMapping("/getAttrByDqCode")
    public R getAttrByDqCode(String dqCode)
    {
        return R.ok(entityAttrService.getAttrByDqCode(dqCode));
    }

}
