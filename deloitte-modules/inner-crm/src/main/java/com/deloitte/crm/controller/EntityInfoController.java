package com.deloitte.crm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.deloitte.crm.dto.EntityDto;
import com.deloitte.crm.dto.EntityInfoDto;

import com.deloitte.crm.dto.EntityInfoDto;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
     * @author 正杰
     * @date 2022/9/22
     * 新增【确定该主体是新增后,填写具体要新增主体的信息】
     * @param entityDto
     * @return
     */
    @RequiresPermissions("crm:entityInfo:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @ApiImplicitParam(name="entityInfoDto",value="包含表中entity_info所有字段以及 haveCreditCode oldName 额外两个字段",required = true,dataType = "body",dataTypeClass = EntityDto.class)
    @PostMapping("/insert")
    public AjaxResult add(@RequestBody EntityDto entityDto)
    {
        //TODO 新增主体
        return toAjax(entityInfoService.insertEntityInfo(entityDto));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:entityInfo:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
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
