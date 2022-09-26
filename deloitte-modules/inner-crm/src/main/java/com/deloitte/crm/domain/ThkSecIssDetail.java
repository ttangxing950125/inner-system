package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;

/**
 * 证券发行-股票发行-首次发行明细对象 thk_sec_iss_detail
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class ThkSecIssDetail implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setIpoDate(Date ipoDate) 
    {
        this.ipoDate = ipoDate;
    }

    public Date getIpoDate() 
    {
        return ipoDate;
    }
    public void setIpoBoard(String ipoBoard) 
    {
        this.ipoBoard = ipoBoard;
    }

    public String getIpoBoard() 
    {
        return ipoBoard;
    }
    public void setIssType(String issType) 
    {
        this.issType = issType;
    }

    public String getIssType() 
    {
        return issType;
    }
    public void setIssPrice(BigDecimal issPrice) 
    {
        this.issPrice = issPrice;
    }

    public BigDecimal getIssPrice() 
    {
        return issPrice;
    }
    public void setIssCountReal(BigDecimal issCountReal) 
    {
        this.issCountReal = issCountReal;
    }

    public BigDecimal getIssCountReal() 
    {
        return issCountReal;
    }
    public void setIssCountPlan(BigDecimal issCountPlan) 
    {
        this.issCountPlan = issCountPlan;
    }

    public BigDecimal getIssCountPlan() 
    {
        return issCountPlan;
    }
    public void setIssPlacingExceed(BigDecimal issPlacingExceed) 
    {
        this.issPlacingExceed = issPlacingExceed;
    }

    public BigDecimal getIssPlacingExceed() 
    {
        return issPlacingExceed;
    }
    public void setIssFundCount(BigDecimal issFundCount) 
    {
        this.issFundCount = issFundCount;
    }

    public BigDecimal getIssFundCount() 
    {
        return issFundCount;
    }
    public void setIssFundNet(BigDecimal issFundNet) 
    {
        this.issFundNet = issFundNet;
    }

    public BigDecimal getIssFundNet() 
    {
        return issFundNet;
    }
    public void setIssFundExceed(BigDecimal issFundExceed) 
    {
        this.issFundExceed = issFundExceed;
    }

    public BigDecimal getIssFundExceed() 
    {
        return issFundExceed;
    }
    public void setMainUnw(String mainUnw) 
    {
        this.mainUnw = mainUnw;
    }

    public String getMainUnw() 
    {
        return mainUnw;
    }
    public void setSponsor(String sponsor) 
    {
        this.sponsor = sponsor;
    }

    public String getSponsor() 
    {
        return sponsor;
    }
    public void setFirstDenom(BigDecimal firstDenom) 
    {
        this.firstDenom = firstDenom;
    }

    public BigDecimal getFirstDenom() 
    {
        return firstDenom;
    }
    public void setFirstCurrency(String firstCurrency) 
    {
        this.firstCurrency = firstCurrency;
    }

    public String getFirstCurrency() 
    {
        return firstCurrency;
    }
    public void setFirstUnit(String firstUnit) 
    {
        this.firstUnit = firstUnit;
    }

    public String getFirstUnit() 
    {
        return firstUnit;
    }
    public void setProspDate(Date prospDate) 
    {
        this.prospDate = prospDate;
    }

    public Date getProspDate() 
    {
        return prospDate;
    }
    public void setProspPriceMax(BigDecimal prospPriceMax) 
    {
        this.prospPriceMax = prospPriceMax;
    }

    public BigDecimal getProspPriceMax() 
    {
        return prospPriceMax;
    }
    public void setProspPriceMin(BigDecimal prospPriceMin) 
    {
        this.prospPriceMin = prospPriceMin;
    }

    public BigDecimal getProspPriceMin() 
    {
        return prospPriceMin;
    }
    public void setBelWind(String belWind) 
    {
        this.belWind = belWind;
    }

    public String getBelWind() 
    {
        return belWind;
    }
    public void setBelModle(String belModle) 
    {
        this.belModle = belModle;
    }

    public String getBelModle() 
    {
        return belModle;
    }
    public void setStockBelong(String stockBelong) 
    {
        this.stockBelong = stockBelong;
    }

    public String getStockBelong() 
    {
        return stockBelong;
    }
    public void setEntityDes(String entityDes) 
    {
        this.entityDes = entityDes;
    }

    public String getEntityDes() 
    {
        return entityDes;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
