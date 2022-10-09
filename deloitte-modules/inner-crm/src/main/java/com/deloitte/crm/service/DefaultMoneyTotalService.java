package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.DefaultFirstNumberCount;
import com.deloitte.crm.domain.DefaultMoneyTotal;

import java.util.List;

/**
 * (DefaultMoneyTotal)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-09 10:50:32
 */
public interface DefaultMoneyTotalService extends IService<DefaultMoneyTotal> {
    /**
     * 完成当前wind文件的任务
     *
     * @param windTask
     * @param delIsses
     * @return
     */
    Object doTask(CrmWindTask windTask, List<DefaultMoneyTotal> defaultMoneyTotal);

}
