package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.DefaultFirstNumberCount;
import com.deloitte.crm.dto.DefaultFirstNumberCountDto;
import com.deloitte.crm.mapper.CnDelistInfoMapper;
import com.deloitte.crm.domain.CnDelistInfo;
import com.deloitte.crm.service.CnDelistInfoService;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.crm.strategy.impl.CnDelistInfoStrategy;
import com.deloitte.crm.utils.ApplicationContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * (CnDelistInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-11 18:46:11
 */
@Service("cnDelistInfoService")
public class CnDelistInfoServiceImpl extends ServiceImpl<CnDelistInfoMapper, CnDelistInfo> implements CnDelistInfoService {
    @Resource
    private ICrmWindTaskService crmWindTaskService;

    @Override
    public Object doTask(CrmWindTask windTask, List<CnDelistInfo> cnDelistInfo) {
        //改任务状态
        windTask.setComplete(2);
        this.crmWindTaskService.updateById(windTask);
        //获取当前时间
        Date timeNow = DateUtil.parseDate(DateUtil.getDate());
        //获取当前时间
        //排除 债券简称 为空的 和
        final List<CnDelistInfo> filterCnDelistInfoList = cnDelistInfo.parallelStream().filter(e -> StrUtil.isNotBlank(e.getName())
                || !e.getCode().contains("数据来源：Wind")).collect(Collectors.toList());
        CopyOnWriteArrayList<Future> futureList = new CopyOnWriteArrayList();
        for (CnDelistInfo cnDelistInfos : filterCnDelistInfoList) {
            Future<CnDelistInfo> future = ApplicationContextHolder.get().getBean(CnDelistInfoStrategy.class).doBondImport(cnDelistInfos, timeNow, windTask);
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
