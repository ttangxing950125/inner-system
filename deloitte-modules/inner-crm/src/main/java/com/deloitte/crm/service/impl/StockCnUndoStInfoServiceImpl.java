package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.mapper.StockCnUndoStInfoMapper;
import com.deloitte.crm.domain.StockCnUndoStInfo;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.service.StockCnUndoStInfoService;
import com.deloitte.crm.strategy.impl.StockCnUndoStInfoStrategy;
import com.deloitte.crm.utils.ApplicationContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

/**
 * 撤销ST(摘帽)(UndoStInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-14 17:50:05
 */
@Service("stockCnUndoStInfoServiceImpl")
public class StockCnUndoStInfoServiceImpl extends ServiceImpl<StockCnUndoStInfoMapper, StockCnUndoStInfo> implements StockCnUndoStInfoService {
    @Resource
    private ICrmWindTaskService crmWindTaskService;
    @Override
    public Object doTask(CrmWindTask windTask, List<StockCnUndoStInfo> delIsses) {
        windTask.setComplete(2);
        crmWindTaskService.updateById(windTask);
        //获取当前时间
        Date timeNow = DateUtil.parseDate(DateUtil.getDate());

        CopyOnWriteArrayList<Future> futureList = new CopyOnWriteArrayList();

        for ( StockCnUndoStInfo undoStInfo : delIsses) {
            if (undoStInfo.getCode().contains("数据来源：Wind")) {
                continue;
            }
            Future<Object> future = ApplicationContextHolder.get().getBean(StockCnUndoStInfoStrategy.class).doBondImport(undoStInfo, timeNow, windTask);
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
