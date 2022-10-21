package com.deloitte.crm.strategy.enums;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 */
public enum WindTaskEnum {
    CN_IPO_FAIL(5, 2, "A股发行", "IPO-发行失败"),
    CN_IPO_PAUSE(6, 2, "A股发行", "IPO-发行暂缓"),
    CN_IPO_INFO(7, 2, "A股发行", "IPO-新股发行资料"),
    CN_COACH_BACK(1, 2, "A股发行", "IPO-辅导备案"),
    CN_CHECK_DECLARE(2, 2, "A股发行", "IPO-审核申报"),
    CN_IEC_SMPC_CHECK_RESULT(3, 2, "A股发行", "IPO-发审委上市委审核结果"),
    CN_APPRD_WAIT_ISS(4, 2, "A股发行", "IPO-审核通过尚未发行"),
    CN_DELISTINFO(8, 2, "A股发行", "退市资料-仅A股"),

    CN_ST_IMPLEMENT(9, 2, "A股发行", "实施ST"),//带帽
    CN_ST_UNDO(10, 2, "A股发行", "撤消ST"),//摘帽




    BOND_NEW_ISS(15, 2, "债券发行", "新债发行-新发行债券"),
    BOND_DEL_ISS(14, 2, "债券发行", "新债发行-推迟或取消发行债券"),
    BOND_CONVERTIBLE(17, 2, "债券发行", "可转债发行预案"),
    BOND_CONVERTIBLE_CHANGE(18, 2, "债券发行", "可交换转债发行预案"),



    THK_SEC_ISS_INFO(22, 3, "港股发行", "证券发行-股票发行-聆讯信息一览"),
    THK_SEC_ISS_DETAIL(23, 3, "港股发行", "证券发行-股票发行-首次发行明细"),

    BREAK_CONTRACT_FIRST_NUMBER_COUNT(24, 4, "违约", "企业首次违约报表"),
    BREAK_CONTRACT_DEFAULT_MONEY_TOTAL(25, 4, "违约", "历年违约发生金额统计");


    private final Integer id;
    private final Integer cateId;
    private final String cateName;
    private final String windFileName;

    WindTaskEnum(Integer id, Integer cateId, String cateName, String windFileName) {
        this.id = id;
        this.cateId = cateId;
        this.cateName = cateName;
        this.windFileName = windFileName;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCateId() {
        return cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public String getWindFileName() {
        return windFileName;
    }
}
