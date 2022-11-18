package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.ProductsMasterDict;
import com.deloitte.crm.mapper.ProductsMasterDictMapper;
import com.deloitte.crm.service.ProductsMasterDictService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/17 10:48
 */
@Service
@AllArgsConstructor

public class ProductsMasterDictImpl extends ServiceImpl<ProductsMasterDictMapper, ProductsMasterDict>  implements ProductsMasterDictService {
     /**
      *根据产品客户id 查询所存在的敞口
      *
      * @param ProdDuctsId
      * @return List<ProductsMasterDict>
      * @author penTang
      * @date 2022/10/17 15:58
     */
    @Override
    public List<ProductsMasterDict> selectMasDict(Integer ProdDuctsId){
        LambdaQueryWrapper<ProductsMasterDict> qw = new LambdaQueryWrapper<ProductsMasterDict>().eq(ProductsMasterDict::getProCustId, ProdDuctsId);
        return list(qw);
    }
}
