package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.StockCnImplementStInfo;

import java.util.List;

/**
 * 实施ST(带帽)(ImplementStInfo)表服务接口
 *ImplementStInfoStrategy
 * @author 吴鹏鹏ppp
 * @since 2022-10-14 17:50:05
 * UndoStInfoStrategy
 */
public interface StockCnImplementStInfoService extends IService<StockCnImplementStInfo> {
    /**
     * 完成当前wind文件的任务
     * @param windTask
     * @param delIsses
     * @return
     */
    Object doTask(CrmWindTask windTask, List<StockCnImplementStInfo> delIsses);

}
