package com.deloitte.crm.constants;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 */
public enum BondStatus {

    WAIT(1,"等待发行"),
    ISSUE(2,"正在发行"),
    WAIT_LIST(3,"已发行待上市"),
    LISTED(4,"成功上市"),
    DELAY_ISSUE(5,"推迟发行"),
    ISSUE_FAIL(6,"发行失败");

    private final int id;

    private final String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    BondStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
