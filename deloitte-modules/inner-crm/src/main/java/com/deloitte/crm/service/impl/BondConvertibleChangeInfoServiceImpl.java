package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.BondConvertibleInfo;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.mapper.BondConvertibleChangeInfoMapper;
import com.deloitte.crm.domain.BondConvertibleChangeInfo;
import com.deloitte.crm.service.BondConvertibleChangeInfoService;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.strategy.impl.BondConvertibleChangeStrategy;
import com.deloitte.crm.strategy.impl.BondConvertibleStrategy;
import com.deloitte.crm.utils.ApplicationContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

/**
 * 可交换转债发行预案(BondConvertibleChangeInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-13 13:39:43
 */
@Service("bondConvertibleChangeInfoService")
public class BondConvertibleChangeInfoServiceImpl extends ServiceImpl<BondConvertibleChangeInfoMapper, BondConvertibleChangeInfo> implements BondConvertibleChangeInfoService {

    @Resource
    private ICrmWindTaskService crmWindTaskService;

    @Override
    public Object doTask(CrmWindTask windTask, List<BondConvertibleChangeInfo> bondConvertibleChangeInfo) {

        windTask.setComplete(2);

        this.crmWindTaskService.updateById(windTask);
        //获取当前时间
        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        CopyOnWriteArrayList<Future> futureList = new CopyOnWriteArrayList();

        for (BondConvertibleChangeInfo bondConvertibleChangeInfos : bondConvertibleChangeInfo) {
            if (bondConvertibleChangeInfos.getNoticeDate().equals("数据来源：Wind")) {
                continue;
            }
            Future<Object> future = ApplicationContextHolder.get().getBean(BondConvertibleChangeStrategy.class).doBondImport(bondConvertibleChangeInfos, timeNow, windTask);
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

        return true;
    }
}
