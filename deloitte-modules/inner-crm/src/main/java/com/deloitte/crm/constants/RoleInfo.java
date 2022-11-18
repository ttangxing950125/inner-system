package com.deloitte.crm.constants;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/24
 */
public enum RoleInfo {
    ROLE1(3,"角色1"),
    ROLE2(4,"角色2"),
    ROLE3(5,"角色3"),
    ROLE4(6,"角色4"),
    ROLE5(7,"角色5"),
    ROLE6(8,"角色6");

    private final int id;

    private final String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    RoleInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
