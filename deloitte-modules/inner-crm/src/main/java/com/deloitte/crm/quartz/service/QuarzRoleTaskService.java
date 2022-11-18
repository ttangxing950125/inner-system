package com.deloitte.crm.quartz.service;

/**
 * @author PenTang
 * @date 2022/09/22 21:30
 */
public interface QuarzRoleTaskService {
    /**
     *1.每天创建全部角色的任务，默认状态 无任务（1）
     * 2.创建角色1的详细任务 crm_wind_task
     * 3.将角色1当天的 crm_daily_task 状态改成 2
     *
     * @return void
     * @author penTang
     * @date 2022/9/22 21:33
    */
    public void executeQuarzRoleTask();
}
