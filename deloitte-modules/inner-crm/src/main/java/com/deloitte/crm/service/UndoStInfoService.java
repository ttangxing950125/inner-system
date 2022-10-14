package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.ImplementStInfo;
import com.deloitte.crm.domain.UndoStInfo;

import java.util.List;

/**
 * 撤销ST(摘帽)(UndoStInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-14 17:50:05
 */
public interface UndoStInfoService extends IService<UndoStInfo> {
    /**
     * 完成当前wind文件的任务
     *
     * @param windTask
     * @param delIsses
     * @return
     */
    Object doTask(CrmWindTask windTask, List<UndoStInfo> delIsses);

}
