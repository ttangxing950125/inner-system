package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.BondDelIss;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.DefaultFirstNumberCount;

import java.util.List;

/**
 * (DefaultFirstNumberCount)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-09 10:50:31
 */
public interface DefaultFirstNumberCountService extends IService<DefaultFirstNumberCount> {
    /**
     * 完成当前wind文件的任务
     * @param windTask
     * @param delIsses
     * @return
     */
    Object doTask(CrmWindTask windTask, List<DefaultFirstNumberCount> delIsses);

}
