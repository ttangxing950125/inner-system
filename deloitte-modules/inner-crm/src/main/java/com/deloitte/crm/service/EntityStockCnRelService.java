package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.*;

import java.util.List;

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

    /**
     * 查询和德勤code的a股所属主体
     * @param code
     * @return
     */
    List<EntityInfo> findByStockCode(String code);

    /**
     * 创建主体任务
     * @param entityName
     * @param windTask
     * @param cnCoachBack
     * @return
     */
    boolean createTask(String entityName, CrmWindTask windTask, CnCoachBack cnCoachBack);
}
