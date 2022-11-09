package com.deloitte.additional.recording.dto;

import com.deloitte.additional.recording.domain.SysMenu;
import com.deloitte.additional.recording.domain.SysUser;

import java.util.List;

public class UserModel {
    private SysUser user;
    private List<String> roleList;
    private String curYear;
    private SysMenu menu;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public SysMenu getMenu() {
        return menu;
    }

    public void setMenu(SysMenu menu) {
        this.menu = menu;
    }

    public String getCurYear() {
        return curYear;
    }

    public void setCurYear(String curYear) {
        this.curYear = curYear;
    }
}
