package com.deloitte.system.domain;

import com.alibaba.fastjson2.JSON;

/**
 * 用户和角色关联 sys_user_role
 *
 * @author lipeng
 */
public class SysUserRole {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
