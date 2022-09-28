package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmWindTask;

import java.util.Date;
import java.util.List;

/**
 * 每日生生成角色任务service接口
 * @author PenTang
 * @date 2022/09/22 15:34
 */
public interface ICrmDailyTaskService extends IService<CrmDailyTask> {

    /**
     * 更新状态为 2-有任务未全部处理完
     * @param timeNow
     * @param roleInfo
     * @return
     */
    boolean updateToUnhandled(Date timeNow, RoleInfo roleInfo);


    /**
     *根据指定日期查询当月的任务(当前登录用户)
     *
     * @param TaskDate
     * @return List<CrmWindTask>
     * @author penTang
     * @date 2022/9/22 10:48
     */
    List<CrmDailyTask> selectCrmDailyTaskListByDate(String TaskDate);

   /**
    *保存每日的角色相关任务
    *
    * @param tasks
    * @return Boolean
    * @author penTang
    * @date 2022/9/22 20:27
   */
    Boolean saveCrmDailyTask(List<CrmDailyTask> tasks);

    /**
     * 根据当天时间和角色1的type更新成有任务未完成(2)
     *
     * @param dateTime
     * @return Boolean
     * @author penTang
     * @date 2022/9/22 20:24
     */
    Boolean updateByType(Date dateTime);
}
