package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.BondConvertibleChangeInfo;
import com.deloitte.crm.domain.CrmWindTask;

import java.util.List;

/**
 * 可交换转债发行预案(BondConvertibleChangeInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-13 13:39:43
 */
public interface BondConvertibleChangeInfoService extends IService<BondConvertibleChangeInfo> {
    /**
     * @param windTask
     * @param bondConvertibleChangeInfo
     * @return
     */
    Object doTask(CrmWindTask windTask, List<BondConvertibleChangeInfo> bondConvertibleChangeInfo);
}
