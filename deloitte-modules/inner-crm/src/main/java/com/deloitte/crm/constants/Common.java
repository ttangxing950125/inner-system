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
    public static final  String STOCK_CODE = "债券代码";
    public static final  Integer STOCK_CODE_ID= 49;


     //上市日期对应的att_id
     public static final Integer SHANSI_DATE_ID =583;

    //退市日期对应的att_id
    public static final Integer  TUISI_DATE_ID  = 584;

    //上市板块
    public static final Integer  STOCK_SHANXI =893;

    //交易所
    public static final Integer  EXCHANGE_ID= 903;

    //港股上市日期
    public static final Integer STOCK_SHANXI_DATE_HK_ID =827;

    //港股退市日期
    public static final  Integer  TUISI_DATE_HK_ID =602;

    //港股上市版
    public static final Integer STOCK_SHANXI_HK_ID = 828;

    //港股交易所
    public static final Integer EXCHANGE_HK_ID=980;

    public static final String START_XI_DATE ="起息日";
    public static final Integer START_XI_DATE_ID=52;

    public static final String  STRING_END_DATE="到期日";
    public static final Integer STRING_END_DATE_ID =53;


    public static final Integer BOND_TYPE_ID= 85;
    public static final String  BOND_TYPE="债券类型";

    public static final String  anRportType ="年报列示类型";
    public static final Integer  AN_RPORT_TYPE =680;

    public static final String  FINANCE_SUB_INDU= "金融机构细分行业";
    public static final Integer FINANCE_SUB_INDU_ID =656;



}
