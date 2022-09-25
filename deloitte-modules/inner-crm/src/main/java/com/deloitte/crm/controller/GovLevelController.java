package com.deloitte.crm.controller;

import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.GovLevel;
import com.deloitte.crm.service.IGovLevelService;
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
@RequestMapping("/level")
public class GovLevelController extends BaseController {
    @Autowired
    private IGovLevelService govLevelService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:level:list")
    @GetMapping("/list")
    public TableDataInfo list(GovLevel govLevel) {
        startPage();
        List<GovLevel> list = govLevelService.selectGovLevelList(govLevel);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:level:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GovLevel govLevel) {
        List<GovLevel> list = govLevelService.selectGovLevelList(govLevel);
        ExcelUtil<GovLevel> util = new ExcelUtil<GovLevel>(GovLevel.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:level:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(govLevelService.selectGovLevelById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:level:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GovLevel govLevel) {
        return toAjax(govLevelService.insertGovLevel(govLevel));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:level:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GovLevel govLevel) {
        return toAjax(govLevelService.updateGovLevel(govLevel));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:level:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(govLevelService.deleteGovLevelByIds(ids));
    }
}
