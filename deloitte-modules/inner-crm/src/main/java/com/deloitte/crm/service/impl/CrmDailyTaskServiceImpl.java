package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.mapper.CrmDailyTaskMapper;
import com.deloitte.crm.service.ICrmDailyTaskService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 每日生产角色任务的service业务实现层
 * @author PenTang
 * @date 2022/09/22 15:35
 */
@Service
public class CrmDailyTaskServiceImpl extends ServiceImpl<CrmDailyTaskMapper, CrmDailyTask> implements ICrmDailyTaskService {
    /**
     * 保存每日的角色相关任务
     *
     * @param tasks
     * @return Boolean
     * @author penTang
     * @date 2022/9/22 20:24
    */
    @Override
    public Boolean saveCrmDailyTask(List<CrmDailyTask> tasks) {
     return  saveBatch(tasks);
    }

    /**
     * 根据当天时间和角色1的type更新成有任务未完成(2)
     *
     * @param dateTime
     * @return Boolean
     * @author penTang
     * @date 2022/9/22 20:24
     */
    @Override
    public Boolean updateByType(Date dateTime){
        return update(new LambdaUpdateWrapper<CrmDailyTask>()
                .eq(CrmDailyTask::getTaskRoleType,3)
                .eq(CrmDailyTask::getTaskDate,dateTime)
        );
    }

}
