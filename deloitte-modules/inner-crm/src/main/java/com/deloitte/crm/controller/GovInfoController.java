package com.deloitte.crm.controller;

import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.domain.dto.GovInfoDto;
import com.deloitte.crm.service.IGovInfoService;
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
@RequestMapping("/govInfo")
public class GovInfoController extends BaseController
{
    @Autowired
    private IGovInfoService govInfoService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:govInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(GovInfo govInfo)
    {
        startPage();
        List<GovInfo> list = govInfoService.selectGovInfoList(govInfo);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:govInfo:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GovInfo govInfo)
    {
        List<GovInfo> list = govInfoService.selectGovInfoList(govInfo);
        ExcelUtil<GovInfo> util = new ExcelUtil<GovInfo>(GovInfo.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:govInfo:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(govInfoService.selectGovInfoById(id));
    }

    /**
     * 新增地方政府
     */
    @RequiresPermissions("crm:govInfo:add")
    @Log(title = "【新增地方政府】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody GovInfo govInfo)
    {
        return toAjax(govInfoService.insertGovInfo(govInfo));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:govInfo:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GovInfo govInfo)
    {
        return toAjax(govInfoService.updateGovInfo(govInfo));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:govInfo:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(govInfoService.deleteGovInfoByIds(ids));
    }

    /**
     * 批量修改
     *
     * @param govInfoList
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 15:24
    */
    @PostMapping("/updateInfoList")
    public AjaxResult updateInfoList(List<GovInfo>govInfoList)
    {
        return AjaxResult.success(govInfoService.updateInfoList(govInfoList));
    }
    /**
     * 查询政府名称，或者编码，是否重复
     *
     * @param govInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 17:49
    */
    @PostMapping("/checkList")
    public AjaxResult checkList(@RequestBody GovInfo govInfo)
    {
        return AjaxResult.success(govInfoService.checkList(govInfo));
    }
    /**
     * 分页查询
     *
     * @param govInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @PostMapping("/getInfoList")
    public AjaxResult getInfoList(@RequestBody GovInfoDto govInfo)
    {
        return govInfoService.getInfoList(govInfo);
    }
    /**
     * 修改曾用名
     *
     * @param govInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 8:44
    */
    @PostMapping("/updateOldName")
    public AjaxResult updateOldName(GovInfo govInfo)
    {
        return govInfoService.updateOldName(govInfo);
    }
    /**
     * 根据 dqCode 查询政府主体
     *
     * @param govInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 8:59
    */
    @PostMapping("/getInfoDetail")
    public AjaxResult getInfoDetail(GovInfo govInfo){
        return govInfoService.getNewInfo(govInfo);
    }
}
