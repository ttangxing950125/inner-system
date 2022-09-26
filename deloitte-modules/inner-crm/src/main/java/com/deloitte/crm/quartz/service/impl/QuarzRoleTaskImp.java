package com.deloitte.crm.quartz.service.impl;

import com.deloitte.common.core.constant.SecurityConstants;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmWindDict;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.quartz.service.QuarzRoleTaskService;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.ICrmWindDictService;
import com.deloitte.crm.service.ICrmWindTaskService;
import com.deloitte.system.api.RoleService;
import com.deloitte.system.api.domain.SysDictData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 同步角色任务实现类
 * @date 2022/09/22 21:29
 */
@Slf4j
@Service
public class QuarzRoleTaskImp implements QuarzRoleTaskService {
    @Resource
    private RoleService getRole;
    @Autowired
    private ICrmDailyTaskService crmDailyTaskService;
    @Autowired
    private ICrmWindDictService crmWindDictService;
    @Autowired
    private ICrmWindTaskService crmWindTaskService;

    /**
     *1.每天创建全部角色的任务，默认状态 无任务（1）
     * 2.创建角色1的详细任务 crm_wind_task
     * 3.将角色1当天的 crm_daily_task 状态改成 2
     *
     * @return void
     * @author penTang
     * @date 2022/9/22 21:35
    */
    @Override
    public void executeQuarzRoleTask() {
        List<SysDictData> roleByType = getRole.getRoleByType();
        ArrayList<CrmDailyTask> crmDailyTasks = new ArrayList<>();
        ArrayList<CrmWindTask> crmWindTasks = new ArrayList<>();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);


        for (SysDictData sysDictData : roleByType) {
            CrmDailyTask crmDailyTask = new CrmDailyTask();
            crmDailyTask.setTaskRoleType(sysDictData.getDictValue());
            crmDailyTask.setTaskStatus(1);
            crmDailyTask.setTaskDate(currentTime);
            crmDailyTasks.add(crmDailyTask);
        }
        if (crmDailyTaskService.saveCrmDailyTask(crmDailyTasks)) {
            //.创建角色1的详细任务 crm_wind_task
            List<CrmWindDict> crmWindDicts = crmWindDictService.selectAll();
            for (CrmWindDict crmWindDict : crmWindDicts) {
                CrmWindTask crmWindTask = new CrmWindTask();
                crmWindTask.setTaskCateId(crmWindDict.getCateId());
                crmWindTask.setTaskDictId(crmWindDict.getId());
                crmWindTask.setTaskDesc(crmWindDict.getTaskDesc());
                crmWindTask.setTaskDate(currentTime);
                crmWindTask.setTaskFileName(crmWindDict.getWindFileName());
                crmWindTask.setTaskCategory(crmWindDict.getCateName());
                crmWindTasks.add(crmWindTask);
            }
            if (crmWindTaskService.saveCrmWindTas(crmWindTasks)) {
                if (crmDailyTaskService.updateByType(currentTime)) {
                    log.info("修改状态成功");
                }

            } else {
                log.info("新增失败");
            }

        } else {
            log.info("新增失败");
        }
        crmDailyTasks.clear();
        crmWindTasks.clear();

    }
}
