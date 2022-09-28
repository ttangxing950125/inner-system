package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.EntityAttr;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.service.IEntityAttrService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/attr")
public class EntityAttrController extends BaseController
{
    @Autowired
    private IEntityAttrService entityAttrService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:attr:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityAttr entityAttr)
    {
        startPage();
        List<EntityAttr> list = entityAttrService.selectEntityAttrList(entityAttr);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:attr:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntityAttr entityAttr)
    {
        List<EntityAttr> list = entityAttrService.selectEntityAttrList(entityAttr);
        ExcelUtil<EntityAttr> util = new ExcelUtil<EntityAttr>(EntityAttr.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:attr:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(entityAttrService.selectEntityAttrById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:attr:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EntityAttr entityAttr)
    {
        return toAjax(entityAttrService.insertEntityAttr(entityAttr));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:attr:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntityAttr entityAttr)
    {
        return toAjax(entityAttrService.updateEntityAttr(entityAttr));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:attr:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(entityAttrService.deleteEntityAttrByIds(ids));
    }


    /**
     * 分组查询全部---构造父子级关系
     *
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 10:56
    */
    @ApiOperation(value = "分组查询全部---构造父子级关系")
    @GetMapping("/getAllByGroup/{type}")
    public R getAllByGroup(@PathVariable("type") Integer type)
    {
        return entityAttrService.getAllByGroup(type);
    }
    /**
     * 根据dqCode查询详细信息
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/25 13:56
     */
    @ApiOperation(value = "根据dqCode查询详细信息")
    @ApiImplicitParam(name="dqCode", value="德勤唯一识别码", paramType = "query", example = "", dataType = "String")
    @PostMapping("/getAttrByDqCode")
    public R getAttrByDqCode(String dqCode)
    {
        return R.ok(entityAttrService.getAttrByDqCode(dqCode));
    }

    /**
     * 根据任务id查询补充信息
     *
     * @param entityCode
     * @return R
     * @author 冉浩岑
     * @date 2022/9/28 9:14
     */
    @ApiOperation(value="根据任务id查询补充信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name="entityCode",value="传入 entityCode",paramType = "query",dataType = "String"),
        @ApiImplicitParam(name="roleId",value="传入 roleId",paramType = "query",dataType = "Integer")
    })
    @PostMapping("/getTaskByEntityCode")
    public R getTaskByEntityCode(String entityCode, Integer roleId){
        return entityAttrService.getTaskByEntityCode(entityCode,roleId);
    }

    /**
     * 根据entityCode补充录入副表信息
     *
     * @param list
     * @return R
     * @author 冉浩岑
     * @date 2022/9/28 9:14
     */
    @ApiOperation(value="补充录入信息")
    @ApiImplicitParam(name="list",value="传入 EntityAttrValue属性值集合",paramType = "body",dataTypeClass = EntityAttrValue.class)
    @PostMapping("/saveAttrValueByCode")
    public R saveAttrValueByCode(List<EntityAttrValue>list){
        return entityAttrService.saveAttrValueByCode(list);
    }
    /**
     * 城投机构补充录入基础信息
     *
     * @param entityCode
     * @param govCode
     * @param preGovCode
     * @param govName
     * @param govLevelBig
     * @param govLevelSmall
     * @return R
     * @author 冉浩岑
     * @date 2022/9/28 10:29
    */
    @ApiOperation(value="补充录入信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="entityCode",value="德勤唯一识别码",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="govCode",value="政府主体官方行政代码",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="preGovCode",value="上级政府代码",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="govName",value="政府主体名称",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="govLevelBig",value="政府行政大类",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="govLevelSmall",value="政府行政小类",paramType = "query",dataType = "String"),
    })
    @PostMapping("/saveGovInfoByCode")
    public R saveGovInfoByCode(String entityCode,String govCode,String preGovCode,String govName,Integer govLevelBig,Integer govLevelSmall){
        return entityAttrService.saveGovInfoByCode(entityCode,govCode,preGovCode,govName,govLevelBig,govLevelSmall);
    }
    /**
     * 一般机构补充录入基础信息
     *
     * @param entityCode
     * @param govCode
     * @param preGovCode
     * @param govName
     * @param govLevelBig
     * @param govLevelSmall
     * @return R
     * @author 冉浩岑
     * @date 2022/9/28 10:29
     */
    @ApiOperation(value="补充录入信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="entityCode",value="德勤唯一识别码",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="creditCode",value="统一社会信用代码",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="entityName",value="企业名称",paramType = "query",dataType = "String")
    })
    @PostMapping("/saveEntityInfoByCode")
    public R saveEntityInfoByCode(String entityCode,String govCode,String preGovCode,String govName,Integer govLevelBig,Integer govLevelSmall){
        return null;
//        return entityAttrService.saveEntityInfoByCode(entityCode,govCode,preGovCode,govName,govLevelBig,govLevelSmall);
    }
}
