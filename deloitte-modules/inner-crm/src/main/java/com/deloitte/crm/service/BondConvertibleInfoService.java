package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.BondConvertibleInfo;
import com.deloitte.crm.domain.CnDelistInfo;
import com.deloitte.crm.domain.CrmWindTask;

import java.util.List;

/**
 * 可转债发行预案(BondConvertibleInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-13 13:39:43
 */
public interface BondConvertibleInfoService extends IService<BondConvertibleInfo> {
    /**
     * @param windTask
     * @param cnCoachBacks
     * @return
     */
    Object doTask(CrmWindTask windTask, List<BondConvertibleInfo> bondConvertibleInfo);

}
