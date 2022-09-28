package com.deloitte.crm.constants;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/26
 */
public enum  StockThkStatus {

    LISTEN(1, "聆听中"),
    ISSUE(2,"发行中"),
    LIST(3,"成功上市");

    private final Integer id;

    private final String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    StockThkStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
