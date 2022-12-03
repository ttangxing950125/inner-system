package com.deloitte.data.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否枚举
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Getter
@AllArgsConstructor
public enum WhetherEnum {
    /**
     * 0 否，1 是
     */
    FALSE("0", "否"),
    TRUE("1", "是");

    private final String code;
    private final String desc;

    /**
     * 根据code获取值
     * @param code
     * @return
     */
    public static String getDesc(String code) {
        for (WhetherEnum value : WhetherEnum.values()) {
            if (value.getCode().equals(code)){
                return value.desc;
            }
        }
        return null;
    }
}
