package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.StockCnInfo;
import com.deloitte.crm.domain.StockThkInfo;
import com.deloitte.crm.domain.dto.EntityAttrByDto;
import com.deloitte.crm.domain.dto.EntityInfoDetails;
import com.deloitte.crm.dto.*;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.service.IEntityNameHisService;
import com.deloitte.crm.vo.EntityInfoHisNameVo;
import com.deloitte.crm.vo.EntitySupplyMsgBack;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@Api(tags = "企业主体查询修改相关数据")
public class EntityInfoController extends BaseController {
    @Autowired
    private IEntityInfoService entityInfoService;
    @Autowired
    private IEntityNameHisService entityNameHisService;
    /**
     * 统计整体企业主体情况
     *
     * @return R
     * @author penTang
     * @date 2022/9/22 22:41
     */
    @PostMapping("/entityInfoList")
    @ApiOperation(value = "{统计整体企业主体情况}", response = EntityInfoDto.class)
    public R getList() {
        return R.ok(entityInfoService.getEntityInfo());
    }

    /**
     * 导出整体企业主体情况
     *
     * @return R
     * @author penTang
     * @date 2022/9/22 22:41
     */

    @ApiOperation(value = "{导出整体企业主体情况}", response = EntityInfoDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "entityAttrDto", value = "包含表中gov_info的所有字段")
    })
    @PostMapping("/exportEntity")
    public R ImporEntityInfo(  @RequestBody EntityAttrByDto entityAttrDto) {
        entityInfoService.ExportEntityInFor(entityAttrDto);
        return R.ok();
    }


    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:entityInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityInfo entityInfo) {
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
    public void export(HttpServletResponse response, EntityInfo entityInfo) {
        List<EntityInfo> list = entityInfoService.selectEntityInfoList(entityInfo);
        ExcelUtil<EntityInfo> util = new ExcelUtil<EntityInfo>(EntityInfo.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:entityInfo:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(entityInfoService.selectEntityInfoById(id));
    }

    /**
     * @param entityDto
     * @return
     * @author 正杰
     * @date 2022/9/22
     * 新增【确定该主体是新增后,填写具体要新增主体的信息】
     */
    @RequiresPermissions("crm:entityInfo:add")
    @Log(title = "【确定该主体是新增后,填写具体要新增主体的信息】", businessType = BusinessType.INSERT)
    @ApiImplicitParam(name = "entityInfoDto", value = "包含表中entity_info所有字段以及 haveCreditCode oldName 额外两个字段", paramType = "body")
    @PostMapping("/add")
    public AjaxResult addEntity(@RequestBody EntityDto entityDto) {
        //新增主体
        //entityInfoService.insertEntityInfo(entityDto);
        return toAjax(true);
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:entityInfo:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody EntityInfo entityInfo) {
        return toAjax(entityInfoService.updateEntityInfo(entityInfo));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:entityInfo:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(entityInfoService.deleteEntityInfoByIds(ids));
    }

    /**
     * 企业主体批量修改
     *
     * @param entityInfoList
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 15:24
     */
    @ApiOperation(value = "企业主体批量修改")
    @ApiImplicitParam(name = "entityInfoList", value = "包含表中entity_info的所有字段", paramType = "body", example = "", dataTypeClass = EntityInfo.class)
    @PostMapping("/updateInfoList")
    public R updateInfoList(List<EntityInfo> entityInfoList) {
        return R.ok(entityInfoService.updateInfoList(entityInfoList));
    }

    /**
     * 查询企业名称，或者编码，是否重复
     *
     * @param entityInfo
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "查询企业名称，或者编码，是否重复")
    @ApiImplicitParam(name = "entityInfo", value = "包含表中eentity_info的所有字段", paramType = "body", example = "", dataTypeClass = EntityInfo.class)
    @PostMapping("/checkEntity")
    public R checkEntity(@RequestBody EntityInfo entityInfo) {
        return R.ok(entityInfoService.checkEntity(entityInfo));
    }

    /**
     * 企业主体分页查询
     *
     * @param type
     * @param param
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "企业主体分类查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "企业主体类型 1、上市 2、发债 3、非上市，非发债 4、金融机构", paramType = "query", example = "", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", example = "", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数", paramType = "query", example = "", dataType = "Integer"),
            @ApiImplicitParam(name = "param", value = "param 筛选条件", paramType = "query", example = "", dataType = "String")
    })
    @PostMapping("/getInfoList")
    public R getInfoList(Integer type, String param, Integer pageNum,  Integer pageSize) {
        return entityInfoService.getInfoList(type, param, pageNum, pageSize);
    }

    /**
     * 新增企业主体的曾用名
     *
     * @param entityInfo
     * @return R
     * @author 冉浩岑
     * @date 2022/9/23 8:44
     */
    @ApiOperation(value = "新增企业主体的曾用名")
    @ApiImplicitParam(name = "entityInfo", value = "包含表中entity_info的所有字段", paramType = "body", example = "", dataTypeClass = EntityInfo.class)
    @PostMapping("/updateEntityName")
    public R updateEntityName(@RequestBody EntityInfo entityInfo) {
        return entityInfoService.addOldName(entityInfo);
    }

    /**
     *添加方法描述
     *
     * @param entityInfo
     * @return R
     * @author 冉浩岑
     * @date 2022/10/28 18:36
    */
    @ApiOperation(value = "新增企业主体的曾用名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityCode", value = "对应的主体code，必传", paramType = "body", example = "IB000001", dataType = "String"),
            @ApiImplicitParam(name = "entityName", value = "对应的主体名称，必传", paramType = "body", example = "哈尔滨哈投投资股份有限公司", dataType = "String"),
            @ApiImplicitParam(name = "updated", value = "修改的时间，没选传空，必传", paramType = "body", example = "2022-10-11", dataType = "String"),
            @ApiImplicitParam(name = "entityNameHisRemarks", value = "新增的备注，没有传空，必传", paramType = "body", example = "新的备注", dataType = "String")
    })
    @PostMapping("/addOldName")
    public R addOldName(@RequestBody EntityInfoHisNameVo entityInfo) {
        return entityNameHisService.addEntityNameHis(entityInfo.getEntityCode(),entityInfo.getEntityName(),entityInfo.getUpdated(),entityInfo.getEntityNameHisRemarks());
    }
    /**
     * 修改,停用企业主体的曾用名
     *
     * @param dqCode
     * @param oldName
     * @param newOldName
     * @param status
     * @return R
     * @author 冉浩岑a
     * @date 2022/9/25 13:22
     */
    @ApiOperation(value = "修改,停用企业主体的曾用名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dqCode", value = "德勤统一识别码,必传", paramType = "query", example = "1", dataType = "String"),
            @ApiImplicitParam(name = "oldName", value = "原本的曾用名,必传", paramType = "query", example = "原始曾用名", dataType = "String"),
            @ApiImplicitParam(name = "newOldName", value = "修改后的曾用名,必传，停用串-", paramType = "query", example = "新的曾用名", dataType = "String"),
            @ApiImplicitParam(name = "remarks", value = "修改后的曾用名备注,必传", paramType = "query", example = "修改后的曾用名备注", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "是否停用曾用名,必传，停用传1，修改传空串", paramType = "query", example = "是否停用曾用名", dataType = "String")
    })
    @PostMapping("/updateOldName")
    public R updateOldName(String dqCode, String oldName, String newOldName, String status,String remarks) {
        return entityInfoService.updateOldName(dqCode, oldName, newOldName, status,remarks);
    }

    /**
     * 上市企业-修改信息-根据 entityCode 查询主体详细信息
     *
     * @param entityCode
     * @return R
     * @author 冉浩岑
     * @date 2022/9/23 8:59
     */
    @ApiOperation(value = "上市企业-修改信息-根据 entityCode 查询主体详细信息")
    @ApiImplicitParam(name = "entityCode", value = "主体德勤唯一识别码", paramType = "query", example = "", dataType = "String")
    @PostMapping("/getInfoDetail")
    public R getInfoDetailByEntityCode(String entityCode) {

        return entityInfoService.getInfoDetailByEntityCode(entityCode);
    }

    /**
     * 上市企业-修改信息
     *
     * @param entityInfoDetails
     * @return R
     * @author 冉浩岑
     * @date 2022/9/23 8:59
     */
    @ApiOperation(value = "上市企业-修改信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityInfo", value = "主体基本信息", paramType = "body", example = "", dataTypeClass = EntityInfo.class),
            @ApiImplicitParam(name = "stockCnInfo", value = "主体A股上市详情", paramType = "body", example = "", dataTypeClass = StockCnInfo.class),
            @ApiImplicitParam(name = "stockThkInfo", value = "主体港股上市详情", paramType = "body", example = "", dataTypeClass = StockThkInfo.class)
    })
    @PostMapping("/updateInfoDetail")
    public R updateInfoDetail(@RequestBody EntityInfoDetails entityInfoDetails) {
        entityInfoService.updateInfoDetail(entityInfoDetails);
        return R.ok();
    }

    /**
     * 企业主体-更多指标
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/23 10:56
     */
    @ApiOperation(value = "企业主体-更多指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mapList", value = "更多指标添加指标项", paramType = "body", example = "", dataTypeClass = MoreIndex.class),
            @ApiImplicitParam(name = "pageSize", value = "页面size", paramType = "body", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "body", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "publicType", value = "公募债券", paramType = "body", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "privateType", value = "私募债券", paramType = "body", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "abs", value = "ABS", paramType = "body", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "coll", value = "集合债券", paramType = "body", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "stockThk", value = "港股", paramType = "body", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "stockCn", value = "内地股", paramType = "body", example = "1", dataType = "Integer")
    })
    @PostMapping("/getListEntityByPage")
    public R getListEntityByPage(@RequestBody EntityAttrByDto entityAttrDto) {
        return R.ok(entityInfoService.getListEntityByPage(entityAttrDto));
    }

    /**
     * 企业主体分类概览
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "企业主体分类概览")
    @PostMapping("/getOverviewByGroup")
    public R getOverviewByGroup() {
        return R.ok(entityInfoService.getOverviewByGroup());
    }


    /**
     * 主体整体概览
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "主体整体概览")
    @PostMapping("/getOverviewByAll")
    public R getOverviewByAll() {
        return R.ok(entityInfoService.getOverviewByAll());
    }

    /**
     * 快速查询企业上市或发债情况
     *
     * @param param
     * @param pageNum
     * @param pageSize
     * @return R
     * @author 冉浩岑
     * @date 2022/10/8 15:53
     */
    @ApiOperation(value = "快速查询企业上市或发债情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "搜索条件", paramType = "query", example = "", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", example = "", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页面size", paramType = "query", example = "", dataType = "Integer")
    })
    @PostMapping("/getQuickOfCoverage")
    public R getQuickOfCoverage(String param, Integer pageNum, Integer pageSize) {
        return entityInfoService.getQuickOfCoverage(param, pageNum, pageSize);
    }

    /**
     * 批量查询并导出excel结果
     *
     * @param file
     * @param importDto
     * @return R
     * @author penTang
     * @date 2022/10/9 15:57
     */
    @ApiOperation(value = "批量查询并导出excel结果")
    @PostMapping("/importExcelByEntity")
    public R importExcelByEntity(@RequestParam("file") MultipartFile file, ImportDto importDto) {
        List<ExportEntityCheckDto> exportEntityCheckDtos = entityInfoService.checkBatchEntity(file, importDto);
        R excelWriter = entityInfoService.getExcelWriter(exportEntityCheckDtos, importDto);
        return excelWriter;
    }


    /**
     * 查询匹配进度
     *
     * @return R
     * @author penTang
     * @date 2022/10/10 20:33
     */
    @ApiOperation(value = "查询匹配进度")
    @PostMapping("/getChecking")
    public R getChecking(String uuid) {
        return entityInfoService.getIng(uuid);
    }


    /**
     * 财报收数根据entityCode补充录入信息--主表
     *
     * @param entitySupplyMsgBack
     * @return R
     * @author 冉浩岑
     * @date 2022/10/12 9:51
     */
    @ApiOperation(value = "财报收数根据entityCode补充录入信息--主表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityCode", value = "主体编码", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "reportType", value = "关注报告类型", paramType = "body", example = "", dataTypeClass = List.class),
            @ApiImplicitParam(name = "listType", value = "财报类型", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "windMaster", value = "wind行业划分", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "shenWanMaster", value = "wind行业划分", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "任务Id", paramType = "body", example = "", dataType = "Integer")
    })
    @PostMapping("/addEntityeMsg")
    public R addEntityeMsg(@RequestBody EntitySupplyMsgBack entitySupplyMsgBack) {
        return entityInfoService.addEntityeMsg(entitySupplyMsgBack);
    }

    /**
     * 根据名称查询主体的相关信息(产品客户划分情况)
     *
     * @return R
     * @author penTang
     * @date 2022/10/17 9:53
     */
    @ApiOperation(value = "敞口划分根据主体名称查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "主体名称", paramType = "body", example = "", dataTypeClass = EntityAttrByDto.class)
    })
    @PostMapping("/entityMaster")
    public R selectEntityInfoByName(@RequestParam("name") String name) {
        return R.ok(entityInfoService.selectEntityInfoListByName(name));

    }

    /**
     * 根据名code
     * 查询主体信息
     *
     * @return R
     * @author penTang
     * @date 2022/10/17 9:53
     */
    @ApiOperation(value = "根据主体code查询主体信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", paramType = "body", example = "", dataTypeClass = EntityAttrByDto.class)
    })
    @PostMapping("/entityByMaster")
    public R selectEntityInfoByCode(@RequestParam("code") String code) {
        return R.ok(entityInfoService.selectEntityDto(code));
    }

    /**
     * 企业主体清单-查询概览
     *
     * @param
     * @return R
     * @author 冉浩岑
     * @date 2022/10/17 8:49
     */
    @ApiOperation(value = "企业主体清单-查询概览")
    @ApiImplicitParam(name = "type", value = "查询类型", paramType = "query", example = "1", dataType = "Integer")
    @PostMapping("/getListView")
    public R getListView(Integer type) {
        return R.ok(entityInfoService.getListView(type));
    }

    /**
     * 角色3，4，5补充录入查询和回显
     *
     * @param id
     * @return R
     * @author 冉浩岑
     * @date 2022/10/24 11:13
     */
    @ApiOperation(value = "角色3，4，5补充录入查询和回显")
    @ApiImplicitParam(name = "id", value = "任务id", paramType = "query", example = "24", dataType = "Long")
    @PostMapping("/getEntityBackSupply")
    public R<EntitySupplyMsgBack> getEntityBackSupply(Integer id) {
        return entityInfoService.getEntityBackSupply(id);
    }

    /**
     * 导出企业主体基本信息
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/10/25 17:39
     */
    @ApiOperation(value = "导出企业主体基本信息")
    @GetMapping("/exportEntity")
    public void exportEntity(HttpServletResponse response) {
        entityInfoService.exportEntity(response);
    }

    /**
     * 根据类型导出企业主体清单
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/10/25 17:39
     */
    @ApiOperation(value = "根据类型导出企业主体清单")
    @GetMapping("/exportEntityByType")
    public void exportEntityByType(Integer type,HttpServletResponse response) {
        entityInfoService.exportEntityByType(type,response);
    }
}
