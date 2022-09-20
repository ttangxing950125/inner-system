package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.math.BigDecimal;
import java.util.Date;


import com.alibaba.fastjson.JSON;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;

/**
 * IPO-新股发行资料-20210914-20221014对象 cn_ipo_info
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class CnIpoInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 代码 */
    @Excel(name = "代码")
    private String code;

    /** 名称 */
    @Excel(name = "名称")
    private String stockName;

    /** 申购代码 */
    @Excel(name = "申购代码")
    private String subsCode;

    /** 招股日期 yyyy-mm-dd */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "招股日期 yyyy-mm-dd", width = 30, dateFormat = "yyyy-MM-dd")
    private Date prospDate;

    /** 网上发行日期 yyyy-mm-dd */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "网上发行日期 yyyy-mm-dd", width = 30, dateFormat = "yyyy-MM-dd")
    private Date netIssDate;

    /** 上市日期 yyyy-mm-dd */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上市日期 yyyy-mm-dd", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ipoDate;

    /** 上市板 */
    @Excel(name = "上市板")
    private String ipoBoard;

    /** 发行价格 */
    @Excel(name = "发行价格")
    private BigDecimal issPrice;

    /** 发行市盈率 */
    @Excel(name = "发行市盈率")
    private BigDecimal issMult;

    /** 行业PE(披露值) */
    @Excel(name = "行业PE(披露值)")
    private BigDecimal industryPe;

    /** 行业PE(近1月,静态) */
    @Excel(name = "行业PE(近1月,静态)")
    private BigDecimal indusPeStat;

    /** 可比上市公司PE均值（扣非后） */
    @Excel(name = "可比上市公司PE均值", readConverterExp = "扣=非后")
    private BigDecimal ipoPeAvg;

    /** 行业PE(近1月,TTM) */
    @Excel(name = "行业PE(近1月,TTM)")
    private BigDecimal indusPeTtm;

    /** 总计 */
    @Excel(name = "总计")
    private BigDecimal total;

    /** 新股发行数量 */
    @Excel(name = "新股发行数量")
    private BigDecimal newStockIssnub;

    /** 老股转让数量 */
    @Excel(name = "老股转让数量")
    private BigDecimal oldStockConvnub;

    /** 网下配售数量 */
    @Excel(name = "网下配售数量")
    private BigDecimal offlinPlacnub;

    /** 网上发行数量 */
    @Excel(name = "网上发行数量")
    private BigDecimal netIssnub;

    /** 回拨前网上发行股数 */
    @Excel(name = "回拨前网上发行股数")
    private BigDecimal callIssstockBefor;

    /** 回拨后网上发行股数 */
    @Excel(name = "回拨后网上发行股数")
    private BigDecimal callIssstockAfter;

    /** 回拨比例(%) */
    @Excel(name = "回拨比例(%)")
    private BigDecimal callRatio;

    /** 网下申购上限 */
    @Excel(name = "网下申购上限")
    private BigDecimal offlinSubsMax;

    /** 网上发行申购上限 */
    @Excel(name = "网上发行申购上限")
    private BigDecimal netIsssubsMax;

    /** 预计募资(上市公司) */
    @Excel(name = "预计募资(上市公司)")
    private BigDecimal predicFund;

    /** 募资总额(上市公司) */
    @Excel(name = "募资总额(上市公司)")
    private BigDecimal fundTotal;

    /** 募资净额(上市公司) */
    @Excel(name = "募资净额(上市公司)")
    private BigDecimal fundNetAmount;

    /** 项目募投资金(上市公司) */
    @Excel(name = "项目募投资金(上市公司)")
    private BigDecimal prsFund;

    /** 上市公司发行费用 */
    @Excel(name = "上市公司发行费用")
    private BigDecimal ipoIssFees;

    /** 上市公司发行费率(%) */
    @Excel(name = "上市公司发行费率(%)")
    private BigDecimal ipoIssMult;

    /** 老股转让费用 */
    @Excel(name = "老股转让费用")
    private BigDecimal stackConvFees;

    /** 转让费率(%) */
    @Excel(name = "转让费率(%)")
    private BigDecimal convFees;

    /** 发行费用合计 */
    @Excel(name = "发行费用合计")
    private BigDecimal issFeesTotal;

    /** 发行费率(%) */
    @Excel(name = "发行费率(%)")
    private BigDecimal issRates;

    /** 承销及保荐费 */
    @Excel(name = "承销及保荐费")
    private BigDecimal unwSponsorFees;

    /** 审计及验资费 */
    @Excel(name = "审计及验资费")
    private BigDecimal auditVerFees;

    /** 法律费用 */
    @Excel(name = "法律费用")
    private BigDecimal lawFees;

    /** 信息披露费 */
    @Excel(name = "信息披露费")
    private BigDecimal inforDisFees;

    /** 投资者数量(家) */
    @Excel(name = "投资者数量(家)")
    private BigDecimal investorsNub;

    /** 配售对象数量(个) */
    @Excel(name = "配售对象数量(个)")
    private BigDecimal placObjNub;

    /** 询价申购数量(万股) */
    @Excel(name = "询价申购数量(万股)")
    private BigDecimal inquirePurNub;

    /** 询价认购倍数 */
    @Excel(name = "询价认购倍数")
    private BigDecimal inquireSubcMult;

    /** 有效报价配售对象家数 */
    @Excel(name = "有效报价配售对象家数")
    private BigDecimal validQuotePlacnub;

    /** 有效报价投资者户数 */
    @Excel(name = "有效报价投资者户数")
    private BigDecimal validQuoteInvenub;

    /** 有效申购配售对象家数 */
    @Excel(name = "有效申购配售对象家数")
    private BigDecimal validPlacsubsNub;

    /** 有效申购数量(万股) */
    @Excel(name = "有效申购数量(万股)")
    private BigDecimal validSubsNub;

    /** 有效申购资金(亿元) */
    @Excel(name = "有效申购资金(亿元)")
    private BigDecimal validSubsFund;

    /** 有效申购获配比例(%) */
    @Excel(name = "有效申购获配比例(%)")
    private BigDecimal validSubsPlacratio;

    /** 认购倍数(网下) */
    @Excel(name = "认购倍数(网下)")
    private BigDecimal offlinSubsMult;

    /** 战略配售获配股份占比 */
    @Excel(name = "战略配售获配股份占比")
    private BigDecimal tacticPlacStakeratio;

    /** A类法人投资者数量 */
    @Excel(name = "A类法人投资者数量")
    private BigDecimal legalInvesnubA;

    /** B类法人投资者数量 */
    @Excel(name = "B类法人投资者数量")
    private BigDecimal legalInvesnubB;

    /** C类法人投资者数量 */
    @Excel(name = "C类法人投资者数量")
    private BigDecimal legalInvesnubC;

    /** A类法人投资者获配数量总计(万股) */
    @Excel(name = "A类法人投资者获配数量总计(万股)")
    private BigDecimal legalInvesPlacnubA;

    /** B类法人投资者获配数量总计(万股) */
    @Excel(name = "B类法人投资者获配数量总计(万股)")
    private BigDecimal legalInvesPlacnubB;

    /** C类法人投资者获配数量总计(万股) */
    @Excel(name = "C类法人投资者获配数量总计(万股)")
    private BigDecimal legalInvesPlacnubC;

    /** 有效申购户数 */
    @Excel(name = "有效申购户数")
    private BigDecimal validSubsInvenub;

    /** 有效申购股数(万股) */
    @Excel(name = "有效申购股数(万股)")
    private BigDecimal validSubsStakernub;

    /** 中签率(%) */
    @Excel(name = "中签率(%)")
    private BigDecimal checkRatio;

    /** 认购倍数(网上) */
    @Excel(name = "认购倍数(网上)")
    private BigDecimal netCheckRatio;

    /** 发行方式 */
    @Excel(name = "发行方式")
    private String issWay;

    /** 承销方式 */
    @Excel(name = "承销方式")
    private String unwWay;

    /** 主承销商 */
    @Excel(name = "主承销商")
    private String unwMaster;

    /** 保荐机构 */
    @Excel(name = "保荐机构")
    private String sponsorInst;

    /** 审计机构 */
    @Excel(name = "审计机构")
    private String auditInst;

    /** 资产评估机构 */
    @Excel(name = "资产评估机构")
    private String assetsEvaIns;

    /** 律师事务所 */
    @Excel(name = "律师事务所")
    private String lawFirm;

    /** 发行公告日期yyyy-mm-dd */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发行公告日期yyyy-mm-dd", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issAnnoDate;

    /** 网下发行起始日期yyyy-mm-dd */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "网下发行起始日期yyyy-mm-dd", width = 30, dateFormat = "yyyy-MM-dd")
    private Date offlinIssStardate;

    /** 网下发行截止日期yyyy-mm-dd */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "网下发行截止日期yyyy-mm-dd", width = 30, dateFormat = "yyyy-MM-dd")
    private Date offlinIssEnddate;

    /** 网上发行冻结资金(亿元) */
    @Excel(name = "网上发行冻结资金(亿元)")
    private BigDecimal netIssFreeze;

    /** 网下配售冻结资金(亿元) */
    @Excel(name = "网下配售冻结资金(亿元)")
    private BigDecimal offlinPlacFreeze;

    /** 承销商认购余股(万股) */
    @Excel(name = "承销商认购余股(万股)")
    private BigDecimal unwSubcBalanstock;

    /** 包销金额(万元) */
    @Excel(name = "包销金额(万元)")
    private BigDecimal underAmount;

    /** 包销比例 */
    @Excel(name = "包销比例")
    private BigDecimal underRatio;

    /** 面值 */
    @Excel(name = "面值")
    private BigDecimal faceValue;

    /** 省份 */
    @Excel(name = "省份")
    private String province;

    /** 证监会行业(2012版) */
    @Excel(name = "证监会行业(2012版)")
    private String csrcIndustry;

    /** Wind行业 */
    @Excel(name = "Wind行业")
    private String windIndustry;

    /** 证券类型 */
    @Excel(name = "证券类型")
    private String bondType;

    /** 交易所 */
    @Excel(name = "交易所")
    private String bourse;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setStockName(String stockName) 
    {
        this.stockName = stockName;
    }

    public String getStockName() 
    {
        return stockName;
    }
    public void setSubsCode(String subsCode) 
    {
        this.subsCode = subsCode;
    }

    public String getSubsCode() 
    {
        return subsCode;
    }
    public void setProspDate(Date prospDate) 
    {
        this.prospDate = prospDate;
    }

    public Date getProspDate() 
    {
        return prospDate;
    }
    public void setNetIssDate(Date netIssDate) 
    {
        this.netIssDate = netIssDate;
    }

    public Date getNetIssDate() 
    {
        return netIssDate;
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
    public void setIssPrice(BigDecimal issPrice) 
    {
        this.issPrice = issPrice;
    }

    public BigDecimal getIssPrice() 
    {
        return issPrice;
    }
    public void setIssMult(BigDecimal issMult) 
    {
        this.issMult = issMult;
    }

    public BigDecimal getIssMult() 
    {
        return issMult;
    }
    public void setIndustryPe(BigDecimal industryPe) 
    {
        this.industryPe = industryPe;
    }

    public BigDecimal getIndustryPe() 
    {
        return industryPe;
    }
    public void setIndusPeStat(BigDecimal indusPeStat) 
    {
        this.indusPeStat = indusPeStat;
    }

    public BigDecimal getIndusPeStat() 
    {
        return indusPeStat;
    }
    public void setIpoPeAvg(BigDecimal ipoPeAvg) 
    {
        this.ipoPeAvg = ipoPeAvg;
    }

    public BigDecimal getIpoPeAvg() 
    {
        return ipoPeAvg;
    }
    public void setIndusPeTtm(BigDecimal indusPeTtm) 
    {
        this.indusPeTtm = indusPeTtm;
    }

    public BigDecimal getIndusPeTtm() 
    {
        return indusPeTtm;
    }
    public void setTotal(BigDecimal total) 
    {
        this.total = total;
    }

    public BigDecimal getTotal() 
    {
        return total;
    }
    public void setNewStockIssnub(BigDecimal newStockIssnub) 
    {
        this.newStockIssnub = newStockIssnub;
    }

    public BigDecimal getNewStockIssnub() 
    {
        return newStockIssnub;
    }
    public void setOldStockConvnub(BigDecimal oldStockConvnub) 
    {
        this.oldStockConvnub = oldStockConvnub;
    }

    public BigDecimal getOldStockConvnub() 
    {
        return oldStockConvnub;
    }
    public void setOfflinPlacnub(BigDecimal offlinPlacnub) 
    {
        this.offlinPlacnub = offlinPlacnub;
    }

    public BigDecimal getOfflinPlacnub() 
    {
        return offlinPlacnub;
    }
    public void setNetIssnub(BigDecimal netIssnub) 
    {
        this.netIssnub = netIssnub;
    }

    public BigDecimal getNetIssnub() 
    {
        return netIssnub;
    }
    public void setCallIssstockBefor(BigDecimal callIssstockBefor) 
    {
        this.callIssstockBefor = callIssstockBefor;
    }

    public BigDecimal getCallIssstockBefor() 
    {
        return callIssstockBefor;
    }
    public void setCallIssstockAfter(BigDecimal callIssstockAfter) 
    {
        this.callIssstockAfter = callIssstockAfter;
    }

    public BigDecimal getCallIssstockAfter() 
    {
        return callIssstockAfter;
    }
    public void setCallRatio(BigDecimal callRatio) 
    {
        this.callRatio = callRatio;
    }

    public BigDecimal getCallRatio() 
    {
        return callRatio;
    }
    public void setOfflinSubsMax(BigDecimal offlinSubsMax) 
    {
        this.offlinSubsMax = offlinSubsMax;
    }

    public BigDecimal getOfflinSubsMax() 
    {
        return offlinSubsMax;
    }
    public void setNetIsssubsMax(BigDecimal netIsssubsMax) 
    {
        this.netIsssubsMax = netIsssubsMax;
    }

    public BigDecimal getNetIsssubsMax() 
    {
        return netIsssubsMax;
    }
    public void setPredicFund(BigDecimal predicFund) 
    {
        this.predicFund = predicFund;
    }

    public BigDecimal getPredicFund() 
    {
        return predicFund;
    }
    public void setFundTotal(BigDecimal fundTotal) 
    {
        this.fundTotal = fundTotal;
    }

    public BigDecimal getFundTotal() 
    {
        return fundTotal;
    }
    public void setfundNetAmount(BigDecimal fundNetAmount)
    {
        this.fundNetAmount = fundNetAmount;
    }

    public BigDecimal getfundNetAmount()
    {
        return fundNetAmount;
    }
    public void setPrsFund(BigDecimal prsFund) 
    {
        this.prsFund = prsFund;
    }

    public BigDecimal getPrsFund() 
    {
        return prsFund;
    }
    public void setIpoIssFees(BigDecimal ipoIssFees) 
    {
        this.ipoIssFees = ipoIssFees;
    }

    public BigDecimal getIpoIssFees() 
    {
        return ipoIssFees;
    }
    public void setIpoIssMult(BigDecimal ipoIssMult) 
    {
        this.ipoIssMult = ipoIssMult;
    }

    public BigDecimal getIpoIssMult() 
    {
        return ipoIssMult;
    }
    public void setStackConvFees(BigDecimal stackConvFees) 
    {
        this.stackConvFees = stackConvFees;
    }

    public BigDecimal getStackConvFees() 
    {
        return stackConvFees;
    }
    public void setConvFees(BigDecimal convFees) 
    {
        this.convFees = convFees;
    }

    public BigDecimal getConvFees() 
    {
        return convFees;
    }
    public void setIssFeesTotal(BigDecimal issFeesTotal) 
    {
        this.issFeesTotal = issFeesTotal;
    }

    public BigDecimal getIssFeesTotal() 
    {
        return issFeesTotal;
    }
    public void setIssRates(BigDecimal issRates) 
    {
        this.issRates = issRates;
    }

    public BigDecimal getIssRates() 
    {
        return issRates;
    }
    public void setUnwSponsorFees(BigDecimal unwSponsorFees) 
    {
        this.unwSponsorFees = unwSponsorFees;
    }

    public BigDecimal getUnwSponsorFees() 
    {
        return unwSponsorFees;
    }
    public void setAuditVerFees(BigDecimal auditVerFees) 
    {
        this.auditVerFees = auditVerFees;
    }

    public BigDecimal getAuditVerFees() 
    {
        return auditVerFees;
    }
    public void setLawFees(BigDecimal lawFees) 
    {
        this.lawFees = lawFees;
    }

    public BigDecimal getLawFees() 
    {
        return lawFees;
    }
    public void setInforDisFees(BigDecimal inforDisFees) 
    {
        this.inforDisFees = inforDisFees;
    }

    public BigDecimal getInforDisFees() 
    {
        return inforDisFees;
    }
    public void setInvestorsNub(BigDecimal investorsNub) 
    {
        this.investorsNub = investorsNub;
    }

    public BigDecimal getInvestorsNub() 
    {
        return investorsNub;
    }
    public void setPlacObjNub(BigDecimal placObjNub) 
    {
        this.placObjNub = placObjNub;
    }

    public BigDecimal getPlacObjNub() 
    {
        return placObjNub;
    }
    public void setInquirePurNub(BigDecimal inquirePurNub) 
    {
        this.inquirePurNub = inquirePurNub;
    }

    public BigDecimal getInquirePurNub() 
    {
        return inquirePurNub;
    }
    public void setInquireSubcMult(BigDecimal inquireSubcMult) 
    {
        this.inquireSubcMult = inquireSubcMult;
    }

    public BigDecimal getInquireSubcMult() 
    {
        return inquireSubcMult;
    }
    public void setValidQuotePlacnub(BigDecimal validQuotePlacnub) 
    {
        this.validQuotePlacnub = validQuotePlacnub;
    }

    public BigDecimal getValidQuotePlacnub() 
    {
        return validQuotePlacnub;
    }
    public void setValidQuoteInvenub(BigDecimal validQuoteInvenub) 
    {
        this.validQuoteInvenub = validQuoteInvenub;
    }

    public BigDecimal getValidQuoteInvenub() 
    {
        return validQuoteInvenub;
    }
    public void setValidPlacsubsNub(BigDecimal validPlacsubsNub) 
    {
        this.validPlacsubsNub = validPlacsubsNub;
    }

    public BigDecimal getValidPlacsubsNub() 
    {
        return validPlacsubsNub;
    }
    public void setValidSubsNub(BigDecimal validSubsNub) 
    {
        this.validSubsNub = validSubsNub;
    }

    public BigDecimal getValidSubsNub() 
    {
        return validSubsNub;
    }
    public void setValidSubsFund(BigDecimal validSubsFund) 
    {
        this.validSubsFund = validSubsFund;
    }

    public BigDecimal getValidSubsFund() 
    {
        return validSubsFund;
    }
    public void setValidSubsPlacratio(BigDecimal validSubsPlacratio) 
    {
        this.validSubsPlacratio = validSubsPlacratio;
    }

    public BigDecimal getValidSubsPlacratio() 
    {
        return validSubsPlacratio;
    }
    public void setOfflinSubsMult(BigDecimal offlinSubsMult) 
    {
        this.offlinSubsMult = offlinSubsMult;
    }

    public BigDecimal getOfflinSubsMult() 
    {
        return offlinSubsMult;
    }
    public void setTacticPlacStakeratio(BigDecimal tacticPlacStakeratio) 
    {
        this.tacticPlacStakeratio = tacticPlacStakeratio;
    }

    public BigDecimal getTacticPlacStakeratio() 
    {
        return tacticPlacStakeratio;
    }
    public void setLegalInvesnubA(BigDecimal legalInvesnubA) 
    {
        this.legalInvesnubA = legalInvesnubA;
    }

    public BigDecimal getLegalInvesnubA() 
    {
        return legalInvesnubA;
    }
    public void setLegalInvesnubB(BigDecimal legalInvesnubB) 
    {
        this.legalInvesnubB = legalInvesnubB;
    }

    public BigDecimal getLegalInvesnubB() 
    {
        return legalInvesnubB;
    }
    public void setLegalInvesnubC(BigDecimal legalInvesnubC) 
    {
        this.legalInvesnubC = legalInvesnubC;
    }

    public BigDecimal getLegalInvesnubC() 
    {
        return legalInvesnubC;
    }
    public void setLegalInvesPlacnubA(BigDecimal legalInvesPlacnubA) 
    {
        this.legalInvesPlacnubA = legalInvesPlacnubA;
    }

    public BigDecimal getLegalInvesPlacnubA() 
    {
        return legalInvesPlacnubA;
    }
    public void setLegalInvesPlacnubB(BigDecimal legalInvesPlacnubB) 
    {
        this.legalInvesPlacnubB = legalInvesPlacnubB;
    }

    public BigDecimal getLegalInvesPlacnubB() 
    {
        return legalInvesPlacnubB;
    }
    public void setLegalInvesPlacnubC(BigDecimal legalInvesPlacnubC) 
    {
        this.legalInvesPlacnubC = legalInvesPlacnubC;
    }

    public BigDecimal getLegalInvesPlacnubC() 
    {
        return legalInvesPlacnubC;
    }
    public void setValidSubsInvenub(BigDecimal validSubsInvenub) 
    {
        this.validSubsInvenub = validSubsInvenub;
    }

    public BigDecimal getValidSubsInvenub() 
    {
        return validSubsInvenub;
    }
    public void setValidSubsStakernub(BigDecimal validSubsStakernub) 
    {
        this.validSubsStakernub = validSubsStakernub;
    }

    public BigDecimal getValidSubsStakernub() 
    {
        return validSubsStakernub;
    }
    public void setCheckRatio(BigDecimal checkRatio) 
    {
        this.checkRatio = checkRatio;
    }

    public BigDecimal getCheckRatio() 
    {
        return checkRatio;
    }
    public void setNetCheckRatio(BigDecimal netCheckRatio) 
    {
        this.netCheckRatio = netCheckRatio;
    }

    public BigDecimal getNetCheckRatio() 
    {
        return netCheckRatio;
    }
    public void setIssWay(String issWay) 
    {
        this.issWay = issWay;
    }

    public String getIssWay() 
    {
        return issWay;
    }
    public void setUnwWay(String unwWay) 
    {
        this.unwWay = unwWay;
    }

    public String getUnwWay() 
    {
        return unwWay;
    }
    public void setUnwMaster(String unwMaster) 
    {
        this.unwMaster = unwMaster;
    }

    public String getUnwMaster() 
    {
        return unwMaster;
    }
    public void setSponsorInst(String sponsorInst) 
    {
        this.sponsorInst = sponsorInst;
    }

    public String getSponsorInst() 
    {
        return sponsorInst;
    }
    public void setAuditInst(String auditInst) 
    {
        this.auditInst = auditInst;
    }

    public String getAuditInst() 
    {
        return auditInst;
    }
    public void setAssetsEvaIns(String assetsEvaIns) 
    {
        this.assetsEvaIns = assetsEvaIns;
    }

    public String getAssetsEvaIns() 
    {
        return assetsEvaIns;
    }
    public void setLawFirm(String lawFirm) 
    {
        this.lawFirm = lawFirm;
    }

    public String getLawFirm() 
    {
        return lawFirm;
    }
    public void setIssAnnoDate(Date issAnnoDate) 
    {
        this.issAnnoDate = issAnnoDate;
    }

    public Date getIssAnnoDate() 
    {
        return issAnnoDate;
    }
    public void setOfflinIssStardate(Date offlinIssStardate) 
    {
        this.offlinIssStardate = offlinIssStardate;
    }

    public Date getOfflinIssStardate() 
    {
        return offlinIssStardate;
    }
    public void setOfflinIssEnddate(Date offlinIssEnddate) 
    {
        this.offlinIssEnddate = offlinIssEnddate;
    }

    public Date getOfflinIssEnddate() 
    {
        return offlinIssEnddate;
    }
    public void setNetIssFreeze(BigDecimal netIssFreeze) 
    {
        this.netIssFreeze = netIssFreeze;
    }

    public BigDecimal getNetIssFreeze() 
    {
        return netIssFreeze;
    }
    public void setOfflinPlacFreeze(BigDecimal offlinPlacFreeze) 
    {
        this.offlinPlacFreeze = offlinPlacFreeze;
    }

    public BigDecimal getOfflinPlacFreeze() 
    {
        return offlinPlacFreeze;
    }
    public void setUnwSubcBalanstock(BigDecimal unwSubcBalanstock) 
    {
        this.unwSubcBalanstock = unwSubcBalanstock;
    }

    public BigDecimal getUnwSubcBalanstock() 
    {
        return unwSubcBalanstock;
    }
    public void setUnderAmount(BigDecimal underAmount) 
    {
        this.underAmount = underAmount;
    }

    public BigDecimal getUnderAmount() 
    {
        return underAmount;
    }
    public void setUnderRatio(BigDecimal underRatio) 
    {
        this.underRatio = underRatio;
    }

    public BigDecimal getUnderRatio() 
    {
        return underRatio;
    }
    public void setFaceValue(BigDecimal faceValue) 
    {
        this.faceValue = faceValue;
    }

    public BigDecimal getFaceValue() 
    {
        return faceValue;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setCsrcIndustry(String csrcIndustry) 
    {
        this.csrcIndustry = csrcIndustry;
    }

    public String getCsrcIndustry() 
    {
        return csrcIndustry;
    }
    public void setWindIndustry(String windIndustry) 
    {
        this.windIndustry = windIndustry;
    }

    public String getWindIndustry() 
    {
        return windIndustry;
    }
    public void setBondType(String bondType) 
    {
        this.bondType = bondType;
    }

    public String getBondType() 
    {
        return bondType;
    }
    public void setBourse(String bourse) 
    {
        this.bourse = bourse;
    }

    public String getBourse() 
    {
        return bourse;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
