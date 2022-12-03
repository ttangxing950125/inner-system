package com.deloitte.crm.controller;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.crm.domain.Products;
import com.deloitte.crm.domain.ProductsCustomer;
import com.deloitte.crm.domain.ProductsMasterDict;
import com.deloitte.crm.domain.ProductsMasterMapping;
import com.deloitte.crm.dto.EntityByBatchDto;
import com.deloitte.crm.dto.OpenCrmByEntityCodeDto;
import com.deloitte.crm.dto.OpenCrmDto;
import com.deloitte.crm.mapper.ProductsCustomerMapper;
import com.deloitte.crm.mapper.ProductsMapper;
import com.deloitte.crm.mapper.ProductsMasterDictMapper;
import com.deloitte.crm.mapper.ProductsMasterMappingMapper;
import com.deloitte.crm.service.ProductsService;
import com.deloitte.crm.vo.OpenCrmVo;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private ProductsCustomerMapper productsCustomerMapper;
    @Resource
    private ProductsMasterDictMapper productsMasterDictMapper;
    @Resource
    private ProductsMasterMappingMapper productsMasterMappingMapper;
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
    @ApiOperation(value = "下载产品excel")
    @PostMapping("/getProductsExcel")
    @ApiImplicitParam(name = "id",value = "id",paramType = "query",dataType="Integer")
    public void getProductsExcel(@RequestParam("id") Integer id, HttpServletResponse response) throws Exception{
      productsService.getProductsExcel(id, response);

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

    /**
     *资产平台调用接口
     *
     * @param OpenCrmVos
     * @return R
     * @author penTang
     * @date 2022/11/24 10:15
    */
    @PostMapping("/openCrm")
   public R OpenCrmData(@RequestBody List<OpenCrmDto> OpenCrmVos){
        ArrayList<OpenCrmVo> openCrmVos = new ArrayList<OpenCrmVo>();
        for (OpenCrmDto openCrmDto : OpenCrmVos) {
            OpenCrmVo openCrmVo1 = new OpenCrmVo();
            openCrmVo1.setBusinessLine(openCrmDto.getBusinessLine());
            openCrmVo1.setBusinessScene(openCrmDto.getBusinessScene());
            openCrmVo1.setEntityCode("测试");
            openCrmVo1.setEntityName("测试");
            openCrmVo1.setEntityStatus(1);
            openCrmVo1.setIndustryWhitewash(openCrmDto.getIndustryWhitewash());
            openCrmVos.add(openCrmVo1);
        }
        return R.ok(openCrmVos);

   }
   /**资产平台调用接口
    *
    *
    * @param openCrmByEntityCodeDtos
    * @return R
    * @author penTang
    * @date 2022/11/24 10:40
   */
   @PostMapping("/openCrmByCode")
   public R OpenCrmByCode(@RequestBody List<String> openCrmByEntityCodeDtos){
       ArrayList<OpenCrmVo> openCrmVos = new ArrayList<OpenCrmVo>();
       for (String openCrmByEntityCodeDto : openCrmByEntityCodeDtos) {
           OpenCrmVo openCrmVo1 = new OpenCrmVo();
           openCrmVo1.setBusinessLine("测试");
           openCrmVo1.setBusinessScene("测试");
           openCrmVo1.setEntityCode(openCrmByEntityCodeDto);
           openCrmVo1.setEntityName("测试");
           openCrmVo1.setEntityStatus(1);
           openCrmVo1.setIndustryWhitewash("测试");
           openCrmVos.add(openCrmVo1);
       }
       return R.ok(openCrmVos);

   }
}
