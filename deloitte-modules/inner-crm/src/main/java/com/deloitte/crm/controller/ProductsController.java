package com.deloitte.crm.controller;
import cn.hutool.http.server.HttpServerResponse;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.Products;
import com.deloitte.crm.service.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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

    /**
     *查询产品的规则文件
     *
     * @return Products
     * @author penTang
     * @date 2022/10/20 17:26
    */
    @ApiOperation(value = "下载查询产品")
    @PostMapping("/getProductsExcel")
    @ApiImplicitParam(name = "id",value = "id",paramType = "query",dataType="Integer")
    public R getProductsExcel(@RequestParam("id") Integer id, HttpServletResponse response) throws Exception{
      productsService.getProductsExcel(id, response);

        return R.ok("下载成功");
    }

    /**
     *查询产品的规则文件
     *
     * @return Products
     * @author penTang
     * @date 2022/10/20 17:26
     */
    @ApiOperation(value = "下载查询产品")
    @PostMapping("/getProductsOne")
    @ApiImplicitParam(name = "id",value = "id",paramType = "query",dataType="Integer")
    public R getProducts(@RequestParam("id") Integer id){
        return productsService.getProductsOne(id);
    }

}
