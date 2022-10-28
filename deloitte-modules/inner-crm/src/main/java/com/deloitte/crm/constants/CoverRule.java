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

    public static final String  IB = "IB";
    public static final Integer IB_ID = 1;

}
