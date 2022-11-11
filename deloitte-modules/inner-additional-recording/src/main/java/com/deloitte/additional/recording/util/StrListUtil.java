package com.deloitte.additional.recording.util;

import com.deloitte.common.core.exception.ServiceException;
import org.apache.commons.beanutils.ConvertUtils;

import static com.deloitte.common.core.constant.Constants.DEFAULT_SEPARATOR;

/**
 * @创建人 tangx
 * @创建时间 2022/11/11
 * @描述 字符 list 转换工具
 */
public class StrListUtil {


    public static Integer[] srtToIntArray(String roles) {
        try {
            String[] roleIds = roles.split(DEFAULT_SEPARATOR);
            return (Integer[]) ConvertUtils.convert(roleIds, Integer.class);
        } catch (Exception e) {
            throw new ServiceException("字符串不符合转换格式");
        }
    }
}
