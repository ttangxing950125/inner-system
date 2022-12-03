package com.deloitte.common.core.utils;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class ListUtil {

    public static <T, D> List<T> listCover(Class<T> clazz, List<D> list) {
        List<T> voList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (D d : list) {
                try {
                    T t = clazz.newInstance();
                    BeanUtils.copyProperties(d, t);
                    voList.add(t);
                } catch (Exception e) {
                    log.warn("listCover数据类型转换出错");
                }
            }
        }
        return voList;
    }

    /**
     * 提取对象数组的ID数组
     *
     * @param classz
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<String> getIdList(Class<T> classz, List<T> list) {
        List<String> idList = new ArrayList<>();
        try {
            Method method = classz.getMethod("getId");
            for (T t : list) {
                String id = (String) method.invoke(t);
                idList.add(id);
            }
        } catch (Exception e) {
            log.warn("获取ID数组出错");
        }
        return idList;
    }

    /**
     * 提取对象数组的ID数组
     *
     * @param classz
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<String> getIdList(Class<T> classz, Collection<T> list, String idField) {
        List<String> idList = new ArrayList<>();
        if (null != list && list.size() > 0) {
            try {
                if (ObjectUtils.isNotEmpty(idField)) {
                    char[] cs = idField.toCharArray();
                    cs[0] -= 32;
                    Method method = classz.getMethod("get" + String.valueOf(cs));
                    for (T t : list) {
                        String id = method.invoke(t).toString();
                        idList.add(id);
                    }
                }

            } catch (Exception e) {
                log.warn("获取ID数组出错");
            }
        }
        return idList;
    }

    /**
     * 字符串转换List<Long>
     *
     * @param str
     * @return
     */
    public static List<Long> stringCoverLongList(String str) {
        List<Long> idList = new ArrayList<>();
        if (commaLinksCheck(str)) {
            String[] strIds = StringUtils.split(str, ",");
            for (String strId : strIds) {
                idList.add(Long.parseLong(strId));
            }
        }
        return idList;
    }

    /**
     * 字符串转换List<String>
     *
     * @param str
     * @return
     */
    public static List<String> commaLinksToList(String str) {
        List<String> stringList = new ArrayList<>();
        if (commaLinksCheck(str)) {
            String[] strIds = StringUtils.split(str, ",");
            for (String strId : strIds) {
                stringList.add(strId);
            }
        }
        return stringList;
    }

    /**
     * 检查是否标准的:1,2,3,4,5的字符串拼接
     *
     * @param content
     * @return
     */
    public static boolean commaLinksCheck(String content) {
        boolean bool = false;
        if (!StringUtils.isEmpty(content)) {
            bool = Pattern.matches("^(\\w+,)*\\w+$", content);
        }
        return bool;
    }

    public static String listCoverString(List<String> temp, String split) {
        StringBuilder sb = new StringBuilder();
        for (String tmp : temp) {
            sb.append(tmp).append(split);
        }
        return sb.toString();
    }

    //a排除重复数组b
    public static void removal(List<String> a, List<String> b) {
        List<String> repeat = new ArrayList<>();
        for (String st : a) {
            for (String st2 : b) {
                if (st.equals(st2)) {
                    repeat.add(st);
                }
            }
        }
        a.removeAll(repeat);
    }
}
