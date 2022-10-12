package com.deloitte.crm.controller;


import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.EntityFinancial;
import com.deloitte.crm.service.EntityFinancialService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (EntityFinancial)表控制层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-11 17:11:57
 */
@RestController
@RequestMapping("entityFinancial")
public class EntityFinancialController {
    /**
     * 服务对象
     */
    @Resource
    private EntityFinancialService entityFinancialService;

    /**
     * 金融机构根据entityCode补充录入副表信息
     *
     * @param entityFinancial
     * @return R
     * @author 冉浩岑
     * @date 2022/10/12 9:10
     */
    @ApiOperation(value = "金融机构根据entityCode补充录入副表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityCode", value = "主体编码", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "belPlace", value = "所属地区", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "belJurisdiction", value = "所属辖区", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "regulators", value = "对口监管机构", paramType = "body", example = "", dataType = "String"),
            @ApiImplicitParam(name = "remarks", value = "备注", paramType = "body", example = "", dataType = "String")
    })
    @PostMapping("/addFinEntitySubtableMsg")
    public R addFinEntitySubtableMsg(@RequestBody EntityFinancial entityFinancial) {
        entityFinancialService.addFinEntitySubtableMsg(entityFinancial);
        return R.ok();
    }
}
