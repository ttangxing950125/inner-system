package com.deloitte.data.platform.domian;

import com.alibaba.fastjson2.JSON;

/**
 * 角色和菜单关联 sys_role_menu
 *
 * @author lipeng
 */
public class SysRoleMenu {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
