package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.ProductsMasterRelHis;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/10/18 16:51
 */
public interface ProducysMasterRelHisService extends IService<ProductsMasterRelHis> {
    List<ProductsMasterRelHis> getHis();
}
