package com.deloitte.crm.controller;

import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.EntityInfoByDto;
import com.deloitte.crm.service.IEntityInfoService;
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
@RequestMapping("/entityInfo")
public class EntityInfoController extends BaseController
{
    @Autowired
    private IEntityInfoService entityInfoService;

  /**
   *统计整体企业主体情况
   *
   * @return AjaxResult
   * @author penTang
   * @date 2022/9/22 22:41
  */
    @PostMapping("/entityInfoList")
    public AjaxResult getList(){
        return AjaxResult.success("查询成功",entityInfoService.getEntityInfo());
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:entityInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityInfo entityInfo)
    {
        startPage();
        List<EntityInfo> list = entityInfoService.selectEntityInfoList(entityInfo);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:entityInfo:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntityInfo entityInfo)
    {
        List<EntityInfo> list = entityInfoService.selectEntityInfoList(entityInfo);
        ExcelUtil<EntityInfo> util = new ExcelUtil<EntityInfo>(EntityInfo.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:entityInfo:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(entityInfoService.selectEntityInfoById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:entityInfo:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody EntityInfo entityInfo)
    {
        return toAjax(entityInfoService.insertEntityInfo(entityInfo));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:entityInfo:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntityInfo entityInfo)
    {
        return toAjax(entityInfoService.updateEntityInfo(entityInfo));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:entityInfo:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(entityInfoService.deleteEntityInfoByIds(ids));
    }

    /**
     * 分页查询
     *
     * @param entityInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @PostMapping("/getInfoList")
    public AjaxResult getInfoList(@RequestBody EntityInfoByDto entityInfo)
    {
        return entityInfoService.getInfoList(entityInfo);
    }
    /**
     * 查询企业全程，或者编码，是否重复
     *
     * @param entityInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @PostMapping("/checkList")
    public AjaxResult checkList(@RequestBody EntityInfo entityInfo)
    {
        return AjaxResult.success(entityInfoService.checkList(entityInfo));
    }
    /**
     * 批量修改
     *
     * @param entityInfoList
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 15:24
     */
    @PostMapping("/updateInfoList")
    public AjaxResult updateInfoList(List<EntityInfo>entityInfoList)
    {
        return AjaxResult.success(entityInfoService.updateInfoList(entityInfoList));
    }
    /**
     * 根据 entityCode 查询企业主体详情
     *
     * @param  entityCode
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 8:59
     */
    @PostMapping("/getInfoDetail")
    public AjaxResult getInfoDetail(String entityCode){
        return entityInfoService.getOneAllInfo(entityCode);
    }
    /**
     * 分页查询全部上市主体
     *
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 10:56
     */
    @PostMapping("/getListEntityByPage")
    public AjaxResult getListEntityByPage(EntityAttrByDto entityAttrDto)
    {
        return entityInfoService.getListEntityByPage(entityAttrDto);
    }
}
