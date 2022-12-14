package com.deloitte.crm.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import java.util.Objects;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.crm.utils.EqualsUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (DefaultMoneyTotal)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-09 10:50:32
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DefaultMoneyTotal implements Serializable {
    private static final long serialVersionUID = 522856210100065494L;
    /**
     * id 主键自动递增
     */
//    @Excel(name = "id 主键自动递增")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * wind_task的id
     */
//    @Excel(name = "wind_task的id")
    private Integer taskId;
    /**
     * 导入时间默认当前时间
     */
//    @Excel(name = "导入时间默认当前时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date importTime;
    /**
     * 1-新增 2-修改
     */
//    @Excel(name = "1-新增 2-修改")
    private Integer changeType;
    /**
     * 债券代码
     */
    @Excel(name = "债券代码")
    private String bondCode;
    /**
     * 违约、展期日期
     */
    @Excel(name = "违约、展期日期")
    private Date defaultDate;
    /**
     * 债券简称
     */
    @Excel(name = "债券简称")
    private String bondAbstract;
    /**
     * 发行人
     */
    @Excel(name = "发行人")
    private String publisher;
    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;
    /**
     * 历程
     */
    @Excel(name = "历程")
    private String course;
    /**
     * 最新状态
     */
    @Excel(name = "最新状态")
    private String latestStatus;
    /**
     * 发行规模(亿元)
     */
    @Excel(name = "发行规模(亿元)")
    private BigDecimal offeringSize;
    /**
     * 违约日债券余额(亿)
     */
    @Excel(name = "违约日债券余额(亿)")
    private BigDecimal bondBalanceDefaultDate;
    /**
     * 逾期本金(亿元)
     */
    @Excel(name = "逾期本金(亿元)")
    private BigDecimal defaultPrincipal;
    /**
     * 逾期利息(万元)
     */
    @Excel(name = "逾期利息(万元)")
    private BigDecimal defaultInterest;
    /**
     * 到期日 yyyy-MM-dd
     */
    @Excel(name = "到期日")
    private Date dueDate;
    /**
     * 起息日 yyyy-MM-dd
     */
    @Excel(name = "起息日")
    private Date valueDate;
    /**
     * 剩余期限(年)
     */
    @Excel(name = "剩余期限(年)")
    private String residualMaturity;
    /**
     * 担保人
     */
    @Excel(name = "担保人")
    private String surety;
    /**
     * 违约前主体评级
     */
    @Excel(name = "违约前主体评级")
    private String predefaultSubjectRating;
    /**
     * 违约前债项评级
     */
    @Excel(name = "违约前债项评级")
    private String predefaultDebtRating;
    /**
     * 企业性质
     */
    @Excel(name = "企业性质")
    private String coNature;
    /**
     * 是否上市公司-Y是 N否
     */
    @Excel(name = "是否上市公司")
    private String listedCompany;
    /**
     * 所属行业(Wind)
     */
    @Excel(name = "所属行业(Wind)")
    private String belWind;
    /**
     * 债券类型
     */
    @Excel(name = "债券类型")
    private String bondType;
    /**
     * 交易所
     */
    @Excel(name = "交易所")
    private String exchange;
    /**
     * 创建时间
     */
//    @Excel(name = "创建时间")
    private Date created;
    /**
     * 更新时间
     */
//    @Excel(name = "更新时间")
    private Date updated;


    @Override
    public boolean equals(Object o) {
        return EqualsUtil.equalsAnnoField(this, o, Excel.class);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bondCode, defaultDate, bondAbstract, publisher, type, course, latestStatus, offeringSize, bondBalanceDefaultDate, defaultPrincipal, defaultInterest, dueDate, valueDate, residualMaturity, surety, predefaultSubjectRating, predefaultDebtRating, coNature, listedCompany, belWind, bondType, exchange);
    }
}
