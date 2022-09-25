package com.deloitte.crm.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
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
import com.deloitte.crm.domain.CrmWindDict;
import com.deloitte.crm.service.ICrmWindDictService;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.page.TableDataInfo;

/**
 * 导入的wind文件分类Controller
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/dict")
@Api(tags = "wind文件数据详情相关接口")
public class CrmWindDictController extends BaseController
{
    @Autowired
    private ICrmWindDictService crmWindDictService;

    /**
     * 查询导入的wind文件分类列表
     */
    @RequiresPermissions("crm:dict:list")
    @GetMapping("/list")
    public TableDataInfo list(CrmWindDict crmWindDict)
    {
        startPage();
        List<CrmWindDict> list = crmWindDictService.selectCrmWindDictList(crmWindDict);
        return getDataTable(list);
    }

    /**
     * 导出导入的wind文件分类列表
     */
    @RequiresPermissions("crm:dict:export")
    @Log(title = "导入的wind文件分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrmWindDict crmWindDict)
    {
        List<CrmWindDict> list = crmWindDictService.selectCrmWindDictList(crmWindDict);
        ExcelUtil<CrmWindDict> util = new ExcelUtil<CrmWindDict>(CrmWindDict.class);
        util.exportExcel(response, list, "导入的wind文件分类数据");
    }

    /**
     * 获取导入的wind文件分类详细信息
     */
    @RequiresPermissions("crm:dict:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(crmWindDictService.selectCrmWindDictById(id));
    }

    /**
     * 新增导入的wind文件分类
     */
    @RequiresPermissions("crm:dict:add")
    @Log(title = "导入的wind文件分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrmWindDict crmWindDict)
    {
        return toAjax(crmWindDictService.insertCrmWindDict(crmWindDict));
    }

    /**
     * 修改导入的wind文件分类
     */
    @RequiresPermissions("crm:dict:edit")
    @Log(title = "导入的wind文件分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrmWindDict crmWindDict)
    {
        return toAjax(crmWindDictService.updateCrmWindDict(crmWindDict));
    }

    /**
     * 删除导入的wind文件分类
     */
    @RequiresPermissions("crm:dict:remove")
    @Log(title = "导入的wind文件分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(crmWindDictService.deleteCrmWindDictByIds(ids));
    }
}
