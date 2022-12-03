package com.deloitte.common.core.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通用常量信息
 *
 * @author lipeng
 */
public class Constants {

    /**
     * 默认初始密码
     */
    public static final String PASSWORD = "123456";


    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 登录成功状态
     */
    public static final String LOGIN_SUCCESS_STATUS = "0";

    /**
     * 登录失败状态
     */
    public static final String LOGIN_FAIL_STATUS = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 验证码有效期（分钟）
     */
    public static final long CAPTCHA_EXPIRATION = 2;


    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 定时任务白名单配置（仅允许访问的包名，如其他需要可以自行添加）
     */
    public static final String[] JOB_WHITELIST_STR = {"com.deloitte"};

    /**
     * 定时任务违规的字符
     */
    public static final String[] JOB_ERROR_STR = {"java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache", "com.deloitte.common.core.utils.file"};
    /**
     * 默认分隔符
     */
    public static final String DEFAULT_SEPARATOR = ",";

    /**
     * 政府
     */
    public static final String GOV_TYPE = "3";

    /**
     * 定量
     */
    public static final String QUAN_TYPE = "2";

    /**
     * 定性
     */
    public static final String QUAL_TYPE = "1";

    /**
     * 导入 wind 子表 指标 常量
     */
    public static final List<String> IMPORT_CONTANTS = new ArrayList<String>(Arrays.asList("主体名称", "实体代码", "数据年份"));


    public static final String ENTITY_NAME = "主体名称";


    public static final String ENTITY_CODE = "主体编码";

    public static final String DATA_YEAR = "数据年份";
    public static final String EXAMINE_USER = "examineUser";
    //百分之5
    public static double PERCENT5 = 0.05;

    //百分之25
    public static double PERCENT25 = 0.25;

    //百分之50
    public static double PERCENT50 = 0.50;

    //百分之75
    public static double PERCENT75 = 0.75;
    //百分之95
    public static double PERCENT95 = 0.95;
}
