package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.ThkSecIssInfo;

import java.util.List;

/**
 * 证券发行-股票发行-聆讯信息一览(ThkSecIssInfo)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:34
 */
public interface ThkSecIssInfoService extends IService<ThkSecIssInfo> {

    /**
     * 完成这个wind excel的任务
     * @param windTask
     * @param thkSecIssInfos
     * @return
     */
    Object doTask(CrmWindTask windTask, List<ThkSecIssInfo> thkSecIssInfos);

    /**
     * 根据名称查询最后一条
     * @param entityCnName
     * @return
     */
    ThkSecIssInfo findLastByEntityName(String entityCnName);
}
