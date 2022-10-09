package com.deloitte.crm.service.impl;

import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.service.RoleMainService;
import com.deloitte.system.api.model.LoginUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author 正杰
 * @description: RoleMainServiceImpl
 * @date 2022/9/30
 */
@Component
@AllArgsConstructor
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
}
