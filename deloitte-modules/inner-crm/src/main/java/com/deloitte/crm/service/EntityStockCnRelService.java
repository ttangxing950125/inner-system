package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CnCoachBack;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.EntityStockCnRel;
import com.deloitte.crm.domain.StockCnInfo;

/**
 * (EntityStockCnRel)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:35
 */
public interface EntityStockCnRelService extends IService<EntityStockCnRel> {

    /**
     * 绑定主体和a股的关联关系，如果没有这个企业，就创建新增企业的任务
     * @param stockCnInfo
     * @param entityName
     * @param windTask
     * @param cnCoachBack
     * @return
     */
    boolean bindRelOrCreateTask(StockCnInfo stockCnInfo, String entityName, CrmWindTask windTask, CnCoachBack cnCoachBack);
}
