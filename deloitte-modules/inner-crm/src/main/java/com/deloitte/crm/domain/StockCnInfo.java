package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;

import com.deloitte.crm.annotation.UpdateLog;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

import javax.validation.constraints.NotNull;

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
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 港股状态
     * 1-聆讯中(XXXX)
     * 2-发行中
     * 3-成功上市
     */
    @Excel(name = "A股状态1-聆讯中(XXXX)            2-发行中            3-成功上市")
    private Integer stockStatus;
    /**
     * 港股状态描述，主要针对聆讯中xxx
     */
    @Excel(name = "A股状态描述，主要针对聆讯中xxx")
    private String statusDesc;
    /**
     * 德勤内部股票代码 ST_0000id 6位数字
     */
    @Excel(name = "德勤内部股票代码 ST_0000id 6位数字")
    private String stockDqCode;

    /**
     * 上市日期
     */
    @ApiModelProperty(value = "上市日期")
    @UpdateLog(fieldName = "A股上市日期",tableFieldName ="list_date")
    private String listDate;

    /**
     * 退市日期
     */
    @ApiModelProperty(value = "退市日期")
    @UpdateLog(fieldName = "A股退市日期",tableFieldName ="delisting_date")
    private String delistingDate;

    /**
     * 交易所
     */
    @ApiModelProperty(value = "交易所")
    @UpdateLog(fieldName = "A股上市交易所",tableFieldName ="exchange")
    private String exchange;
    /**
     * 摘戴帽状态 0 摘帽状态 1 带帽状态
     */
    private Integer stUndoImplemtnet;

    /**
     * 状态 1-删除 0-未删除 默认都是未删除
     */
    private Boolean isDeleted = Boolean.FALSE;
    /**
     * 股票简称
     */
    @ApiModelProperty(value = "股票简称")
    private String stockShortName;
    /**
     * 股票代码
     */
    @Excel(name = "股票代码")
    @UpdateLog(fieldName = "A股股票代码",tableFieldName ="stock_code")
    private String stockCode;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;
}
