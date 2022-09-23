package com.deloitte.crm.controller;
import java.util.List;
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
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.service.IGovInfoService;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.page.TableDataInfo;

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
   * 统计政府信息
   *
   * @return GovInfoDto
   * @author penTang
   * @date 2022/9/22 23:21
   *
  */
  @PostMapping("/govList")
    public AjaxResult getGovInfo(){
        return AjaxResult.success("查询成功",govInfoService.getGovInfo());

    }

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
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:govInfo:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
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
}
