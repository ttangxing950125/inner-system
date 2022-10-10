package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.DefaultFirstNumberCount;
import com.deloitte.crm.dto.BondInfoDto;
import com.deloitte.crm.mapper.DefaultMoneyTotalMapper;
import com.deloitte.crm.domain.DefaultMoneyTotal;
import com.deloitte.crm.service.DefaultMoneyTotalService;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.strategy.impl.DefaultFirstNumberCountStrategy;
import com.deloitte.crm.strategy.impl.DefaultMoneyTotalStrategy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * (DefaultMoneyTotal)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-09 10:50:32
 */
@Service("defaultMoneyTotalService")
public class DefaultMoneyTotalServiceImpl extends ServiceImpl<DefaultMoneyTotalMapper, DefaultMoneyTotal> implements DefaultMoneyTotalService {
    @Resource
    private ICrmWindTaskService crmWindTaskService;
    @Resource
    @Lazy
    private DefaultMoneyTotalStrategy defaultMoneyTotalStrategy;

    @Override
    public Object doTask(CrmWindTask windTask, List<DefaultMoneyTotal> defaultMoneyTotal) {
        //改任务状态
        windTask.setComplete(2);
        this.crmWindTaskService.updateById(windTask);
        //获取当前时间
        Date timeNow = DateUtil.parseDate(DateUtil.getDate());
        //排除 债券简称 为空的 和 非法 债券代码
        List<DefaultMoneyTotal> filterBondAbstractIsBankLists = defaultMoneyTotal.parallelStream()
                .filter(e -> StrUtil.isNotBlank(e.getBondAbstract()) || !e.getBondCode().contains("数据来源：Wind")).collect(Collectors.toList());
        /**
         * 代码可作为后期优化
         * Future 的升级版 CompletableFuture 作为 Future 升级版 构建多个异步并发任务 多任务执行 提高执行效率
         * @see java.util.concurrent.CompletableFuture
         * {@link com.deloitte.crm.config.SpringAsyncConfig}
         * @since 1.8
         */
        CopyOnWriteArrayList<Future> futureList = new CopyOnWriteArrayList();

        for (DefaultMoneyTotal moneyTotal : filterBondAbstractIsBankLists) {
            Future<Object> future = defaultMoneyTotalStrategy.doBondImport(moneyTotal, timeNow, windTask);
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
