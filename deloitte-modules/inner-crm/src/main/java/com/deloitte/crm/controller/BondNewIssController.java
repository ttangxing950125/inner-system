package com.deloitte.crm.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.deloitte.crm.domain.BondNewIss;
import com.deloitte.crm.service.IBondNewIssService;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.page.TableDataInfo;

/**
 * 新债发行-新发行债券-20220801-20220914Controller
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/iss")
@Api(description = "新债发行，债券信息表")
public class BondNewIssController extends BaseController
{
    @Autowired
    private IBondNewIssService bondNewIssService;



    /**
     * 查询新债发行-新发行债券-20220801-20220914列表
     */
    @RequiresPermissions("crm:iss:list")
    @GetMapping("/list")
    @ApiOperation(value = "分页查询")
    public TableDataInfo list(BondNewIss bondNewIss)
    {
        QueryWrapper<BondNewIss> wrapper = new QueryWrapper<>();
        startPage();
        List<BondNewIss> list = bondNewIssService.selectBondNewIssList(bondNewIss);
        return getDataTable(list);
    }

    /**
     * 导出新债发行-新发行债券-20220801-20220914列表
     */
    @RequiresPermissions("crm:iss:export")
    @Log(title = "新债发行-新发行债券-20220801-20220914", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BondNewIss bondNewIss)
    {
        List<BondNewIss> list = bondNewIssService.selectBondNewIssList(bondNewIss);
        ExcelUtil<BondNewIss> util = new ExcelUtil<BondNewIss>(BondNewIss.class);
        util.exportExcel(response, list, "新债发行-新发行债券-20220801-20220914数据");
    }

    /**
     * 获取新债发行-新发行债券-20220801-20220914详细信息
     */
    @RequiresPermissions("crm:iss:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bondNewIssService.selectBondNewIssById(id));
    }

    /**
     * 新增新债发行-新发行债券-20220801-20220914
     */
    @RequiresPermissions("crm:iss:add")
    @Log(title = "新债发行-新发行债券-20220801-20220914", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BondNewIss bondNewIss)
    {
        return toAjax(bondNewIssService.insertBondNewIss(bondNewIss));
    }

    /**
     * 修改新债发行-新发行债券-20220801-20220914
     */
    @RequiresPermissions("crm:iss:edit")
    @Log(title = "新债发行-新发行债券-20220801-20220914", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BondNewIss bondNewIss)
    {
        return toAjax(bondNewIssService.updateBondNewIss(bondNewIss));
    }

    /**
     * 删除新债发行-新发行债券-20220801-20220914
     */
    @RequiresPermissions("crm:iss:remove")
    @Log(title = "新债发行-新发行债券-20220801-20220914", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bondNewIssService.deleteBondNewIssByIds(ids));
    }
}
