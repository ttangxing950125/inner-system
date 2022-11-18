package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.domain.StockThkInfo;

/**
 * 股票信息表(StockThkInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:18
 */
public interface StockThkInfoService extends IService<StockThkInfo> {

    /**
     * 保存或更新
     * @param entity
     * @return
     */
    StockThkInfo saveOrUpdateNew(StockThkInfo entity);

    /**
     * 根据证券代码code查询港股
     * @param secIssInfoCode
     * @return
     */
    StockThkInfo findByCode(String secIssInfoCode);
}
