package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.GovInfoByDto;
import com.deloitte.crm.dto.GovInfoDto;
import com.deloitte.crm.service.IGovInfoService;
import io.swagger.annotations.Api;
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
@RequestMapping("/govInfo")
@Api(tags="政府主体查询修改相关数据")

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
    @ApiOperation(value = "{统计政府信息}", response= GovInfoDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query"

            )
    })
    public R getGovInfo(){
        return R.ok(govInfoService.getGovInfo());

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
     * 政府主体批量修改
     * @param govInfoList
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 15:24
    */
    @ApiOperation(value = "政府主题批量修改")
    @ApiImplicitParam(
            // 参数名
            name="govInfoList",
            // 参数描述
            value="包含表中egov_info的所有字段",
            // 参数出现的地方 query-表单数据,body-applicationJson,path-路径
            paramType = "body",
            // 示例值
            example = "")
    @PostMapping("/updateInfoList")
    public R updateInfoList(List<GovInfo>govInfoList)
    {
        return R.ok(govInfoService.updateInfoList(govInfoList));
    }
    /**
     * 查询政府名称，或者编码，是否重复
     * @param govInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 17:49
    */
    @ApiOperation(value = "查询政府名称，或者编码，是否重复")
    @ApiImplicitParam(
            // 参数名
            name="govInfo",
            // 参数描述
            value="包含表中egov_info的所有字段",
            // 参数出现的地方 query-表单数据,body-applicationJson,path-路径
            paramType = "body",
            // 示例值
            example = "")
    @PostMapping("/checkGov")
    public R checkGov(@RequestBody GovInfo govInfo)
    {
        return R.ok(govInfoService.checkGov(govInfo));
    }
    /**
     * 政府主体分页查询
     * @param govInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "政府主体分页查询")
    @ApiImplicitParam(
            // 参数名
            name="govInfo",
            // 参数描述
            value="包含表中gov_info的所有字段",
            // 参数出现的地方 query-表单数据,body-applicationJson,path-路径
            paramType = "body",
            // 示例值
            example = "")
    @PostMapping("/getInfoList")
    public R getInfoList(@RequestBody GovInfoByDto govInfo)
    {
        return govInfoService.getInfoList(govInfo);
    }
    /**
     * 新增政府主体的曾用名
     * @param govInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 8:44
    */
    @ApiOperation(value = "新增政府主体的曾用名")
    @ApiImplicitParam(
            // 参数名
            name="govInfo",
            // 参数描述
            value="包含表中gov_info的所有字段",
            // 参数出现的地方 query-表单数据,body-applicationJson,path-路径
            paramType = "body",
            // 示例值
            example = "")
    @PostMapping("/addOldName")
    public R addOldName(@RequestBody GovInfo govInfo)
    {
        return govInfoService.addOldName(govInfo);
    }
  /**
   * 修改政府主体的曾用名
   *
   * @param dqCode
   * @param oldName
   * @param newOldName
   * @param status
   * @return AjaxResult
   * @author 冉浩岑
   * @date 2022/9/25 13:22
  */
  @ApiOperation(value = "修改政府主体的曾用名")
  @ApiImplicitParams({
          @ApiImplicitParam(
                  // 参数名
                  name = "dqCode",
                  // 参数描述
                  value = "德勤统一识别码",
                  // 参数出现的地方 query-表单数据,body-applicationJson,path-路径
                  paramType = "query",
                  // 示例值
                  example = "1"),
          @ApiImplicitParam(
                  // 参数名
                  name = "oldName",
                  // 参数描述
                  value = "原本的曾用名",
                  // 参数出现的地方 query-表单数据,body-applicationJson,path-路径
                  paramType = "query",
                  // 示例值
                  example = "原始曾用名"),
          @ApiImplicitParam(
                  // 参数名
                  name = "newOldName",
                  // 参数描述
                  value = "修改后的曾用名",
                  // 参数出现的地方 query-表单数据,body-applicationJson,path-路径
                  paramType = "query",
                  // 示例值
                  example = "新的曾用名"),
          @ApiImplicitParam(
                  // 参数名
                  name = "status",
                  // 参数描述
                  value = "是否停用曾用名",
                  // 参数出现的地方 query-表单数据,body-applicationJson,path-路径
                  paramType = "query",
                  // 示例值
                  example = "新的曾用名")
  })
  @PostMapping("/updateOldName")
  public R updateOldName(String dqCode,String oldName, String newOldName,String status)
  {
    return govInfoService.updateOldName(dqCode,oldName,newOldName,status);
  }
    /**
     * 根据 dqCode 查询政府主体
     * @param govInfo
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 8:59
    */
    @ApiOperation(value = "根据 dqCode 查询政府主体")
    @ApiImplicitParam(
            // 参数名
            name="govInfo",
            // 参数描述
            value="包含表中gov_info的所有字段",
            // 参数出现的地方 query-表单数据,body-applicationJson,path-路径
            paramType = "body",
            // 示例值
            example = "")
    @PostMapping("/getInfoDetail")
    public R getInfoDetail(@RequestBody GovInfo govInfo){
        return govInfoService.getInfoDetail(govInfo);
    }
    /**
     * 分页查询全部上市主体
     * @return AjaxResult
     * @author 冉浩岑
     * @date 2022/9/23 10:56
     */
    @ApiOperation(value = "分页查询全部上市主体")
    @ApiImplicitParam(
            // 参数名
            name="entityAttrDto",
            // 参数描述
            value="包含表中gov_info的所有字段和分页参数 pageSize pageNum",
            // 参数出现的地方 query-表单数据,body-applicationJson,path-路径
            paramType = "body",
            // 示例值
            example = "")
    @PostMapping("/getListEntityByPage")
    public R getListEntityByPage(@RequestBody EntityAttrByDto govAttrDto)
    {
        return govInfoService.getListEntityByPage(govAttrDto);
    }

}
