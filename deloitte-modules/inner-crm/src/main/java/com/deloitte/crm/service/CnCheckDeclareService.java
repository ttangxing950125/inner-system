package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CnCheckDeclare;
import com.deloitte.crm.domain.CrmWindTask;

import java.util.List;

/**
 * IPO-审核申报(CnCheckDeclare)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:47
 */
public interface CnCheckDeclareService extends IService<CnCheckDeclare> {

    /**
     * IPO-审核申报(CnCheckDeclare)执行导入任务
     * @param windTask
     * @param cnCoachBacks
     * @return
     */
    Object doTask(CrmWindTask windTask, List<CnCheckDeclare> cnCoachBacks);

    /**
     * 根据企业名查询 IPO-审核申报(CnCheckDeclare) 最后一条记录
     * @param entityName
     * @return
     */
    CnCheckDeclare findLastByEntityName(String entityName);
}
