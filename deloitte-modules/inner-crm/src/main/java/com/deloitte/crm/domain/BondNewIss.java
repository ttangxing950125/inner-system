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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 新债发行-新发行债券-20220801-20220914对象 bond_new_iss
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Getter
@Setter
@ToString
public class BondNewIss implements Serializable
{
    private static final Long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer taskId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date importTime;

    private Integer changeType;

    /** 交易代码 */
    @Excel(name = "交易代码")
    private String tradeCode;

    /** 债券简称 */
    @Excel(name = "债券简称")
    private String bondShortName;

    /** 发行起始日 */
    @Excel(name = "发行起始日", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String issStartDate;

    /** 缴款日 */
    @Excel(name = "缴款日", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String payDate;

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
    private Integer payFre;

    /** 公告日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "公告日期", width = 30, dateFormat = "yyyy-MM-dd")
    private String annoDate;

    /** 发行截止日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发行截止日", width = 30, dateFormat = "yyyy-MM-dd")
    private String issEndDate;

    /** 上市日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上市日期", width = 30, dateFormat = "yyyy-MM-dd")
    private String ipoDate;

    /** 上市地点 */
    @Excel(name = "上市地点")
    private String ipoAddr;

    /** 起息日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "起息日", width = 30, dateFormat = "yyyy-MM-dd")
    private String valueDate;

    /** 到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private String expireDate;

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
    @Excel(name = "招标日", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String bidDate;

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
    private String issStatus;

    /** 是否城投债 */
    @Excel(name = "是否城投债")
    private String isCiBond;

    /** 是否增发 */
    @Excel(name = "是否增发")
    private String addIssStatus;

    /** 是否跨市场 */
    @Excel(name = "是否跨市场")
    private String crossMarket;

    /** 面值 */
    @Excel(name = "面值")
    private BigDecimal denom;

    /** 地方政府债新增金额（万） */
    @Excel(name = "地方政府债新增金额（万）")
    private BigDecimal govNewAmount;

    /** 地方政府债置换金额（万） */
    @Excel(name = "地方政府债置换金额（万）")
    private BigDecimal govReplaceAmount;

}
