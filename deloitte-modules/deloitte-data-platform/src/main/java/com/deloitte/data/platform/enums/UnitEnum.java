package com.deloitte.data.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 单位枚举
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Getter
@AllArgsConstructor
public enum UnitEnum {
    /**
     * 单位  1 元，2 万元，3 亿元，4 数值，5 文本，6 百分比
     */
    YUAN("1", "元"),
    WAN_YUAN("2", "万元"),
    YI_YUAN("3", "亿元"),
    NUMERICAL("4", "数值"),
    TEXT("5", "文本"),
    PERCENT("6", "百分比");

    private final String code;
    private final String desc;

    /**
     * 根据code获取单位
     * @param code
     * @return
     */
    public static String getUnitDesc(String code) {
        for (UnitEnum value : UnitEnum.values()) {
            if (value.getCode().equals(code)){
                return value.desc;
            }
        }
        return null;
    }
}
