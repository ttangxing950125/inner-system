package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.*;

import java.util.List;

/**
 * (EntityStockThkRel)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:18
 */
public interface EntityStockThkRelService extends IService<EntityStockThkRel> {

    /**
     * 绑定港股和主体的关联关系
     * @param stockThkInfo
     * @param entityName
     * @return
     */
    boolean bindRelOrCreateTask(StockThkInfo stockThkInfo, String entityName, CrmWindTask windTask, ThkSecIssInfo secIssInfo);

    /**
     * 查询是否存在主体和港股的关联关系
     * @param entityCode
     * @param stockDqCode
     * @return
     */
    EntityStockThkRel findByEntityStockDeCode(String entityCode, String stockDqCode);

    /**
     * 查询港股所属主体，只查询关联关系启用状态的
     * @param stockDqCode
     * @return
     */
    List<EntityInfo> findEntityByStockDqCode(String stockDqCode);

    List<EntityStockThkRel> selectEntityStockThkRelListByBondCodes(List<String> stockThkCodes);
}
