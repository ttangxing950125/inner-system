package com.deloitte.crm.constants;

/**
 * @author PenTang
 * @date 2022/10/28 10:39
 */
public enum CoverRule {
    NULL;

    /**
     * 覆盖规则是否上市是否上市 0-未上市 1-已上市
     */
    public static final String ESG ="ESG";
    public static final Integer ESG_ID = 7;

    public static final String  CT_LE = "产业链";
    public static final Integer CT_LE_ID = 6;

    public static final String  STOCK = "股票";
    public static final Integer STOCK_ID = 5;


    public static final String  CA_ZP = "财报智评";
    public static final Integer CA_ZP_ID = 4;

    public static final String  HO_SE = "房地产";
    public static final Integer HO_SE_ID = 3;


    public static final String  CH_TO = "城投";
    public static final Integer CH_TO_ID = 2;

    public static final String  IB = "IB评级";
    public static final Integer IB_ID = 1;

    public static final String  COVER_IB_A = "应覆盖(A股上市)";
    public static final String COVER_IB_E  = "已覆盖";
    public static final String  COVER_IB_Bond  = "应覆盖(存续公募债)";
    public static final String  COVER_IB_FIN  = "应覆盖(金融机构)";
    public static final String  COVER_NOT_A  = "不再覆盖(A股退市)";
    public static final String  COVER_NOT_Bond  = "不在覆盖(无存续公募债)";
    public static final String  COVER_NOT = "未覆盖(未A股上市且未发公募债)";


    public  static final String COVER_IBY_QY="一般企业债";
    public  static final String COVER_IBY_GS="一般公司债";
    public  static final String COVER_IBY_DR="一般短期融资券";
    public  static final String COVER_IBY_DRS="超短期融资债券";
    public  static final String COVER_IBY_ZQ="一般中期票据";
    public  static final String COVER_IBY_DX="定向工具";
    public  static final String COVER_IBY_SM="私募债";









}
