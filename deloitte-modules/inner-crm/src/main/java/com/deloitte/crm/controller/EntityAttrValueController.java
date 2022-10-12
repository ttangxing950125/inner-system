package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.common.core.web.page.TableDataInfo;
import com.deloitte.common.log.annotation.Log;
import com.deloitte.common.log.enums.BusinessType;
import com.deloitte.common.security.annotation.RequiresPermissions;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.service.EntityAttrIntypeService;
import com.deloitte.crm.service.IEntityAttrValueService;
import com.deloitte.crm.vo.EntityByIondVo;
import com.deloitte.crm.vo.EntityStockInfoVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/value")
public class EntityAttrValueController extends BaseController {
    @Autowired
    private IEntityAttrValueService entityAttrValueService;

    @Autowired
    private EntityAttrIntypeService entityAttrIntypeService;

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("crm:value:list")
    @GetMapping("/list")
    public TableDataInfo list(EntityAttrValue entityAttrValue) {
        startPage();
        List<EntityAttrValue> list = entityAttrValueService.selectEntityAttrValueList(entityAttrValue);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("crm:value:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntityAttrValue entityAttrValue) {
        List<EntityAttrValue> list = entityAttrValueService.selectEntityAttrValueList(entityAttrValue);
        ExcelUtil<EntityAttrValue> util = new ExcelUtil<EntityAttrValue>(EntityAttrValue.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @RequiresPermissions("crm:value:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(entityAttrValueService.selectEntityAttrValueById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @RequiresPermissions("crm:value:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EntityAttrValue entityAttrValue) {
        return toAjax(entityAttrValueService.insertEntityAttrValue(entityAttrValue));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("crm:value:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntityAttrValue entityAttrValue) {
        return toAjax(entityAttrValueService.updateEntityAttrValue(entityAttrValue));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("crm:value:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(entityAttrValueService.deleteEntityAttrValueByIds(ids));
    }

    @PostMapping("/createBE")
    @ApiOperation(value = "新增债券主体")
    @ApiImplicitParam(name = "entityByIondVo", value = "", paramType = "body", example = "", dataTypeClass = EntityByIondVo.class)
    /**
     *新增债券主体
     *
     * @param entityByIondVo
     * @return R
     * @author penTang
     * @date 2022/9/27 20:40
     */
    public R createBondEntity(@Validated @RequestBody EntityByIondVo entityByIondVo) {

        return entityAttrValueService.createBondEntity(entityByIondVo);

    }


    @PostMapping("/createST")
    @ApiOperation(value = "新增股票主体")
    @ApiImplicitParam(name = "entityStockInfoVo", value = "", paramType = "body", example = "", dataTypeClass = EntityStockInfoVo.class)
    /**
     *创建股票主体(根据股票状态 A -A股 G -港股)
     *
     * @param entityStockInfoVo
     * @return R
     * @author penTang
     * @date 2022/9/28 23:09
     */
    public R createStockEntity(@Validated @RequestBody EntityStockInfoVo entityStockInfoVo) {
        if (ObjectUtils.equals(entityStockInfoVo.getStockType(), "STOCK_CN_INFO")) {
            return entityAttrValueService.createStockEntity(entityStockInfoVo);
        } else if (ObjectUtils.equals(entityStockInfoVo.getStockType(), "STOCK_HK_INFO")) {
            return entityAttrValueService.createStockEntityG(entityStockInfoVo);
        }
        return R.fail("股票代码错误");
    }

    /**
     * 获取上市板块
     *
     * @return R
     * @author penTang
     * @date 2022/10/10 17:22
     */
    @GetMapping("/getLisSec")
    public R getLisSec(String StockType) {
        if (ObjectUtils.equals(StockType, Common.STOCK_TYPE_A)) {


        } else {


        }
        return null;

    }

    /**
     * 获取交易所
     *
     * @return R
     * @author penTang
     * @date 2022/10/10 17:23
     */
    @GetMapping("/getExchange")
    public R getExchange(String StockType) {
        if (ObjectUtils.equals(StockType, Common.STOCK_TYPE_A)) {


        } else {


        }
        return null;


    }

    /**
     * 获取金融机构子行业
     *
     * @return R
     * @author penTang
     * @date 2022/10/10 17:24
     */
    @GetMapping("/getFinanceSubIndu")
    public R getFinanceSubIndu(Integer attrId) {
        return R.ok(entityAttrIntypeService.getTypeByAttrId(attrId));
    }
}