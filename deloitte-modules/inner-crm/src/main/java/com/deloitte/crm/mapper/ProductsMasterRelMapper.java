package com.deloitte.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.ProductsMasterRel;
import com.deloitte.crm.dto.ProCustomerDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/17 11:13
 */
public interface ProductsMasterRelMapper extends BaseMapper<ProductsMasterRel> {

    List<ProCustomerDto> futureList (Integer proId);}
