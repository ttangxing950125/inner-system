package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.dto.DataYearDto;
import com.deloitte.crm.service.ProductsMasterDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author PenTang
 * @date 2022/10/17 18:46
 */
@RestController
@RequestMapping("/ProMaDir")
@Api(tags = "产品客户敞口信息")
public class ProductsMasterDictController
{
    @Resource
    private ProductsMasterDictService productsMasterDictService;
    @PostMapping("/ProMaDir")
    @ApiOperation(value = "{查询敞口信息}", response = DataYearDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "proCustId", value = "客户产品id")
    })
    public R getProductsDir(@RequestParam("proCustId") Integer proCustId ){
      return  R.ok( productsMasterDictService.selectMasDict(proCustId));

    }



}




