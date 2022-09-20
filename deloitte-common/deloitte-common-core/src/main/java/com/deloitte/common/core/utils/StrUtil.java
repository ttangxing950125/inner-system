package com.deloitte.common.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.deloitte.common.core.constant.Constants;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * 字符串工具类
 *
 * @author lipeng
 */
public class StrUtil extends cn.hutool.core.util.StrUtil {


    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
    public static boolean ishttp(String link) {
        return StrUtil.startWithAny(link, Constants.HTTP, Constants.HTTPS);
    }

    /**
     * 驼峰转下划线命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(C_UNDERLINE);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(C_UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
     *
     * @param str  指定字符串
     * @param strs 需要检查的字符串数组
     * @return 是否匹配
     */
    public static boolean matches(String str, List<String> strs) {
        if (StrUtil.isEmpty(str) || CollUtil.isEmpty(strs)) {
            return false;
        }
        for (String pattern : strs) {
            if (isMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param pattern 匹配规则
     * @param url     需要匹配的url
     * @return
     */
    public static boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    /**
     * 数字左边补齐0，使之达到指定长度。注意，如果数字转换为字符串后，长度大于size，则只保留 最后size个字符。
     *
     * @param num  数字对象
     * @param size 字符串指定长度
     * @return 返回数字的字符串格式，该字符串为指定长度。
     */
    public static final String padl(final Number num, final int size) {
        return padPre(num.toString(), size, '0');
    }

    /**
     * 去掉指定字符串后的空格或者字符
     *
     * @param str        源字符串
     * @param stripChars 指定字符串
     *                   stringUtils.stripEnd(null, *)          = null
     *                   StringUtils.stripEnd("", *)            = ""
     *                   StringUtils.stripEnd("abc", "")        = "abc"
     *                   StringUtils.stripEnd("abc", null)      = "abc"
     *                   StringUtils.stripEnd("  abc", null)    = "  abc"
     *                   StringUtils.stripEnd("abc  ", null)    = "abc"
     *                   StringUtils.stripEnd(" abc ", null)    = " abc"
     *                   StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
     *                   StringUtils.stripEnd("120.00", ".0")   = "12"
     * @return
     */
    public static String stripEnd(final String str, final String stripChars) {
        int end = StrUtil.length(str);
        if (end == 0) {
            return str;
        }

        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != StrUtil.INDEX_NOT_FOUND) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    /**
     * 判断字符串数组中有一个字符串为null或者为空字符串
     *
     * @param strs 字符串数组
     * @return
     */
    public static Boolean isAnyBlank(CharSequence... strs) {
        if (ArrayUtil.isEmpty(strs)) {
            return true;
        }

        for (CharSequence str : strs) {
            if (isNotBlank(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据列表循环替换字符串
     *
     * @param str             源字符串
     * @param searchList      要替换的字符串列表
     * @param replacementList 替换的字符串列表
     * @return
     */
    public static String replaceEach(String str, String[] searchList, String[] replacementList) {
        if (isBlank(str)) {
            return str;
        }
        int searchLength = searchList.length;
        int replacementLength = replacementList.length;
        if (searchLength != replacementLength) {
            throw new IllegalArgumentException(format("检索列表和替换列表长度不匹配: {} vs {}", searchLength, replacementLength));
        }

        for (int i = 0; i < searchLength; i++) {
            str.replaceAll(searchList[i], replacementList[i]);
        }
        return str;
    }
}
