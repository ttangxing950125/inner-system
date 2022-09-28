package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CnCoachBack;
import com.deloitte.crm.domain.CrmWindTask;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * IPO-辅导备案(CnCoachBack)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:47
 */
public interface CnCoachBackService extends IService<CnCoachBack> {

    /**
     * IPO-辅导备案(CnCoachBack)执行导入任务
     * @param windTask
     * @param cnCoachBacks
     * @return
     */
    Object doTask(CrmWindTask windTask, List<CnCoachBack> cnCoachBacks);

    /**
     * 查询最后一条 CnCoachBack
     * @param entityName
     * @return
     */
    CnCoachBack findLastByEntityName(String entityName);
}
