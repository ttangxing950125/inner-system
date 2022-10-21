package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.Products;
import com.deloitte.crm.domain.ProductsCover;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/12 18:06
 */
public interface ProductsService extends IService<Products>  {
    List<Products> getProducts();

    void  getProductsExcel(Integer id, HttpServletResponse response);

    R getProductsOne (Integer id);
}
