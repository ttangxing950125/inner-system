package com.deloitte.crm.controller;

import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.service.IBondInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 【请填写功能名称】Controller
 * 
 * @author deloitte
 * @date 2022-09-23
 */
@RestController
@RequestMapping("/BondInfo")
public class BondInfoController extends BaseController
{
    @Autowired
    private IBondInfoService bondInfoService;


    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:BondInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(BondInfo bondInfo)
    {
        startPage();
        List<BondInfo> list = bondInfoService.selectBondInfoList(bondInfo);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:BondInfo:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BondInfo bondInfo)
    {
        List<BondInfo> list = bondInfoService.selectBondInfoList(bondInfo);
        ExcelUtil<BondInfo> util = new ExcelUtil<>(BondInfo.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:BondInfo:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bondInfoService.selectBondInfoById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:BondInfo:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BondInfo bondInfo)
    {
        return toAjax(bondInfoService.insertBondInfo(bondInfo));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:BondInfo:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BondInfo bondInfo)
    {
        return toAjax(bondInfoService.updateBondInfo(bondInfo));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:BondInfo:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bondInfoService.deleteBondInfoByIds(ids));
    }
}
