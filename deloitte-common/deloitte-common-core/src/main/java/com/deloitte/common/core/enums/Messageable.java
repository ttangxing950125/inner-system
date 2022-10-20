package com.deloitte.common.core.enums;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/20/17:04
 * @Description: 通用枚举接口
 */
public interface Messageable extends Serializable {
    String code();

    String message();
}
