package com.deloitte.crm.vo;

import com.deloitte.crm.domain.EntityInfoLogs;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/10/14:43
 * @Description:
 */

@Data
@Accessors(chain = true)
public class EntityInfoLogsBySockVo {
    /**
     * 今日新上市主体个数
     */
    private Integer addToday;

    /**
     * 最近七个交易日平均每日新上市主体个数
     */
    private String sevenTradingDayAverages;
    /**
     * 最近一月平均每日新上市主体
     */
    private String averageDailyLatestMonth;
    /**
     * 发债主体 需求待确定 标记 过时
     */
//    @Deprecated
//    private String issueBondTruesEntity;

    /**
     * 收录主体
     */
//    @Deprecated
//    private String shouliEntity;
    /**
     * 实体接
     */
    private List<EntityInfoLogs> entityInfoLogs;
    /**
     * 其余数据
     */
    Map<String, Object> cylinderDatas;

}
