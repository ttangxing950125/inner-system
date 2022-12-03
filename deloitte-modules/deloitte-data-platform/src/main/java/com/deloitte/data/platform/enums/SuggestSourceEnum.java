package com.deloitte.data.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * '推荐值来源
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Getter
@AllArgsConstructor
public enum SuggestSourceEnum {
    /**
     *   1 wind数据库，2 wind客户端，3 同花顺，4 OCR'
     */
    WIND("1", "wind数据库"),
    WIND_CLIENT("2", "wind客户端"),
    FLUSH("3", "同花顺"),
    OCR("4", "OCR");

    private final String code;
    private final String name;

    /**
     * 根据code获取值
     * @param code
     * @return
     */
    public static SuggestSourceEnum getSuggestSourceEnum(String code) {
        for (SuggestSourceEnum value : SuggestSourceEnum.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

    /**
     * 根据code获取值
     * @param code
     * @return
     */
    public static String getSuggestSourceName(String code) {
        for (SuggestSourceEnum value : SuggestSourceEnum.values()) {
            if (value.getCode().equals(code)){
                return value.getName();
            }
        }
        return null;
    }

}
