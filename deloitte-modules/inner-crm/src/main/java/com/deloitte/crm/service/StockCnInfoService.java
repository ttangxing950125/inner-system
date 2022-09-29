package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.StockCnInfo;

/**
 * a股信息表，大陆股票(StockCnInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:35
 */
public interface StockCnInfoService extends IService<StockCnInfo> {

    R checkStockCnInfo(String StockBode);

    R checkSortName(String SortName);
    /**
     * 根据code查询 大陆股票
     * @param code
     * @return
     */
    StockCnInfo findByCode(String code);

    /**
     * 保存或更新，会删缓存
     * @param stockCnInfo
     * @return
     */
    StockCnInfo saveOrUpdateNew(StockCnInfo stockCnInfo);
}
