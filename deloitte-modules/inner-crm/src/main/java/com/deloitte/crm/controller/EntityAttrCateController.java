package com.deloitte.crm.controller;

import java.util.List;
import java.io.IOException;
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
import com.deloitte.crm.domain.EntityAttrCate;
import com.deloitte.crm.service.IEntityAttrCateService;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.page.TableDataInfo;

/**
 * 企业属性分类Controller
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/cate")
public class EntityAttrCateController extends BaseController
{
    @Autowired
    private IEntityAttrCateService entityAttrCateService;

    /**
     * 查询企业属性分类列表
     */
    @RequiresPermissions("crm:cate:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityAttrCate entityAttrCate)
    {
        startPage();
        List<EntityAttrCate> list = entityAttrCateService.selectEntityAttrCateList(entityAttrCate);
        return getDataTable(list);
    }

    /**
     * 导出企业属性分类列表
     */
    @RequiresPermissions("crm:cate:export")
    @Log(title = "企业属性分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntityAttrCate entityAttrCate)
    {
        List<EntityAttrCate> list = entityAttrCateService.selectEntityAttrCateList(entityAttrCate);
        ExcelUtil<EntityAttrCate> util = new ExcelUtil<EntityAttrCate>(EntityAttrCate.class);
        util.exportExcel(response, list, "企业属性分类数据");
    }

    /**
     * 获取企业属性分类详细信息
     */
    @RequiresPermissions("crm:cate:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(entityAttrCateService.selectEntityAttrCateById(id));
    }

    /**
     * 新增企业属性分类
     */
    @RequiresPermissions("crm:cate:add")
    @Log(title = "企业属性分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EntityAttrCate entityAttrCate)
    {
        return toAjax(entityAttrCateService.insertEntityAttrCate(entityAttrCate));
    }

    /**
     * 修改企业属性分类
     */
    @RequiresPermissions("crm:cate:edit")
    @Log(title = "企业属性分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntityAttrCate entityAttrCate)
    {
        return toAjax(entityAttrCateService.updateEntityAttrCate(entityAttrCate));
    }

    /**
     * 删除企业属性分类
     */
    @RequiresPermissions("crm:cate:remove")
    @Log(title = "企业属性分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(entityAttrCateService.deleteEntityAttrCateByIds(ids));
    }
}
