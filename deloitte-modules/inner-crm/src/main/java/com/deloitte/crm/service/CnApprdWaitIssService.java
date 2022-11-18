package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CnApprdWaitIss;
import com.deloitte.crm.domain.CrmWindTask;

import java.util.List;

/**
 * IPO-审核通过尚未发行(CnApprdWaitIss)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:46
 */
public interface CnApprdWaitIssService extends IService<CnApprdWaitIss> {

    /**
     * 查询IPO-审核通过尚未发行(CnApprdWaitIss)表最后一条记录
     * @param entityName
     * @return
     */
    CnApprdWaitIss findLastByEntityName(String entityName);

    /**
     * IPO-审核通过尚未发行(CnApprdWaitIss)执行导入任务
     * @param windTask
     * @param list
     * @return
     */
    Object doTask(CrmWindTask windTask, List<CnApprdWaitIss> list);
}
