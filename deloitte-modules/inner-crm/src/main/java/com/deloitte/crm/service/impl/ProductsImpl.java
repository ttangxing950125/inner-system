package com.deloitte.crm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.Products;
import com.deloitte.crm.mapper.ProductsMapper;
import com.deloitte.crm.service.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/12 18:33
 */
@Service
@AllArgsConstructor
public class ProductsImpl extends ServiceImpl<ProductsMapper,Products> implements ProductsService {

    @Resource
    private  ProductsMapper mapper;
    /**
     *添加方法描述
     *
     * @return List<Products>
     * @author penTang
     * @date 2022/10/12 18:44
    */
    @Override
    public List<Products> getProducts() {
        return list();
    }
}
