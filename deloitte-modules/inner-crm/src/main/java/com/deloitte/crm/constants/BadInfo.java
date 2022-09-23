package com.deloitte.crm.constants;

/**
 * @author 正杰
 * @description: 坏 信息常量
 * @date 2022/9/22
 */
public enum BadInfo {

    PARAM_EMPTY("参数不能为空"),RESULT_NULL("结果为空")
    ,ERROR_SYSTEM_BUSY("系统繁忙，请稍后再操作"),EXITS_ENTITY_NAME("该主体名称已存在")
    ,EXITS_CREDIT_CODE("该社会信用代码已存在，无法新增");

    public static final Boolean GET = false;

    private final String info;

    BadInfo(String info){
        this.info = info;
    }

    public String getInfo(){return info;};

}
