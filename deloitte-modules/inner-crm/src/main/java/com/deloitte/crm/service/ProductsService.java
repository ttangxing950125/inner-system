package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.Products;
import com.deloitte.crm.domain.ProductsCover;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/12 18:06
 */
public interface ProductsService extends IService<Products>  {
    List<Products> getProducts();
}
