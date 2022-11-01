package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.ThkSecIssInfo;
import com.deloitte.crm.mapper.ThkSecRetiredInfoMapper;
import com.deloitte.crm.domain.ThkSecRetiredInfo;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.service.ThkSecRetiredInfoService;
import com.deloitte.crm.strategy.impl.ThkSecRetiredInfoStorategy;
import com.deloitte.crm.utils.ApplicationContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 证券发行-港股-已退市证券一览(ThkSecRetiredInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-01 11:45:42
 */
@Service("thkSecRetiredInfoService")
public class ThkSecRetiredInfoServiceImpl extends ServiceImpl<ThkSecRetiredInfoMapper, ThkSecRetiredInfo> implements ThkSecRetiredInfoService {
    @Resource
    private ICrmWindTaskService crmWindTaskService;

    @Override
    public Object doTask(CrmWindTask windTask, List<ThkSecRetiredInfo> thkSecRetiredInfo) {
        //改任务状态
        windTask.setComplete(2);
        crmWindTaskService.updateById(windTask);

        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        List<Future> futureList = new ArrayList<>();

        for (ThkSecRetiredInfo thkSecRetiredInfos : thkSecRetiredInfo) {
            String code = thkSecRetiredInfos.getCode();
            if (StrUtil.isBlank(code) || code.contains("数据来源：Wind")) {
                continue;
            }
            ThkSecRetiredInfoStorategy thkSecRetiredInfoStorategy = ApplicationContextHolder.get().getBean(ThkSecRetiredInfoStorategy.class);
            Future<Object> future = thkSecRetiredInfoStorategy.doImport(thkSecRetiredInfos, timeNow, windTask);
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
}
