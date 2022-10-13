package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CnCoachBack;
import com.deloitte.crm.domain.CnDelistInfo;
import com.deloitte.crm.domain.CrmWindTask;

import java.util.List;

/**
 * (CnDelistInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-11 18:46:11
 */
public interface CnDelistInfoService extends IService<CnDelistInfo> {
    /**
     * @param windTask
     * @param cnCoachBacks
     * @return
     */
    Object doTask(CrmWindTask windTask, List<CnDelistInfo> cnDelistInfo);

}
