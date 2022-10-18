package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.ProductsMasterDict;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/17 10:46
 */
public interface ProductsMasterDictService extends IService<ProductsMasterDict> {
    List<ProductsMasterDict> selectMasDict(Integer ProdDuctsId);
}
