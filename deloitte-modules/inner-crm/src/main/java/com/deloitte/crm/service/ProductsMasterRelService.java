package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.ProductsMasterRel;
import com.deloitte.crm.dto.ProCustomerDto;
import com.deloitte.crm.vo.ProductsMasterRelVo;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/17 11:14
 */
public interface ProductsMasterRelService extends IService<ProductsMasterRel> {
    List<ProCustomerDto> getProductsMasterRelList(String entityCode,String dataYear,Integer ProId);

    Boolean updateRel(ProductsMasterRelVo productsMasterRelVo);
}
