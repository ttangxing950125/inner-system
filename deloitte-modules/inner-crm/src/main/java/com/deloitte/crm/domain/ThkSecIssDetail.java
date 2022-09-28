package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 证券发行-股票发行-首次发行明细对象 thk_sec_iss_detail
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Getter
@Setter
@ToString
public class ThkSecIssDetail implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer taskId;

    private Date importTime;

    private Integer changeType;

    @Excel(name = "代码")
    private String code;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 上市日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上市日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ipoDate;

    /** 上市版 */
    @Excel(name = "上市板")
    private String ipoBoard;

    /** 发行方式 */
    @Excel(name = "发行方式")
    private String issType;

    /** 发行价格(港元) */
    @Excel(name = "发行价格(港元)")
    private BigDecimal issPrice;

    /** 实际发行总数(百万股) */
    @Excel(name = "实际发行总数(百万股)")
    private BigDecimal issCountReal;

    /** 计划发行总数(百万股) */
    @Excel(name = "计划发行总数(百万股)")
    private BigDecimal issCountPlan;

    /** 超额配售数量(百万股) */
    @Excel(name = "超额配售数量(百万股)")
    private BigDecimal issPlacingExceed;

    /** 首发/预计募资总额(百万港元) */
    @Excel(name = "首发/预计募资总额(百万港元)")
    private BigDecimal issFundCount;

    /** 发售募资净额(百万港元) */
    @Excel(name = "发售募资净额(百万港元)")
    private BigDecimal issFundNet;

    /** 超额配售募资净额(百万港元) */
    @Excel(name = "超额配售募资净额(百万港元)")
    private BigDecimal issFundExceed;

    /** 主承销商 */
    @Excel(name = "主承销商")
    private String mainUnw;

    /** 保荐人 */
    @Excel(name = "保荐人")
    private String sponsor;

    /** 发售面值 */
    @Excel(name = "首发面值")
    private BigDecimal firstDenom;

    /** 面值货币 */
    @Excel(name = "面值货币")
    private String firstCurrency;

    /** 首发交易单位(股) */
    @Excel(name = "首发交易单位(股)")
    private String firstUnit;

    /** 招股公告日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "招股公告日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date prospDate;

    /** 招股价区间上限(港元) */
    @Excel(name = "招股价区间上限(港元)")
    private BigDecimal prospPriceMax;

    /** 招股价区间下限(港元) */
    @Excel(name = "招股价区间下限(港元)")
    private BigDecimal prospPriceMin;

    /** 所属Wind行业 */
    @Excel(name = "所属Wind行业")
    private String belWind;

    /** 所属行业(HS) */
    @Excel(name = "所属行业(HS)")
    private String belModle;

    /** H股/红筹股 */
    @Excel(name = "H股/红筹股")
    private String stockBelong;

    /** 公司介绍 */
    @Excel(name = "公司介绍")
    private String entityDes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThkSecIssDetail that = (ThkSecIssDetail) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(ipoDate, that.ipoDate) &&
                Objects.equals(ipoBoard, that.ipoBoard) &&
                Objects.equals(issType, that.issType) &&
                Objects.equals(issPrice, that.issPrice) &&
                Objects.equals(issCountReal, that.issCountReal) &&
                Objects.equals(issCountPlan, that.issCountPlan) &&
                Objects.equals(issPlacingExceed, that.issPlacingExceed) &&
                Objects.equals(issFundCount, that.issFundCount) &&
                Objects.equals(issFundNet, that.issFundNet) &&
                Objects.equals(issFundExceed, that.issFundExceed) &&
                Objects.equals(mainUnw, that.mainUnw) &&
                Objects.equals(sponsor, that.sponsor) &&
                Objects.equals(firstDenom, that.firstDenom) &&
                Objects.equals(firstCurrency, that.firstCurrency) &&
                Objects.equals(firstUnit, that.firstUnit) &&
                Objects.equals(prospDate, that.prospDate) &&
                Objects.equals(prospPriceMax, that.prospPriceMax) &&
                Objects.equals(prospPriceMin, that.prospPriceMin) &&
                Objects.equals(belWind, that.belWind) &&
                Objects.equals(belModle, that.belModle) &&
                Objects.equals(stockBelong, that.stockBelong) &&
                Objects.equals(entityDes, that.entityDes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, ipoDate, ipoBoard, issType, issPrice, issCountReal, issCountPlan, issPlacingExceed, issFundCount, issFundNet, issFundExceed, mainUnw, sponsor, firstDenom, firstCurrency, firstUnit, prospDate, prospPriceMax, prospPriceMin, belWind, belModle, stockBelong, entityDes);
    }
}
