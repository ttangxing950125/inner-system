package com.deloitte.data.platform.enums;

/**
 * 数据层级
 *
 * @author XY
 * @date 2022/11/20
 */
public enum HierarchyEnum {

    ALL(0, "全部"),
    BASE(1, "基础层"),
    MIDDLE(2, "中间层"),
    APPLY(3, "指标层"),
    ENTITY(4, "主体信息");

    HierarchyEnum(Integer code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    private Integer code;

    private String remark;

    public Integer getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }
}
