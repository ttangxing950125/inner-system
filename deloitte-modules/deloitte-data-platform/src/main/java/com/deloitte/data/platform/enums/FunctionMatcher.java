package com.deloitte.data.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 函数匹配器
 *
 * @author XY
 * @date 2022/11/17
 */
@Getter
@AllArgsConstructor
public class FunctionMatcher {

    /**
     * 函数正则枚举
     */

    public enum FunctionRegexEnum {
        /**
         * 上一年
         */
        LAG("log "),
        /**
         * 上上一年（前年）
         */
        SAG("sag ");

        private String regex;

        FunctionRegexEnum(String regex) {
            this.regex = regex;
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }
    }

}
