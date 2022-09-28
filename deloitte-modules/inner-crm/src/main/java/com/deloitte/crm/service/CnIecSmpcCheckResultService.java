package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CnIecSmpcCheckResult;
import com.deloitte.crm.domain.CrmWindTask;

import java.util.List;

/**
 * IPO-发审委上市委审核结果(CnIecSmpcCheckResult)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:48
 */
public interface CnIecSmpcCheckResultService extends IService<CnIecSmpcCheckResult> {

    /**
     * IPO-发审委上市委审核结果(CnIecSmpcCheckResult))执行导入任务
     * @param windTask
     * @param cnCoachBacks
     * @return
     */
    Object doTask(CrmWindTask windTask, List<CnIecSmpcCheckResult> cnCoachBacks);

    /**
     *根据公司名称查询 IPO-发审委上市委审核结果(CnIecSmpcCheckResult))执行导入任务 表的最后一条记录
     * @param entityName
     * @return
     */
    CnIecSmpcCheckResult findLastByEntityName(String entityName);
}
