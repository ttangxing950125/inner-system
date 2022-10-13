package com.deloitte.crm.controller;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.service.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PenTang
 * @date 2022/10/12 18:49
 */
@RestController
@RequestMapping("/Products")
@Api(tags = "产品相关数据")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    /**
     *查询产品
     *
     * @return R
     * @author penTang
     * @date 2022/10/12 18:41
     */
    @ApiOperation(value = "查询产品")
    @PostMapping("/getProducts")
    public R getProduct (){
        return R.ok( productsService.getProducts());
    }

}
