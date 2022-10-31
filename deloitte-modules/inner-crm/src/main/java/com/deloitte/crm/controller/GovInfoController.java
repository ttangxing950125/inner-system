package com.deloitte.crm.controller;

import com.alibaba.fastjson.JSON;
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
import com.deloitte.crm.domain.dto.GovAttrByDto;
import com.deloitte.crm.dto.GovInfoDto;
import com.deloitte.crm.dto.MoreIndex;
import com.deloitte.crm.service.IEntityNameHisService;
import com.deloitte.crm.service.IGovInfoService;
import com.deloitte.crm.vo.EntityInfoHisNameVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 【请填写功能名称】Controller
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Slf4j
@RestController
@RequestMapping("/govInfo")
@Api(tags = "政府主体查询修改相关数据")

public class GovInfoController extends BaseController {
    @Autowired
    private IGovInfoService govInfoService;
    @Autowired
    private IEntityNameHisService entityNameHisService;
    /**
     * 简陋搜索条件--市
     */
    public static String EASY_NAME_CITY = "市";
    /**
     * 简陋搜索条件--区
     */
    public static String EASY_NAME_AREA = "区";
    /**
     * 简陋搜索条件--县
     */
    public static String EASY_NAME_COUNTRY = "县";

    /**
     * 统计政府信息
     *
     * @return GovInfoDto
     * @author penTang
     * @date 2022/9/22 23:21
     */
    @PostMapping("/govList")
    @ApiOperation(value = "{统计政府信息}", response = GovInfoDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query"

            )
    })
    public R getGovInfo() {
        return R.ok(govInfoService.getGovInfo());

    }

    /**
     * 导出政府信息
     *
     * @return GovInfoDto
     * @author penTang
     * @date 2022/9/22 23:21
     */
    @PostMapping("/govExport")
    @ApiOperation(value = "{导出政府信息}", response = GovInfoDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "entityAttrDto", value = "包含表中gov_info的所有字段"

            )
    })
    public R importGovInfo(GovAttrByDto govAttrByDto) {
        govInfoService.ExportEntityGov(govAttrByDto);
        return R.ok();
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:govInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(GovInfo govInfo) {
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
    public void export(HttpServletResponse response, GovInfo govInfo) {
        List<GovInfo> list = govInfoService.selectGovInfoList(govInfo);
        ExcelUtil<GovInfo> util = new ExcelUtil<GovInfo>(GovInfo.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:govInfo:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(govInfoService.selectGovInfoById(id));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:govInfo:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GovInfo govInfo) {
        return toAjax(govInfoService.updateGovInfo(govInfo));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:govInfo:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(govInfoService.deleteGovInfoByIds(ids));
    }

    /**
     * 政府主体批量修改
     *
     * @param govInfoList
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 15:24
     */
    @ApiOperation(value = "政府主体批量修改")
    @ApiImplicitParam(name = "govInfoList", value = "可包含表 gov_info 的所有字段", paramType = "body", dataTypeClass = GovInfo.class)
    @PostMapping("/updateInfoList")
    public R updateInfoList(@RequestBody GovInfo govInfoList) {
        return R.ok(govInfoService.updateInfoList(govInfoList));
    }

    /**
     * 根据政府名称查询政府主体
     *
     * @param govName
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "根据政府名称查询政府主体")
    @ApiImplicitParam(name = "govName", value = "政府主体名称", paramType = "query", example = "", dataType = "String")
    @PostMapping("/getGovByName")
    public R getGovByName(String govName) {
        if (ObjectUtils.isEmpty(govName)) {
            return R.fail("请输入需要查询的条件");
        }
        if (EASY_NAME_CITY.equals(govName) || EASY_NAME_AREA.equals(govName) || EASY_NAME_COUNTRY.equals(govName)) {
            return R.fail("简陋搜索条件--" + govName);
        }
        return govInfoService.getGovByName(govName);
    }

    /**
     * 政府主体分类查询
     *
     * @param type  政府主体类型 1、地方政府2、地方主管部门3、其他
     * @param param 筛选条件
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "政府主体分类查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "政府主体类型 1、地方政府2、地方主管部门3、其他", paramType = "query", example = "", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", example = "", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数", paramType = "query", example = "", dataType = "Integer"),
            @ApiImplicitParam(name = "param", value = "param 筛选条件", paramType = "query", example = "", dataType = "String")
    })
    @PostMapping("/getInfoList")
    public R getInfoList(Integer type, String param, Integer pageNum, Integer pageSize) {
        return govInfoService.getInfoList(type, param, pageNum, pageSize);
    }

    /**
     * 政府主体分类概览
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/22 17:49
     */
    @ApiOperation(value = "政府主体分类概览")
    @PostMapping("/getOverviewByGroup")
    public R getOverviewByGroup() {
        return R.ok(govInfoService.getOverviewByGroup());
    }

    /**
     * 新增政府主体的曾用名
     *
     * @param govInfo
     * @return R
     * @author 冉浩岑
     * @date 2022/9/23 8:44
     */
    @ApiOperation(value = "新增政府主体的曾用名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityCode", value = "政府主体代码", paramType = "body", example = "dq000111", dataType = "Integer"),
            @ApiImplicitParam(name = "entityName", value = "政府主题名称", paramType = "body", example = "白云区", dataType = "String"),
            @ApiImplicitParam(name = "updated", value = "修改时间", paramType = "body", example = "2022-10-11", dataType = "String"),
            @ApiImplicitParam(name = "entityNameHisRemarks", value = "主体添加的曾用名或别称的备注", paramType = "body", example = "测试", dataType = "String")
    })
    @PostMapping("/addOldName")
    public R addOldName(@RequestBody EntityInfoHisNameVo entityInfo) {
        return entityNameHisService.addGovNameHis(entityInfo.getEntityCode(),entityInfo.getEntityName(),entityInfo.getUpdated(),entityInfo.getEntityNameHisRemarks());
    }

    /**
     * 新增地方政府
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/10/12 17:10
     */
    @ApiOperation(value = "新增地方政府")
    @ApiImplicitParam(name = "govInfo", value = "全数据", paramType = "body", example = "", dataTypeClass = GovInfo.class)
    @PostMapping("/addGovInfo")
    public R addGovInfo(@RequestBody GovInfo govInfo) {
        return govInfoService.insertGovInfo(govInfo);
    }

    /**
     * 修改,停用政府主体的曾用名
     *
     * @param dqCode
     * @param oldName
     * @param newOldName
     * @param status
     * @return R
     * @author 冉浩岑
     * @date 2022/9/25 13:22
     */
    @ApiOperation(value = "修改,停用政府主体的曾用名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dqCode", value = "德勤统一识别码", paramType = "query", example = "1", dataType = "String"),
            @ApiImplicitParam(name = "oldName", value = "原本的曾用名", paramType = "query", example = "原始曾用名", dataType = "String"),
            @ApiImplicitParam(name = "newOldName", value = "修改后的曾用名", paramType = "query", example = "新的曾用名", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "是否停用曾用名", paramType = "query", example = "新的曾用名", dataType = "String"),
            @ApiImplicitParam(name = "remarks", value = "新的曾用名备注", paramType = "query", example = "新的曾用名备注", dataType = "String")
    })
    @PostMapping("/updateOldName")
    public R updateOldName(String dqCode, String oldName, String newOldName, String status,String remarks) {
        return govInfoService.updateOldName(dqCode, oldName, newOldName, status,remarks);
    }

    /**
     * 根据 dqCode 查询政府主体
     *
     * @param dqGovCode
     * @return R
     * @author 冉浩岑
     * @date 2022/9/23 8:59
     */
    @ApiOperation(value = "根据 dqCode 查询政府主体")
    @ApiImplicitParam(name = "dqGovCode", value = "政府主体德勤唯一识别码", paramType = "query", dataType = "String")
    @PostMapping("/getInfoDetail")
    public R getInfoDetail(String dqGovCode) {
        return govInfoService.getInfoDetail(dqGovCode);
    }

    /**
     * 地方政府-更多指标
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/23 10:56
     */
    @ApiOperation(value = "地方政府-更多指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mapList", value = "更多指标添加指标项", paramType = "body", example = "", dataTypeClass = MoreIndex.class),
            @ApiImplicitParam(name = "pageSize", value = "页面size", paramType = "body", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "body", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "govScale", value = "城市规模", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "govGrading", value = "城市分级", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "isProvince", value = "省级行政区", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "isCity", value = "地级行政区", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "isCounty", value = "县级行政区", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "isJKGX", value = "经开高新区", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "eightER", value = "八大经济区", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "nineteenCity", value = "19个城市群", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "hundred", value = "百强县", paramType = "body", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "CCity", value = "国家中心城市", paramType = "body", example = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "provincial", value = "省会城市", paramType = "body", example = "1", dataType = "Integer")
    })
    @PostMapping("/getListEntityByPage")
    public R getListEntityByPage(@RequestBody GovAttrByDto govAttrDto) {
        log.info(">>>>>地方政府-更多指标请求参数:{}", JSON.toJSONString(govAttrDto));
        R<Object> ok = R.ok(govInfoService.getListEntityByPage(govAttrDto));
        log.info(">>>>>地方政府-更多指标返回结果集合:{}", JSON.toJSONString(ok));
        return ok;
    }

    /**
     * 地方政府-更多指标-主体范围
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/10/11 17:10
     */
    @ApiOperation(value = "地方政府-更多指标-主体范围")
    @PostMapping("/getGovRange")
    public R getGovRange() {
        return R.ok(govInfoService.getGovRange());
    }

    /**
     * 政府主体总概览
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/28 10:56
     */
    @ApiOperation(value = "政府主体总概览")
    @PostMapping("/getOverview")
    public R getOverview() {
        return R.ok(govInfoService.getOverview());
    }

    /**
     * 导出导出全部企业主体Excel表
     *
     * @param govAttrDto
     * @return R
     * @author penTang
     * @date 2022/9/25 17:48
     */
    @ApiOperation(value = "导出全部企业主体Excel表")
    @ApiImplicitParam(name = "entityAttrDto", value = "包含表中gov_info的所有字段", paramType = "body", example = "", dataTypeClass = EntityAttrByDto.class)
    @PostMapping("/getListEntity")
    public R ImportListEntity(@RequestBody GovAttrByDto govAttrDto) {
        return R.ok(govInfoService.getListEntityByPage(govAttrDto));
    }


    /**
     * 获取省市级数据
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/29 15:49
     */
    @ApiOperation(value = "获取省市级数据")
    @ApiImplicitParam(name = "preGovCode", value = "父级Code", paramType = "query", example = "GV10110", dataType = "Integer")
    @PostMapping("/getGovLevel")
    public R getGovLevel(String preGovCode) {
        return R.ok(govInfoService.getGovLevel(preGovCode));
    }


    /**
     * 政府主体清单-地方政府概览
     *
     * @param
     * @return R
     * @author 冉浩岑
     * @date 2022/10/17 8:49
     */
    @ApiOperation(value = "政府主体清单-地方政府概览")
    @PostMapping("/getGovView")
    public R getGovView() {
        return R.ok(govInfoService.getGovView());
    }

    /**
     * 根据政府名称或者政府code查询政府主体
     *
     * @param param
     * @return R
     * @author 冉浩岑
     * @date 2022/10/18 17:39
     */
    @ApiOperation(value = "根据政府名称或者政府code查询政府主体")
    @PostMapping("/getGovByParam")
    public R getGovByParam(String param) {
        return R.ok(govInfoService.getGovByParam(param));
    }


    /**
     * 导出政府主体基本信息
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/10/25 17:39
     */
    @ApiOperation(value = "导出政府主体基本信息")
    @GetMapping("/exportGov")
    public void exportGov(HttpServletResponse response) throws Exception {
        govInfoService.exportEntity(response);
    }

    /**
     * 整表根据父级 code 更新政府主体父子级对应关系
     *
     * @return void
     * @author 冉浩岑
     * @date 2022/10/26 9:42
     */
    @RequiresPermissions("crm:govInfo:edit")
    @ApiOperation(value = "根据父级 code 更新政府主体父子级对应关系")
    @PostMapping("/updateGovInfosByPreCode")
    public void updateGovInfosByPreCode()   {
        govInfoService.updateGovInfosByPreCode();
    }
}
