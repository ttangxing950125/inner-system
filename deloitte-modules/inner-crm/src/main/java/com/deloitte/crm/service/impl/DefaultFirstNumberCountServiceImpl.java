package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.dto.BondInfoDto;
import com.deloitte.crm.mapper.DefaultFirstNumberCountMapper;
import com.deloitte.crm.domain.DefaultFirstNumberCount;
import com.deloitte.crm.service.DefaultFirstNumberCountService;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.strategy.impl.BondDelIssStrategy;
import com.deloitte.crm.strategy.impl.DefaultFirstNumberCountStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * (DefaultFirstNumberCount)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-09 10:50:32
 */
@Service("defaultFirstNumberCountService")
public class DefaultFirstNumberCountServiceImpl extends ServiceImpl<DefaultFirstNumberCountMapper, DefaultFirstNumberCount> implements DefaultFirstNumberCountService {

    @Resource
    private ICrmWindTaskService crmWindTaskService;

    @Resource
    private DefaultFirstNumberCountStrategy defaultFirstNumberCountStrategy;


    @Override
    public Object doTask(CrmWindTask windTask, List<DefaultFirstNumberCount> delIsses) {
        //改任务状态
        windTask.setComplete(2);
        this.crmWindTaskService.updateById(windTask);
        //获取当前时间
        Date timeNow = DateUtil.parseDate(DateUtil.getDate());
        //排除 债券简称 为空的
        final List<DefaultFirstNumberCount> filterDefaultBondsDescIsBlakList = delIsses.stream().filter(e -> StrUtil.isNotBlank(e.getDefaultBondsDesc())).collect(Collectors.toList());

        CopyOnWriteArrayList<Future> futureList = new CopyOnWriteArrayList();

        for (DefaultFirstNumberCount defaultFirstNumberCount : filterDefaultBondsDescIsBlakList) {
            Future<BondInfoDto> future = defaultFirstNumberCountStrategy.doBondImport(defaultFirstNumberCount, timeNow, windTask);
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
