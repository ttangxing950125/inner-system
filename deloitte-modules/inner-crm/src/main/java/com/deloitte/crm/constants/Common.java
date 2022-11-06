package com.deloitte.crm.constants;

/**
 * @author 正杰
 * @description: common
 * @date 2022/9/22
 */
public enum Common {

    /** 通用信息 */
    NULL;
    // 经开区为“GVA”+000001开始排序
    public static final String DOV_INFO_TYPE_JK_CODE = "GVA";
    //  高新区为“GVB”+000001开始排序
    public static final String DOV_INFO_TYPE_GX_CODE = "GVB";
    //  新区为“GVC”+000001开始排序
    public static final String DOV_INFO_TYPE_XQ_CODE = "GVC";
    //  其他类型区域暂以“GVZ”+000001开始排序
    public static final String DOV_INFO_TYPE_QT_CODE = "GVZ";
    //  省级、地级、县级政府为“GV+官方行政代码
    public static final String DOV_INFO_TYPE_PRIVINCE_CODE = "GV";
    //  省
    public static final String DOV_INFO_TYPE_PRIVINCE_NAME = "省";
    //  市
    public static final String DOV_INFO_TYPE_CITY_NAME = "市";
    //  县
    public static final String DOV_INFO_TYPE_AREAXIAN_NAME = "县";
    //  区
    public static final String DOV_INFO_TYPE_AREAQU_NAME = "区";

    public static final String DAY = "DAY";

    public static final String MOUTH = "MOUTH";

    public static final Integer FIRST_NUMBER = 0;

    public static final String EMPTY_PARAM = "参数为空";

    public static final String SUCCESS = "操作成功";

    public static final String TABLE_ENTITY_INFO = "ENTITY_INFO";

    public static final String TABLE_BOND_INFO = "BOND_INFO";

    public static final String ENTITY_ATTR_VALUE = "ENTITY_ATTR_VALUE";

    public static final String  STOCK_TYPE_A= "A";

    public static final String  STOCK_TYPE_G= "G";

    public static final String  CHECK_TYPE1="无法识别";
    public static final String  CHECK_TYPE2="识别成功,已覆盖主体";
    public static final String  CHECK_TYPE3="识别成功,未覆盖主体";
    public static final String  CHECK_TYPE4="一致无冲突";
    public static final String  CHECK_TYPE5="不一致";
    public static final String  CHECK_TYPE6="不适用";
    public static final String  CHECK_TYPE7="未覆盖";
    public static final String  CHECK_TYPE8="匹配冲突,需人工介入";
    public static final String  CHECK_TYPE9="识别失败";

    //股票
    public static final String TYPE_STOCK = "TYPE_STOCK";
    //债券
    public static final String TYPE_BOND = "TYPE_BOND";

    /**
     *  匹配社会信用代码的正则表达式
     */
    public static final String REGEX_CREDIT_CODE = "\\S{18}";



    public static final String REGEX_ENTITY_CODE = "\\S{8}";

    /** 地方行政编码 */
    public static final String REGEX_GOV_CODE = "\\d{6}";

    public static final String ENTITY_CODE = "主体code";

    public static final String UPDATED = "修改时间";

    public static final String CREATED = "创建时间";
    /** 时间 yyyy-MM-dd 格式 */
    public static final String TIME_FORMART_YYYY_MM_DD = "yyyy-MM-dd";
    /** 政府 */
    public static final String GOV = "政府";
    /** 企业 */
    public static final String ENTITY = "企业";
    /** 修改成功 */
    public static final String UPDATE_SUCCESS = "修改成功";
    /** 排除股票证券以 a 开头 */
    public static final String STOCK_BEGIN_WITH_A = "a";
    /** 存续状态 Y */
    public static final String LIVE_STATE = "Y";
    /** 存续状态 N */
    public static final String DEAD_STATE = "N";
    /** 修改名称 */
    public static final String EDITE_NAME = "roleSevenEditeName";
    /** 新增主体 */
    public static final String INSERT_ENTITY = "roleSevenInsertEntity";
    /** 添加曾用名 */
    public static final String INSERT_OLD_NAME = "roleSevenInsertOldName";
    /** 忽略任务 */
    public static final String IGNORE = "roleSevenIgnore";
    /** 删除任务 */
    public static final String DELETE_TASK = "roleSevenDeleteTask";


}
