package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.crm.annotation.UpdateLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

import javax.validation.constraints.NotNull;

/**
 * 股票信息表(StockThkInfo)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:19
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StockThkInfo implements Serializable {
    private static final long serialVersionUID = 422225653182126009L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 港股状态
     * 1-聆讯中(XXXX)
     * 2-发行中
     * 3-成功上市
     */
    @Excel(name = "港股状态1-聆讯中(XXXX)2-发行中3-成功上市")
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

    /** 上市日期*/
    @ApiModelProperty(value="上市日期")
    @UpdateLog(fieldName = "港股上市时间",tableFieldName ="list_date")
    private String  listDate ;

    /** 退市日期*/
    @ApiModelProperty(value="退市日期")
    @UpdateLog(fieldName = "港股退市时间",tableFieldName ="delisting_date")
    private  String  delistingDate ;

    /** 交易所*/
    @ApiModelProperty(value="交易所")
    @UpdateLog(fieldName = "港股上市交易所",tableFieldName ="exchange")
    private  String  exchange ;

    /**
     * 港股简称
     */
    private String stockName;
    /**
     * 状态 1-删除 0-未删除 默认都是未删除
     */
    private Boolean isDeleted = Boolean.FALSE;
    /**
     * 股票代码
     */
    @Excel(name = "股票代码")
    @UpdateLog(fieldName = "港股股票代码",tableFieldName ="stock_code")
    private String stockCode;
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 上市板
     */
    private String listsector;
}
