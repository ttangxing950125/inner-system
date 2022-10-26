package com.deloitte.crm.constants;

/**
 * @author 正杰
 * @description: 坏 信息常量
 * @date 2022/9/22
 */
public enum BadInfo {

    PARAM_EMPTY("参数不能为空"),
    RESULT_NULL("结果为空"),
    ERROR_SYSTEM_BUSY("系统繁忙，请稍后再操作"),
    EXITS_ENTITY_NAME("该主体名称已存在"),
    EXITS_ENTITY_DIFFERENT_NAME("主体存在，主体名称不同"),
    EXITS_ENTITY_CODE("该主体已存在，无法新增"),
    EXITS_CREDIT_CODE("该统一社会信用代码已存在"),
    EXITS_BOND_CODE("该债券已存在"),
    EXITS_STOCK_CODE("该股票代码重复"),
    EXITS_STOCK_SHO_NAME("该股票简称重复"),
    VALID_EMPTY_TARGET("未查询到有效数据"),
    VALID_PARAM("参数无效"),
    VALID_CREDIT_CODE("统一社会信用代码无效"),
    VALID_ENTITY_NAME("企业主体名称无效"),
    VALID_DATA_CODE("主表code异常"),
    VALID_EMPTY_USERNAME("未查到当前登录用户名"),
    EXITS_ENTITY_OLD_NAME("该主体已使用过曾用名"),
    EXITS_TASK_FINISH("该任务已经完成"),
    EMPTY_TASK_TABLE("未查询到当日任务列表"),
    EMPTY_LOGIN_USER("未能获取到当前角色"),
    EMPTY_BOND_INFO("没有找到债券信息"),
    EMPTY_THK_STOCK_INFO("没有找到港股信息"),
    EMPTY_CN_STOCK_INFO("没有找到A股信息"),
    ERROR_PARAM_DATE("参数日期格式格式错误"),
    PARAM_PROBABLY_BE_EMPTY("参数可能为空"),
    PARAM_PROBABLY_BE_VALIDA("参数可能无效"),
    PARAM_TABLE_COULD_NOT_BE_NULL("参数中的数据库表常量不能为空!"),
    PARAM_ID_COULD_NOT_BE_NULL("参数中的id不能为空!"),
    COULD_NOT_MATCH_TABLE("未能匹配到数据库表"),
    COULD_NOT_FIND_SOURCE("找不到该信息的来源"),
    EMPTY_ENTITY_INFO("未能查到主体表信息"),
    UNABLE_CREAT("查询到重复数据");

    public static final Boolean GET = false;

    private final String info;

    BadInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
