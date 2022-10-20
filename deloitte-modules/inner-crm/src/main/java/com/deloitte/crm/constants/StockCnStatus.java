package com.deloitte.crm.constants;

import com.deloitte.common.core.enums.Messageable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/26
 */
public enum StockCnStatus implements Messageable {

    COACH_BACK(1, "IPO辅导备案中"),
    CHECK_DECLARE(2, "IPO审核申报中"),
    IEC_SMPC_CHECK(3, "IPO法审上市委审核中"),
    APPRD_WAIT_ISS(4, "IPO审核通过尚未发行"),
    ISSUE(5, "发行中"),
    IPO_INFO(6, "成功上市"),
    IPO_PAUSE(7, "发行暂缓"),
    IPO_FAIL(8, "发行失败");

    private final Integer code;
    private final String message;

    StockCnStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }


    /**
     * 根据code获取枚举
     * @param code code值
     * @return 枚举
     */
    public static StockCnStatus getOrderLifeEnum(Integer code) {
        for (StockCnStatus stockCnStatus : StockCnStatus.values()) {
            if (stockCnStatus.code.equals(code)) {
                return stockCnStatus;
            }
        }
        return null;
    }

    @Override
    public String code() {
        return String.valueOf(this.code);
    }

    @Override
    public String message() {
        return this.message;
    }
}
