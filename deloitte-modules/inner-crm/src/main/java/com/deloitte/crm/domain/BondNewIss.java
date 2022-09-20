package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;

/**
 * 新债发行-新发行债券-20220801-20220914对象 bond_new_iss
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class BondNewIss extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 交易代码 */
    @Excel(name = "交易代码")
    private String tradeCode;

    /** 债券简称 */
    @Excel(name = "债券简称")
    private String bondShortName;

    /** 发行起始日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发行起始日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issStartDate;

    /** 缴款日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "缴款日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payDate;

    /** 计划发行规模(亿) */
    @Excel(name = "计划发行规模(亿)")
    private BigDecimal issScalePlan;

    /** 发行金额上限(亿) */
    @Excel(name = "发行金额上限(亿)")
    private BigDecimal issAmountTop;

    /** 发行规模(亿) */
    @Excel(name = "发行规模(亿)")
    private BigDecimal issScale;

    /** 发行期限(年) */
    @Excel(name = "发行期限(年)")
    private BigDecimal issTerm;

    /** 特殊期限 */
    @Excel(name = "特殊期限")
    private String specialTerm;

    /** 债券评级 */
    @Excel(name = "债券评级")
    private String bondGrade;

    /** 主体评级 */
    @Excel(name = "主体评级")
    private String enetityGrade;

    /** 票面利率(%) */
    @Excel(name = "票面利率(%)")
    private BigDecimal couponRate;

    /** 增发债发行收益率(%) */
    @Excel(name = "增发债发行收益率(%)")
    private BigDecimal ipoissYield;

    /** 指导利率(%) */
    @Excel(name = "指导利率(%)")
    private BigDecimal guideRate;

    /** 指导利率利差(%) */
    @Excel(name = "指导利率利差(%)")
    private BigDecimal guideRateMargin;

    /** 浮动利率(%) */
    @Excel(name = "浮动利率(%)")
    private BigDecimal floatRate;

    /** 利率类型 */
    @Excel(name = "利率类型")
    private String rateType;

    /** 付息频率 */
    @Excel(name = "付息频率")
    private Long payFre;

    /** 公告日期 yyyy-MM-dd */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "公告日期 yyyy-MM-dd", width = 30, dateFormat = "yyyy-MM-dd")
    private Date annoDate;

    /** 发行截止日期 yyyy-MM-dd */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发行截止日期 yyyy-MM-dd", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issEndDate;

    /** 上市日期 yyyy-MM-dd */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上市日期 yyyy-MM-dd", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ipoDate;

    /** 上市地点 */
    @Excel(name = "上市地点")
    private String ipoAddr;

    /** 起息日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "起息日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date valueDate;

    /** 到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    /** 债券代码列表 */
    @Excel(name = "债券代码列表")
    private String bondCodeList;

    /** 募集资金用途 */
    @Excel(name = "募集资金用途")
    private String fundUse;

    /** 特殊条款 */
    @Excel(name = "特殊条款")
    private String specialRule;

    /** 增信方式 */
    @Excel(name = "增信方式")
    private String addCreditMethod;

    /** 评级机构 */
    @Excel(name = "评级机构")
    private String assessOrgan;

    /** 债券全称 */
    @Excel(name = "债券全称")
    private String bondName;

    /** 发行人简称 */
    @Excel(name = "发行人简称")
    private String issorShortName;

    /** 发行人全称 */
    @Excel(name = "发行人全称")
    private String issorName;

    /** 发行人Wind行业(一级) */
    @Excel(name = "发行人Wind行业(一级)")
    private String issorIndustryFirst;

    /** 发行人Wind行业(二级) */
    @Excel(name = "发行人Wind行业(二级)")
    private String issorIndustrySecond;

    /** 发行人企业性质 */
    @Excel(name = "发行人企业性质")
    private String issorEntityNature;

    /** 发行人省份 */
    @Excel(name = "发行人省份")
    private String issorProvince;

    /** 发行人城市 */
    @Excel(name = "发行人城市")
    private String issorCity;

    /** 担保人 */
    @Excel(name = "担保人")
    private String sponsor;

    /** 发行时担保人评级 */
    @Excel(name = "发行时担保人评级")
    private String issSponsorGrade;

    /** 担保人企业性质 */
    @Excel(name = "担保人企业性质")
    private String sponsorEntityNature;

    /** 担保条款 */
    @Excel(name = "担保条款")
    private String assureRule;

    /** 主承销商 */
    @Excel(name = "主承销商")
    private String mainUnw;

    /** 承销金额主承分摊 */
    @Excel(name = "承销金额主承分摊")
    private String fundShare;

    /** 副主承销商 */
    @Excel(name = "副主承销商")
    private String coUnw;

    /** 簿记管理人 */
    @Excel(name = "簿记管理人")
    private String bookKeeper;

    /** 发行方式 */
    @Excel(name = "发行方式")
    private String issType;

    /** 承销方式 */
    @Excel(name = "承销方式")
    private String unwType;

    /** 发行价格 */
    @Excel(name = "发行价格")
    private BigDecimal issPrice;

    /** 招标标的 */
    @Excel(name = "招标标的")
    private String target;

    /** 招标方式 */
    @Excel(name = "招标方式")
    private String bidType;

    /** 投标区间 */
    @Excel(name = "投标区间")
    private String bidRegion;

    /** 招标日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "招标日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date bidDate;

    /** 招标时间 */
    @Excel(name = "招标时间")
    private String bidTime;

    /** 招标场所 */
    @Excel(name = "招标场所")
    private String bidAddr;

    /** 中标价位 */
    @Excel(name = "中标价位")
    private BigDecimal winBidPrice;

    /** 中标区间 */
    @Excel(name = "中标区间")
    private String winBidRegion;

    /** 认购倍数 */
    @Excel(name = "认购倍数")
    private BigDecimal subcMult;

    /** 加权利率 */
    @Excel(name = "加权利率")
    private BigDecimal weightRate;

    /** 全场倍数 */
    @Excel(name = "全场倍数")
    private BigDecimal fullCourtMult;

    /** 边际利率 */
    @Excel(name = "边际利率")
    private BigDecimal margRate;

    /** 边际倍数 */
    @Excel(name = "边际倍数")
    private BigDecimal margMult;

    /** 发行费率(%) */
    @Excel(name = "发行费率(%)")
    private BigDecimal issRate;

    /** 承销团成员 */
    @Excel(name = "承销团成员")
    private String unwMenber;

    /** 上网发行数量 */
    @Excel(name = "上网发行数量")
    private BigDecimal issOnlineNub;

    /** 上网认购代码 */
    @Excel(name = "上网认购代码")
    private String onlineSubcCode;

    /** Wind债券类型(二级) */
    @Excel(name = "Wind债券类型(二级)")
    private String windBondTypeSecond;

    /** 次级债或混合资本债 */
    @Excel(name = "次级债或混合资本债")
    private String secondMixedCapitalBond;

    /** 是否发行失败 */
    @Excel(name = "是否发行失败")
    private Integer issStatus;

    /** 是否城投债 */
    @Excel(name = "是否城投债")
    private Integer isCiBond;

    /** 是否增发 */
    @Excel(name = "是否增发")
    private Integer addIssStatus;

    /** 是否跨市场 */
    @Excel(name = "是否跨市场")
    private Integer crossMarket;

    /** 面值 */
    @Excel(name = "面值")
    private BigDecimal denom;

    /** 地方政府债新增金额（万） */
    @Excel(name = "地方政府债新增金额", readConverterExp = "万=")
    private BigDecimal govNewAmount;

    /** 地方政府债置换金额（万） */
    @Excel(name = "地方政府债置换金额", readConverterExp = "万=")
    private BigDecimal govReplaceAmount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTradeCode(String tradeCode) 
    {
        this.tradeCode = tradeCode;
    }

    public String getTradeCode() 
    {
        return tradeCode;
    }
    public void setBondShortName(String bondShortName) 
    {
        this.bondShortName = bondShortName;
    }

    public String getBondShortName() 
    {
        return bondShortName;
    }
    public void setIssStartDate(Date issStartDate) 
    {
        this.issStartDate = issStartDate;
    }

    public Date getIssStartDate() 
    {
        return issStartDate;
    }
    public void setPayDate(Date payDate) 
    {
        this.payDate = payDate;
    }

    public Date getPayDate() 
    {
        return payDate;
    }
    public void setIssScalePlan(BigDecimal issScalePlan) 
    {
        this.issScalePlan = issScalePlan;
    }

    public BigDecimal getIssScalePlan() 
    {
        return issScalePlan;
    }
    public void setIssAmountTop(BigDecimal issAmountTop) 
    {
        this.issAmountTop = issAmountTop;
    }

    public BigDecimal getIssAmountTop() 
    {
        return issAmountTop;
    }
    public void setIssScale(BigDecimal issScale) 
    {
        this.issScale = issScale;
    }

    public BigDecimal getIssScale() 
    {
        return issScale;
    }
    public void setIssTerm(BigDecimal issTerm) 
    {
        this.issTerm = issTerm;
    }

    public BigDecimal getIssTerm() 
    {
        return issTerm;
    }
    public void setSpecialTerm(String specialTerm) 
    {
        this.specialTerm = specialTerm;
    }

    public String getSpecialTerm() 
    {
        return specialTerm;
    }
    public void setBondGrade(String bondGrade) 
    {
        this.bondGrade = bondGrade;
    }

    public String getBondGrade() 
    {
        return bondGrade;
    }
    public void setEnetityGrade(String enetityGrade) 
    {
        this.enetityGrade = enetityGrade;
    }

    public String getEnetityGrade() 
    {
        return enetityGrade;
    }
    public void setCouponRate(BigDecimal couponRate) 
    {
        this.couponRate = couponRate;
    }

    public BigDecimal getCouponRate() 
    {
        return couponRate;
    }
    public void setIpoissYield(BigDecimal ipoissYield) 
    {
        this.ipoissYield = ipoissYield;
    }

    public BigDecimal getIpoissYield() 
    {
        return ipoissYield;
    }
    public void setGuideRate(BigDecimal guideRate) 
    {
        this.guideRate = guideRate;
    }

    public BigDecimal getGuideRate() 
    {
        return guideRate;
    }
    public void setGuideRateMargin(BigDecimal guideRateMargin) 
    {
        this.guideRateMargin = guideRateMargin;
    }

    public BigDecimal getGuideRateMargin() 
    {
        return guideRateMargin;
    }
    public void setFloatRate(BigDecimal floatRate) 
    {
        this.floatRate = floatRate;
    }

    public BigDecimal getFloatRate() 
    {
        return floatRate;
    }
    public void setRateType(String rateType) 
    {
        this.rateType = rateType;
    }

    public String getRateType() 
    {
        return rateType;
    }
    public void setPayFre(Long payFre) 
    {
        this.payFre = payFre;
    }

    public Long getPayFre() 
    {
        return payFre;
    }
    public void setAnnoDate(Date annoDate) 
    {
        this.annoDate = annoDate;
    }

    public Date getAnnoDate() 
    {
        return annoDate;
    }
    public void setIssEndDate(Date issEndDate) 
    {
        this.issEndDate = issEndDate;
    }

    public Date getIssEndDate() 
    {
        return issEndDate;
    }
    public void setIpoDate(Date ipoDate) 
    {
        this.ipoDate = ipoDate;
    }

    public Date getIpoDate() 
    {
        return ipoDate;
    }
    public void setIpoAddr(String ipoAddr) 
    {
        this.ipoAddr = ipoAddr;
    }

    public String getIpoAddr() 
    {
        return ipoAddr;
    }
    public void setValueDate(Date valueDate) 
    {
        this.valueDate = valueDate;
    }

    public Date getValueDate() 
    {
        return valueDate;
    }
    public void setExpireDate(Date expireDate) 
    {
        this.expireDate = expireDate;
    }

    public Date getExpireDate() 
    {
        return expireDate;
    }
    public void setBondCodeList(String bondCodeList) 
    {
        this.bondCodeList = bondCodeList;
    }

    public String getBondCodeList() 
    {
        return bondCodeList;
    }
    public void setFundUse(String fundUse) 
    {
        this.fundUse = fundUse;
    }

    public String getFundUse() 
    {
        return fundUse;
    }
    public void setSpecialRule(String specialRule) 
    {
        this.specialRule = specialRule;
    }

    public String getSpecialRule() 
    {
        return specialRule;
    }
    public void setAddCreditMethod(String addCreditMethod) 
    {
        this.addCreditMethod = addCreditMethod;
    }

    public String getAddCreditMethod() 
    {
        return addCreditMethod;
    }
    public void setAssessOrgan(String assessOrgan) 
    {
        this.assessOrgan = assessOrgan;
    }

    public String getAssessOrgan() 
    {
        return assessOrgan;
    }
    public void setBondName(String bondName) 
    {
        this.bondName = bondName;
    }

    public String getBondName() 
    {
        return bondName;
    }
    public void setIssorShortName(String issorShortName) 
    {
        this.issorShortName = issorShortName;
    }

    public String getIssorShortName() 
    {
        return issorShortName;
    }
    public void setIssorName(String issorName) 
    {
        this.issorName = issorName;
    }

    public String getIssorName() 
    {
        return issorName;
    }
    public void setIssorIndustryFirst(String issorIndustryFirst) 
    {
        this.issorIndustryFirst = issorIndustryFirst;
    }

    public String getIssorIndustryFirst() 
    {
        return issorIndustryFirst;
    }
    public void setIssorIndustrySecond(String issorIndustrySecond) 
    {
        this.issorIndustrySecond = issorIndustrySecond;
    }

    public String getIssorIndustrySecond() 
    {
        return issorIndustrySecond;
    }
    public void setIssorEntityNature(String issorEntityNature) 
    {
        this.issorEntityNature = issorEntityNature;
    }

    public String getIssorEntityNature() 
    {
        return issorEntityNature;
    }
    public void setIssorProvince(String issorProvince) 
    {
        this.issorProvince = issorProvince;
    }

    public String getIssorProvince() 
    {
        return issorProvince;
    }
    public void setIssorCity(String issorCity) 
    {
        this.issorCity = issorCity;
    }

    public String getIssorCity() 
    {
        return issorCity;
    }
    public void setSponsor(String sponsor) 
    {
        this.sponsor = sponsor;
    }

    public String getSponsor() 
    {
        return sponsor;
    }
    public void setIssSponsorGrade(String issSponsorGrade) 
    {
        this.issSponsorGrade = issSponsorGrade;
    }

    public String getIssSponsorGrade() 
    {
        return issSponsorGrade;
    }
    public void setSponsorEntityNature(String sponsorEntityNature) 
    {
        this.sponsorEntityNature = sponsorEntityNature;
    }

    public String getSponsorEntityNature() 
    {
        return sponsorEntityNature;
    }
    public void setAssureRule(String assureRule) 
    {
        this.assureRule = assureRule;
    }

    public String getAssureRule() 
    {
        return assureRule;
    }
    public void setMainUnw(String mainUnw) 
    {
        this.mainUnw = mainUnw;
    }

    public String getMainUnw() 
    {
        return mainUnw;
    }
    public void setFundShare(String fundShare) 
    {
        this.fundShare = fundShare;
    }

    public String getFundShare() 
    {
        return fundShare;
    }
    public void setCoUnw(String coUnw) 
    {
        this.coUnw = coUnw;
    }

    public String getCoUnw() 
    {
        return coUnw;
    }
    public void setBookKeeper(String bookKeeper) 
    {
        this.bookKeeper = bookKeeper;
    }

    public String getBookKeeper() 
    {
        return bookKeeper;
    }
    public void setIssType(String issType) 
    {
        this.issType = issType;
    }

    public String getIssType() 
    {
        return issType;
    }
    public void setUnwType(String unwType) 
    {
        this.unwType = unwType;
    }

    public String getUnwType() 
    {
        return unwType;
    }
    public void setIssPrice(BigDecimal issPrice) 
    {
        this.issPrice = issPrice;
    }

    public BigDecimal getIssPrice() 
    {
        return issPrice;
    }
    public void setTarget(String target) 
    {
        this.target = target;
    }

    public String getTarget() 
    {
        return target;
    }
    public void setBidType(String bidType) 
    {
        this.bidType = bidType;
    }

    public String getBidType() 
    {
        return bidType;
    }
    public void setBidRegion(String bidRegion) 
    {
        this.bidRegion = bidRegion;
    }

    public String getBidRegion() 
    {
        return bidRegion;
    }
    public void setBidDate(Date bidDate) 
    {
        this.bidDate = bidDate;
    }

    public Date getBidDate() 
    {
        return bidDate;
    }
    public void setBidTime(String bidTime) 
    {
        this.bidTime = bidTime;
    }

    public String getBidTime() 
    {
        return bidTime;
    }
    public void setBidAddr(String bidAddr) 
    {
        this.bidAddr = bidAddr;
    }

    public String getBidAddr() 
    {
        return bidAddr;
    }
    public void setWinBidPrice(BigDecimal winBidPrice) 
    {
        this.winBidPrice = winBidPrice;
    }

    public BigDecimal getWinBidPrice() 
    {
        return winBidPrice;
    }
    public void setWinBidRegion(String winBidRegion) 
    {
        this.winBidRegion = winBidRegion;
    }

    public String getWinBidRegion() 
    {
        return winBidRegion;
    }
    public void setSubcMult(BigDecimal subcMult) 
    {
        this.subcMult = subcMult;
    }

    public BigDecimal getSubcMult() 
    {
        return subcMult;
    }
    public void setWeightRate(BigDecimal weightRate) 
    {
        this.weightRate = weightRate;
    }

    public BigDecimal getWeightRate() 
    {
        return weightRate;
    }
    public void setFullCourtMult(BigDecimal fullCourtMult) 
    {
        this.fullCourtMult = fullCourtMult;
    }

    public BigDecimal getFullCourtMult() 
    {
        return fullCourtMult;
    }
    public void setMargRate(BigDecimal margRate) 
    {
        this.margRate = margRate;
    }

    public BigDecimal getMargRate() 
    {
        return margRate;
    }
    public void setMargMult(BigDecimal margMult) 
    {
        this.margMult = margMult;
    }

    public BigDecimal getMargMult() 
    {
        return margMult;
    }
    public void setIssRate(BigDecimal issRate) 
    {
        this.issRate = issRate;
    }

    public BigDecimal getIssRate() 
    {
        return issRate;
    }
    public void setUnwMenber(String unwMenber) 
    {
        this.unwMenber = unwMenber;
    }

    public String getUnwMenber() 
    {
        return unwMenber;
    }
    public void setIssOnlineNub(BigDecimal issOnlineNub) 
    {
        this.issOnlineNub = issOnlineNub;
    }

    public BigDecimal getIssOnlineNub() 
    {
        return issOnlineNub;
    }
    public void setOnlineSubcCode(String onlineSubcCode) 
    {
        this.onlineSubcCode = onlineSubcCode;
    }

    public String getOnlineSubcCode() 
    {
        return onlineSubcCode;
    }
    public void setWindBondTypeSecond(String windBondTypeSecond) 
    {
        this.windBondTypeSecond = windBondTypeSecond;
    }

    public String getWindBondTypeSecond() 
    {
        return windBondTypeSecond;
    }
    public void setSecondMixedCapitalBond(String secondMixedCapitalBond) 
    {
        this.secondMixedCapitalBond = secondMixedCapitalBond;
    }

    public String getSecondMixedCapitalBond() 
    {
        return secondMixedCapitalBond;
    }
    public void setIssStatus(Integer issStatus) 
    {
        this.issStatus = issStatus;
    }

    public Integer getIssStatus() 
    {
        return issStatus;
    }
    public void setIsCiBond(Integer isCiBond) 
    {
        this.isCiBond = isCiBond;
    }

    public Integer getIsCiBond() 
    {
        return isCiBond;
    }
    public void setAddIssStatus(Integer addIssStatus) 
    {
        this.addIssStatus = addIssStatus;
    }

    public Integer getAddIssStatus() 
    {
        return addIssStatus;
    }
    public void setCrossMarket(Integer crossMarket) 
    {
        this.crossMarket = crossMarket;
    }

    public Integer getCrossMarket() 
    {
        return crossMarket;
    }
    public void setDenom(BigDecimal denom) 
    {
        this.denom = denom;
    }

    public BigDecimal getDenom() 
    {
        return denom;
    }
    public void setGovNewAmount(BigDecimal govNewAmount) 
    {
        this.govNewAmount = govNewAmount;
    }

    public BigDecimal getGovNewAmount() 
    {
        return govNewAmount;
    }
    public void setGovReplaceAmount(BigDecimal govReplaceAmount) 
    {
        this.govReplaceAmount = govReplaceAmount;
    }

    public BigDecimal getGovReplaceAmount() 
    {
        return govReplaceAmount;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
