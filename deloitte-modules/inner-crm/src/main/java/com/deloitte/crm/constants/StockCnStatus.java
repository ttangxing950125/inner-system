package com.deloitte.crm.constants;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/26
 */
public enum StockCnStatus {

    COACH_BACK(1, "ipo辅导备案中"),
    CHECK_DECLARE(2,"审核申报中"),
    IEC_SMPC_CHECK(3,"法审上市委审核中"),
    APPRD_WAIT_ISS(4,"审核通过尚未发行"),
    ISSUE(5,"发行中"),
    IPO_INFO(6, "成功上市"),
    IPO_PAUSE(7, "发行暂缓"),
    IPO_FAIL(8,"发行失败");

    private final Integer id;

    private final String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    StockCnStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
