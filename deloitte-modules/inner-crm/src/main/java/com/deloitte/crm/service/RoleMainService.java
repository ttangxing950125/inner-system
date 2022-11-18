package com.deloitte.crm.service;

import com.deloitte.common.core.domain.R;

/**
 * @author 正杰
 * @date 2022/9/30
 *
 *   ****************
 *   *    通用方法   *
 *   ****************
 *
 * @description: RoleMainService
 */
public interface RoleMainService {

    /**
     *   **********************
     *   *    获取当前用户角色   *
     *   **********************
     *
     * @return 用户角色信息
     */
    String getRoleByUser();

    /**
     *   ***********************
     *   * 根据当前用户查询每日任务 *
     *   ***********************
     *
     * @param taskDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    R queryDailyTaskByDay(String taskDate, Integer pageNum, Integer pageSize);
}
