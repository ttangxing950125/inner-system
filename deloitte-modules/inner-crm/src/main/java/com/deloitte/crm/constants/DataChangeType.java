package com.deloitte.crm.constants;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/26
 */
public enum  DataChangeType {

    INSERT(1,"新增"),
    UPDATE(2,"更新");

    private final Integer id;

    private final String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    DataChangeType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
