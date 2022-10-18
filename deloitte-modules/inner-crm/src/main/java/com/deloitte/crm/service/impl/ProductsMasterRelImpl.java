package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.ProductsMasterRel;
import com.deloitte.crm.dto.ProCustomerDto;
import com.deloitte.crm.mapper.ProductsMasterRelMapper;
import com.deloitte.crm.service.ProductsMasterRelService;
import com.deloitte.crm.vo.ProductsMasterRelVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/17 11:16
 */
@Service
@AllArgsConstructor
public class ProductsMasterRelImpl extends ServiceImpl<ProductsMasterRelMapper,ProductsMasterRel> implements ProductsMasterRelService {
        private  ProductsMasterRelMapper mapper;
        /**
         *查询客户产品敞口映射
         *
         * @param entityCode
         * @return List<ProCustomerDto>
         * @author penTang
         * @date 2022/10/17 14:58
        */
        @Override
        public List<ProCustomerDto> getProductsMasterRelList(String entityCode,String dataYear) {

            return mapper.futureList(entityCode,dataYear);
        }
                /**
                 修改敞口关联关系
                 *
                 * @param productsMasterRelVo
                 * @return Boolean
                 * @author penTang
                 * @date 2022/10/17 19:18
                */
        @Override
        public  Boolean updateRel(ProductsMasterRelVo productsMasterRelVo){
                LambdaQueryWrapper<ProductsMasterRel> qw = new LambdaQueryWrapper<ProductsMasterRel>()
                        .eq(ProductsMasterRel::getEntityCode, productsMasterRelVo.getEntityCode())
                        .eq(ProductsMasterRel::getProCustId, productsMasterRelVo.getProCustId())
                .eq(ProductsMasterRel::getDataYear, productsMasterRelVo.getDataYear());
                ProductsMasterRel productsMasterRel = getOne(qw);
                if (productsMasterRel!= null){
                        boolean b = updateById(productsMasterRel.setProMasDictId(productsMasterRelVo.getProMasDictIdNew()));
                        return b;
                }else{
                        return false;
                }


        }


}