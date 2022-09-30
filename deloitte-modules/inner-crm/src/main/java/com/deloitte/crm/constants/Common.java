package com.deloitte.crm.constants;

/**
 * @author 正杰
 * @description: common
 * @date 2022/9/22
 */
public enum Common {

    NULL;
    //        经开区为“GVA”+000001开始排序
    public static final String DOV_INFO_TYPE_JK_CODE = "GVA";
    //        高新区为“GVB”+000001开始排序
    public static final String DOV_INFO_TYPE_GX_CODE = "GVB";
    //        新区为“GVC”+000001开始排序
    public static final String DOV_INFO_TYPE_XQ_CODE = "GVC";
    //        其他类型区域暂以“GVZ”+000001开始排序
    public static final String DOV_INFO_TYPE_QT_CODE = "GVZ";
    //        省级、地级、县级政府为“GV+官方行政代码
    public static final String DOV_INFO_TYPE_PRIVINCE_CODE = "GV";
    //        省
    public static final String DOV_INFO_TYPE_PRIVINCE_NAME = "省";
    //        市
    public static final String DOV_INFO_TYPE_CITY_NAME = "市";
    //        县
    public static final String DOV_INFO_TYPE_AREAXIAN_NAME = "县";
    //        区
    public static final String DOV_INFO_TYPE_AREAQU_NAME = "区";

    public static final String DAY = "DAY";

    public static final String ATTR_FIN = "金融机构表";
    public static final String ATTR_CITY = "城投机构表";
    public static final String ATTR_ISS = "财报收数";

    public static final String MOUTH = "MOUTH";

    public static final Integer FIRST_NUMBER = 0;

    public static final String EMPTY_PARAM = "参数为空";

    public static final String SUCCESS = "操作成功";

    public static final String TABLE_ENTITY_INFO = "ENTITY_INFO";

    public static final String TABLE_BOND_INFO = "BOND_INFO";

    public static final String ENTITY_ATTR_VALUE = "ENTITY_ATTR_VALUE";

    public static final String TRANSACTION_CODE_NAME = "交易代码";
    public static final Integer TRANSACTION_CODE_ID = 804;


    public static final String WHETHER_COLLECTIVE_BONDS = "是否发行集合债";
    public static final Integer WHETHER_COLLECTIVE_BONDS_ID = 58;


    public static final String BOND_NAME = "债券全称";
    public static final Integer BOND_NAME_ID = 48;


    public static final String WHETHER_VIOLATION = "是否违约";
    public static final Integer WHETHER_VIOLATION_ID = 108;


    public static final String WHETHER_ATTR_NAME_SW = "申万(2021)行业划分明细";
    public static final Integer WHETHER_ATTR_NAME_SW_ID = 650;
    public static final Integer WHETHER_ATTR_NAME_SW_ATTR_CATE_ID = 97;


    public static final String WHETHER_ATTR_NAME_WIND = "wind行业划分明细";
    public static final Integer WWHETHER_ATTR_NAME_WIND_ID = 652;
    public static final Integer WWHETHER_ATTR_NAME_WIND_ATTR_CATE_ID = 99;

    public static final String TABLE_CRM_MAS_TASK = "TABLE_CRM_MAS_TASK";
}
