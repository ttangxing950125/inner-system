package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.mapper.ThkSecIssInfoMapper;
import com.deloitte.crm.domain.ThkSecIssInfo;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.service.ThkSecIssInfoService;
import com.deloitte.crm.strategy.impl.ThkSecIssInfoStrategy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 证券发行-股票发行-聆讯信息一览(ThkSecIssInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:34
 */
@Service("thkSecIssInfoService")
public class ThkSecIssInfoServiceImpl extends ServiceImpl<ThkSecIssInfoMapper, ThkSecIssInfo> implements ThkSecIssInfoService {

    @Resource
    private ICrmWindTaskService crmWindTaskService;

    @Lazy
    @Resource
    private ThkSecIssInfoStrategy thkSecIssInfoStrategy;

    /**
     * 完成这个wind excel的任务
     * @param windTask
     * @param thkSecIssInfos
     * @return
     */
    @Override
    public Object doTask(CrmWindTask windTask, List<ThkSecIssInfo> thkSecIssInfos) {
        //改任务状态
        windTask.setComplete(2);
        crmWindTaskService.updateById(windTask);

        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        List<Future> futureList = new ArrayList<>();

        for (ThkSecIssInfo secIssInfo : thkSecIssInfos) {
            String code = secIssInfo.getCode();
            if (StrUtil.isBlank(code) || code.contains("数据来源：Wind")){
                continue;
            }
            //多线程保存债券信息，更新attrvalue表
            Future<Object> future = thkSecIssInfoStrategy.doThkStockImport(secIssInfo, timeNow, windTask);

            futureList.add(future);
        }

        while (true) {
            boolean isAllDone = true;
            for (Future future : futureList) {
                if (null == future || !future.isDone()) {
                    isAllDone = false;
                }
            }
            if (isAllDone) {
                break;
            }
        }

        //修改原任务状态
        windTask.setComplete(1);
        windTask.setHandleUser(SecurityUtils.getUserId().intValue());
        crmWindTaskService.updateById(windTask);

        //如果今天的windTask全部为已完成，修改crm_daily_task的状态
        crmWindTaskService.checkAllComplete(timeNow);

        return null;
    }

    /**
     * 根据名称查询最后一条
     * @param entityCnName
     * @return
     */
    @Override
    public ThkSecIssInfo findLastByEntityName(String entityCnName) {


        return baseMapper.findLastByEntityName(entityCnName);
    }
}
