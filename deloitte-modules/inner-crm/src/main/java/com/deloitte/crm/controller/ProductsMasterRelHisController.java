package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.ProductsMasterRelHis;
import com.deloitte.crm.dto.DataYearDto;
import com.deloitte.crm.service.ProducysMasterRelHisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PenTang
 * @date 2022/10/18 17:35
 */
@RestController
@RequestMapping("/ProMaRelHis")
@Api(tags = "产品敞口关联关系修改记录")
public class ProductsMasterRelHisController {

    private ProducysMasterRelHisService producysMasterRelHisService;
    /**
     * 查询修改记录
     *
     * @return R
     * @author penTang
     * @date 2022/10/17 11:47
     */
    @PostMapping("/getDataHis")
    @ApiOperation(value = "{查询修改记录}", response = ProductsMasterRelHis.class)
    public R getAll(){
       return R.ok(producysMasterRelHisService.getHis());
    }

}




