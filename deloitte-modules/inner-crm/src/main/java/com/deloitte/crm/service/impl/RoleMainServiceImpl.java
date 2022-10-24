package com.deloitte.crm.service.impl;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.service.RoleMainService;
import com.deloitte.system.api.model.LoginUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * @author 正杰
 * @description: RoleMainServiceImpl
 * @date 2022/9/30
 */
@Component
@AllArgsConstructor
@Slf4j
public class RoleMainServiceImpl implements RoleMainService {


    /**
     *   **********************
     *   *    获取当前用户角色   *
     *   **********************
     *
     * @return 用户角色信息
     */
    @Override
    public String getRoleByUser() {
        LoginUser loginUser = SecurityUtils.getLoginUser();

        return null;
    }

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
    @Override
    public R queryDailyTaskByDay(String taskDate, Integer pageNum, Integer pageSize) {
        pageNum = pageNum==null?10:pageNum;
        pageSize = pageSize==null?1:pageSize;
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Set<String> roles = loginUser.getRoles();

        log.info(" =>> 开始 查询 {} {} 任务  ",roles,taskDate);

        return null;
    }
}
