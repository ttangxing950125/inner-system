package com.deloitte.crm.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/10/14:42
 * @Description:
 */
@Data
@Accessors(chain = true)
public class EntityInfoLogsByBondVo {
    /**
     * 今日新上市主体个数
     */
    private Long addToday;

    /**
     * 最近七个交易日平均每日新上市主体个数
     */
    private Long sevenTradingDayAverages;
    /**
     * 最近一月平均每日新上市主体
     */
    private Long averageDailyLatestMonth;
}
