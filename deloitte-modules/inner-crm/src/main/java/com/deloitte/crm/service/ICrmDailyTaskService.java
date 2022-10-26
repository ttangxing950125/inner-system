package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmSupplyTask;

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

    /**
     * 指定日期查询各角色当月任务完成情况
     *
     * @return R
     * @author penTang
     * @editeBy 正杰
     * @date 2022/9/21 18:06
     * @editeDate 2022/9/29
     *
     */
    R<List<CrmDailyTask>> queryDailyTask(String taskDate, Long userId);

    /**
     * 传入对应参数 当月任务列表进行新增或修改
     * @author 正杰
     * @param taskRoleType
     * @param taskStatus
     * @param date
     */
    void saveTask(Integer taskRoleType,Integer taskStatus,String date);
    /**
     * 检验是否完成每日 supply 任务，更新 dailyTask
     *
     * @param crmSupplyTask
     * @return void
     * @author 冉浩岑
     * @date 2022/10/25 21:32
    */
    void checkDailyTask(CrmSupplyTask crmSupplyTask);
}
