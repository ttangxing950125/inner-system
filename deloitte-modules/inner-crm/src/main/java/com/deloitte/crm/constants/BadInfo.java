package com.deloitte.crm.constants;

import javax.validation.Valid;

/**
 * @author 正杰
 * @description: 坏 信息常量
 * @date 2022/9/22
 */
public enum BadInfo {

    PARAM_EMPTY("参数不能为空"),RESULT_NULL("结果为空")
    ,ERROR_SYSTEM_BUSY("系统繁忙，请稍后再操作"),EXITS_ENTITY_NAME("该主体名称已存在")
    ,EXITS_ENTITY_DIFFERENT_NAME("主体存在，主体名称不同")
    ,EXITS_ENTITY_CODE("该主体已存在，无法新增")
    ,VALID_EMPTY_TARGET("未查询到有效数据"),VALID_PARAM("参数无效")
    ,VALID_EMPTY_USERNAME("未查到当前登录用户名")
    ,EXITS_ENTITY_OLD_NAME("该主体已使用过曾用名");


    public static final Boolean GET = false;

    private final String info;

    BadInfo(String info){
        this.info = info;
    }

    public String getInfo(){return info;}

}
