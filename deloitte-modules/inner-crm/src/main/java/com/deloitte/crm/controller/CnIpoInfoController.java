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
import com.deloitte.crm.domain.CnIpoInfo;
import com.deloitte.crm.service.ICnIpoInfoService;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.page.TableDataInfo;

/**
 * IPO-新股发行资料-20210914-20221014Controller
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/ipoInfo")
public class CnIpoInfoController extends BaseController
{
    @Autowired
    private ICnIpoInfoService cnIpoInfoService;

    /**
     * 查询IPO-新股发行资料-20210914-20221014列表
     */
    @RequiresPermissions("crm:ipoInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(CnIpoInfo cnIpoInfo)
    {
        startPage();
        List<CnIpoInfo> list = cnIpoInfoService.selectCnIpoInfoList(cnIpoInfo);
        return getDataTable(list);
    }

    /**
     * 导出IPO-新股发行资料-20210914-20221014列表
     */
    @RequiresPermissions("crm:ipoInfo:export")
    @Log(title = "IPO-新股发行资料-20210914-20221014", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CnIpoInfo cnIpoInfo)
    {
        List<CnIpoInfo> list = cnIpoInfoService.selectCnIpoInfoList(cnIpoInfo);
        ExcelUtil<CnIpoInfo> util = new ExcelUtil<CnIpoInfo>(CnIpoInfo.class);
        util.exportExcel(response, list, "IPO-新股发行资料-20210914-20221014数据");
    }

    /**
     * 获取IPO-新股发行资料-20210914-20221014详细信息
     */
    @RequiresPermissions("crm:ipoInfo:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cnIpoInfoService.selectCnIpoInfoById(id));
    }

    /**
     * 新增IPO-新股发行资料-20210914-20221014
     */
    @RequiresPermissions("crm:ipoInfo:add")
    @Log(title = "IPO-新股发行资料-20210914-20221014", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CnIpoInfo cnIpoInfo)
    {
        return toAjax(cnIpoInfoService.insertCnIpoInfo(cnIpoInfo));
    }

    /**
     * 修改IPO-新股发行资料-20210914-20221014
     */
    @RequiresPermissions("crm:ipoInfo:edit")
    @Log(title = "IPO-新股发行资料-20210914-20221014", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CnIpoInfo cnIpoInfo)
    {
        return toAjax(cnIpoInfoService.updateCnIpoInfo(cnIpoInfo));
    }

    /**
     * 删除IPO-新股发行资料-20210914-20221014
     */
    @RequiresPermissions("crm:ipoInfo:remove")
    @Log(title = "IPO-新股发行资料-20210914-20221014", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cnIpoInfoService.deleteCnIpoInfoByIds(ids));
    }
}
