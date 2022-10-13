package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.ProductsCover;
import com.deloitte.crm.dto.ProductCoverDto;
import com.deloitte.crm.vo.EntityOrGovByAttrVo;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/12 16:50
 */
public interface ProductsCoverService extends IService<ProductsCover> {


    Page<ProductCoverDto> getProducts(EntityOrGovByAttrVo entityOrGovByAttrVo);
}
