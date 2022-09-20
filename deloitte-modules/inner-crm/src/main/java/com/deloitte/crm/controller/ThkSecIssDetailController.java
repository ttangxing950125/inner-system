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
import com.deloitte.crm.domain.ThkSecIssDetail;
import com.deloitte.crm.service.IThkSecIssDetailService;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.page.TableDataInfo;

/**
 * 证券发行-股票发行-首次发行明细Controller
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/detail")
public class ThkSecIssDetailController extends BaseController
{
    @Autowired
    private IThkSecIssDetailService thkSecIssDetailService;

    /**
     * 查询证券发行-股票发行-首次发行明细列表
     */
    @RequiresPermissions("crm:detail:list")
    @GetMapping("/list")
    public TableDataInfo list(ThkSecIssDetail thkSecIssDetail)
    {
        startPage();
        List<ThkSecIssDetail> list = thkSecIssDetailService.selectThkSecIssDetailList(thkSecIssDetail);
        return getDataTable(list);
    }

    /**
     * 导出证券发行-股票发行-首次发行明细列表
     */
    @RequiresPermissions("crm:detail:export")
    @Log(title = "证券发行-股票发行-首次发行明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ThkSecIssDetail thkSecIssDetail)
    {
        List<ThkSecIssDetail> list = thkSecIssDetailService.selectThkSecIssDetailList(thkSecIssDetail);
        ExcelUtil<ThkSecIssDetail> util = new ExcelUtil<ThkSecIssDetail>(ThkSecIssDetail.class);
        util.exportExcel(response, list, "证券发行-股票发行-首次发行明细数据");
    }

    /**
     * 获取证券发行-股票发行-首次发行明细详细信息
     */
    @RequiresPermissions("crm:detail:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(thkSecIssDetailService.selectThkSecIssDetailById(id));
    }

    /**
     * 新增证券发行-股票发行-首次发行明细
     */
    @RequiresPermissions("crm:detail:add")
    @Log(title = "证券发行-股票发行-首次发行明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThkSecIssDetail thkSecIssDetail)
    {
        return toAjax(thkSecIssDetailService.insertThkSecIssDetail(thkSecIssDetail));
    }

    /**
     * 修改证券发行-股票发行-首次发行明细
     */
    @RequiresPermissions("crm:detail:edit")
    @Log(title = "证券发行-股票发行-首次发行明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThkSecIssDetail thkSecIssDetail)
    {
        return toAjax(thkSecIssDetailService.updateThkSecIssDetail(thkSecIssDetail));
    }

    /**
     * 删除证券发行-股票发行-首次发行明细
     */
    @RequiresPermissions("crm:detail:remove")
    @Log(title = "证券发行-股票发行-首次发行明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(thkSecIssDetailService.deleteThkSecIssDetailByIds(ids));
    }
}
