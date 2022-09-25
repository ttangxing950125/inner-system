package com.deloitte.crm.strategy.enums;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 */
public enum WindTaskEnum {
    NEW_ISSUE(15,2,"债券发行","新债发行-新发行债券"),
    CANCEL_ISSUE(14,2,"债券发行","新债发行-推迟或取消发行债券");



    private final Integer id;
    private final Integer cateId;
    private final String cateName;
    private final String windFileName;

    WindTaskEnum(Integer id, Integer cateId, String cateName, String windFileName) {
        this.id = id;
        this.cateId = cateId;
        this.cateName = cateName;
        this.windFileName = windFileName;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCateId() {
        return cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public String getWindFileName() {
        return windFileName;
    }
}
