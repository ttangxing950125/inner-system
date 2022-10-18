package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.dto.DataYearDto;
import com.deloitte.crm.dto.EntityInfoDto;
import com.deloitte.crm.dto.ProCustomerDto;
import com.deloitte.crm.service.ProductsMasterRelService;
import com.deloitte.crm.vo.ProductsMasterRelVo;
import com.deloitte.system.api.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author PenTang
 * @date 2022/10/17 11:44
 */
@RestController
@RequestMapping("/ProMaRel")
@Api(tags = "产品敞口关联关系修改d")
public class ProductsMasterRelController {

    @Resource
    private RoleService roleService;
    @Resource
    private ProductsMasterRelService productsMasterRelService;

    /**
     * 查询产品年份
     *
     * @return R
     * @author penTang
     * @date 2022/10/17 11:47
     */
    @PostMapping("/getDataYear")
    @ApiOperation(value = "{查询产品年份}", response = DataYearDto.class)
    public R selectDataYear() {
        return R.ok(roleService.selectDataYear());
    }

    /**
     * 根据当前的entityCode查询当前的产品的敞口信息
     *
     * @param entityCode
     * @return R
     * @author penTang
     * @date 2022/10/17 14:12
     */
    @PostMapping("/getProDucCom")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "EntityCode", value = "主体code")
    })
    @ApiOperation(value = "{查询产品客户敞口信息}", response = ProCustomerDto.class)
    public R selectProCouByEntityCode(@RequestParam("entityCode") String entityCode,@RequestParam("dataYear") String dataYear) {
        return R.ok(productsMasterRelService.getProductsMasterRelList(entityCode,dataYear));
    }

    /**
     *修改产品客户信息
     *
     * @param productsMasterRelVo
     * @return R
     * @author penTang
     * @date 2022/10/18 9:34
    */
    @PostMapping("/updateRel")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "ProductsMasterRelVo", value = "修改客户信息参数")
    })
    @ApiOperation(value = "{修改产品信息}", response =Boolean.class)
    public R updateRel(@RequestBody ProductsMasterRelVo productsMasterRelVo ){
        return R.ok(productsMasterRelService.updateRel(productsMasterRelVo));
    }



}