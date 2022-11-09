package com.deloitte.additional.recording.constants;

/**
 * 常量类
 *
 * @author 冉浩岑
 * @date 2022/11/7 16:38
*/
public enum Common {

    /** 通用信息 */
    NULL;
    /** 默认页码 */
    public static final Integer DEFAUT_PAGE_NUM = 1;
    /** 默认页面size */
    public static final Integer DEFAUT_PAGE_SIZE = 10;
    /** 可查年份 */
    public static final String SEARCH_YEAR = "search_year";
    /** 数据来源 */
    public static final String DATA_SOURCE = "data_source";
    /** 显示类型 */
    public static final String SHOW_TYPE = "show_type";
    /** 限定查询一条 */
    public static final String SQL_LIMIT_ONE = " limit 1";
    /** 操作成功 */
    public static final String DO_SUCCESS = "操作成功";
    /** 新增成功 */
    public static final String INSERT_SUCCESS = "新增成功";
    /** 修改成功 */
    public static final String UPDATE_SUCCESS = "修改成功";
    /** 新增失败 */
    public static final String INSERT_FAIL = "新增失败";
    /** 敞口指标重复 */
    public static final String REPEAT_MAS_EVD = "敞口指标重复";
    /** 请传入参数 */
    public static final String PLEASE_SEND_PARAM = "请传入参数";
    /** 正常 */
    public static final Integer NORMAL = 1;
    /** 删除 */
    public static final Integer DELETE = 0;
}
