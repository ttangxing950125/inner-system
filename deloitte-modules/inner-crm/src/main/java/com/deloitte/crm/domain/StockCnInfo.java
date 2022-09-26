package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * a股信息表，大陆股票(StockCnInfo)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:35
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StockCnInfo implements Serializable {
    private static final long serialVersionUID = -67604867285400904L;
    @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 港股状态
     * 1-聆讯中(XXXX)
     * 2-发行中
     * 3-成功上市
     */
    @Excel(name = "港股状态1-聆讯中(XXXX)            2-发行中            3-成功上市")
    private Integer stockStatus;
    /**
     * 港股状态描述，主要针对聆讯中xxx
     */
    @Excel(name = "港股状态描述，主要针对聆讯中xxx")
    private String statusDesc;
    /**
     * 德勤内部股票代码 ST_0000id 6位数字
     */
    @Excel(name = "德勤内部股票代码 ST_0000id 6位数字")
    private String stockDqCode;
    /**
     * 股票代码
     */
    @Excel(name = "股票代码")
    private String stockCode;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;
}
