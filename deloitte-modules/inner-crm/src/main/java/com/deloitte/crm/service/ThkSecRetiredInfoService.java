package com.deloitte.crm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.ThkSecRetiredInfo;

import java.util.List;

/**
 * 证券发行-港股-已退市证券一览(ThkSecRetiredInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-01 11:45:21
 */
public interface ThkSecRetiredInfoService extends IService<ThkSecRetiredInfo> {

    Object doTask(CrmWindTask windTask, List<ThkSecRetiredInfo> thkSecRetiredInfo);
}
