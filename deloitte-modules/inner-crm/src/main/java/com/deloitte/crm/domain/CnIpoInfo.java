package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import com.deloitte.common.core.annotation.Excel;

/**
 * IPO-新股发行资料-20210914-20221014(CnIpoInfo)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-28 23:14:46
 */
@Getter
@Setter
public class CnIpoInfo implements Serializable {
    private static final long serialVersionUID = -98684665609350571L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    private Integer taskId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date importTime;

    private Integer changeType;

    /**
     * 代码
     */
    @Excel(name = "代码")
    private String code;
    /**
     * 名称
     */
    @Excel(name = "名称")
    private String stockName;
    /**
     * 申购代码
     */
    @Excel(name = "申购代码")
    private String subsCode;
    /**
     * 招股日期
     */
    @Excel(name = "招股日期")
    private Date prospDate;
    /**
     * 网上发行日期
     */
    @Excel(name = "网上发行日期")
    private Date netIssDate;
    /**
     * 上市日期
     */
    @Excel(name = "上市日期")
    private Date ipoDate;
    /**
     * 上市板
     */
    @Excel(name = "上市板")
    private String ipoBoard;
    /**
     * 发行价格
     */
    @Excel(name = "发行价格")
    private Double issPrice;
    /**
     * 发行市盈率
     */
    @Excel(name = "发行市盈率")
    private Double issMult;
    /**
     * 行业PE(披露值)
     */
    @Excel(name = "行业PE(披露值)")
    private Double industryPe;
    /**
     * 行业PE(近1月,静态)
     */
    @Excel(name = "行业PE(近1月,静态)")
    private Double indusPeStat;
    /**
     * 可比上市公司PE均值（扣非后）
     */
    @Excel(name = "可比上市公司PE均值（扣非后）")
    private Double ipoPeAvg;
    /**
     * 行业PE(近1月,TTM)
     */
    @Excel(name = "行业PE(近1月,TTM)")
    private Double indusPeTtm;
    /**
     * 总计
     */
    @Excel(name = "总计")
    private Double total;
    /**
     * 新股发行数量
     */
    @Excel(name = "新股发行数量")
    private Double newStockIssnub;
    /**
     * 老股转让数量
     */
    @Excel(name = "老股转让数量")
    private Double oldStockConvnub;
    /**
     * 网下配售数量
     */
    @Excel(name = "网下配售数量")
    private Double offlinePlacnub;
    /**
     * 网上发行数量
     */
    @Excel(name = "网上发行数量")
    private Double netIssnub;
    /**
     * 回拨前网上发行股数
     */
    @Excel(name = "回拨前网上发行股数")
    private Double callIssstockBefor;
    /**
     * 回拨后网上发行股数
     */
    @Excel(name = "回拨前网下发行股数")
    private Double callIssstockAfter;
    /**
     * 回拨比例(%)
     */
    @Excel(name = "回拨比例(%)")
    private Double callRatio;
    /**
     * 网下申购上限
     */
    @Excel(name = "网下申购上限")
    private Double offlineSubsMax;
    /**
     * 网上发行申购上限
     */
    @Excel(name = "网上发行申购上限")
    private Double onlineIsssubsMax;
    /**
     * 预计募资(上市公司)
     */
    @Excel(name = "预计募资(上市公司)")
    private Double predicFund;
    /**
     * 募资总额(上市公司)
     */
    @Excel(name = "募资总额(上市公司)")
    private Double fundTotal;
    /**
     * 募资净额(上市公司)
     */
    @Excel(name = "募资净额(上市公司)")
    private Double fundNetAmount;
    /**
     * 项目募投资金(上市公司)
     */
    @Excel(name = "项目募投资金(上市公司)")
    private Double prsFund;
    /**
     * 上市公司发行费用
     */
    @Excel(name = "上市公司发行费用")
    private Double ipoIssFees;
    /**
     * 上市公司发行费率(%)
     */
    @Excel(name = "上市公司发行费率(%)")
    private Double ipoIssMult;
    /**
     * 老股转让费用
     */
    @Excel(name = "老股转让费用")
    private Double stackConvFees;
    /**
     * 转让费率(%)
     */
    @Excel(name = "转让费率(%)")
    private Double convRate;
    /**
     * 发行费用合计
     */
    @Excel(name = "发行费用合计")
    private Double issFeesTotal;
    /**
     * 发行费率(%)
     */
    @Excel(name = "发行费率(%)")
    private Double issRate;
    /**
     * 承销及保荐费
     */
    @Excel(name = "承销及保荐费")
    private Double unwSponsorFees;
    /**
     * 审计及验资费
     */
    @Excel(name = "审计及验资费")
    private Double auditVerFees;
    /**
     * 法律费用
     */
    @Excel(name = "法律费用")
    private Double lawFees;
    /**
     * 信息披露费
     */
    @Excel(name = "信息披露费")
    private Double inforDisFees;
    /**
     * 投资者数量(家)
     */
    @Excel(name = "投资者数量(家)")
    private Double investorsNub;
    /**
     * 配售对象数量(个)
     */
    @Excel(name = "配售对象数量(个)")
    private Double placObjNub;
    /**
     * 询价申购数量(万股)
     */
    @Excel(name = "询价申购数量(万股)")
    private Double inquirePurNub;
    /**
     * 询价认购倍数
     */
    @Excel(name = "询价认购倍数")
    private Double inquireSubcMult;
    /**
     * 有效报价配售对象家数
     */
    @Excel(name = "有效报价配售对象家数")
    private Double validQuotePlacnub;
    /**
     * 有效报价投资者户数
     */
    @Excel(name = "有效报价投资者户数")
    private Double validQuoteInvenub;
    /**
     * 有效申购配售对象家数
     */
    @Excel(name = "有效申购配售对象家数")
    private Double validPlacsubsNub;
    /**
     * 有效申购数量(万股)
     */
    @Excel(name = "有效申购数量(万股)")
    private Double validSubsNub;
    /**
     * 有效申购资金(亿元)
     */
    @Excel(name = "有效申购资金(亿元)")
    private Double validSubsFund;
    /**
     * 有效申购获配比例(%)
     */
    @Excel(name = "有效申购获配比例(%)")
    private Double validSubsPlacratio;
    /**
     * 认购倍数(网下)
     */
    @Excel(sort = 47, name = "认购倍数(网下)")
    private Double offlineSubsMult;
    /**
     * 战略配售获配股份占比
     */
    @Excel(name = "战略配售获配股份占比")
    private Double tacticPlacStakeratio;
    /**
     * A类法人投资者数量
     */
    @Excel(name = "A类法人投资者数量")
    private Double legalInvesnubA;
    /**
     * B类法人投资者数量
     */
    @Excel(name = "B类法人投资者数量")
    private Double legalInvesnubB;
    /**
     * C类法人投资者数量
     */
    @Excel(name = "C类法人投资者数量")
    private Double legalInvesnubC;
    /**
     * A类法人投资者获配数量总计(万股)
     */
    @Excel(name = "A类法人投资者获配数量总计(万股)")
    private Double legalInvesPlacnubA;
    /**
     * B类法人投资者获配数量总计(万股)
     */
    @Excel(name = "B类法人投资者获配数量总计(万股)")
    private Double legalInvesPlacnubB;
    /**
     * C类法人投资者获配数量总计(万股)
     */
    @Excel(name = "C类法人投资者获配数量总计(万股)")
    private Double legalInvesPlacnubC;
    /**
     * 有效申购户数
     */
    @Excel(name = "有效申购户数")
    private Double validSubsInvenub;
    /**
     * 有效申购股数(万股)
     */
    @Excel(name = "有效申购股数(万股)")
    private Double validSubsStakernub;
    /**
     * 中签率(%)
     */
    @Excel(name = "中签率(%)")
    private Double checkRatio;
    /**
     * 认购倍数(网上)
     */
    @Excel(sort = 58, name = "认购倍数(网上)")
    private Double netCheckRatio;
    /**
     * 发行方式
     */
    @Excel(name = "发行方式")
    private String issType;
    /**
     * 承销方式
     */
    @Excel(name = "承销方式")
    private String unwType;
    /**
     * 主承销商
     */
    @Excel(name = "主承销商")
    private String mainUnw;
    /**
     * 保荐机构
     */
    @Excel(name = "保荐机构")
    private String sponsorInst;
    /**
     * 审计机构
     */
    @Excel(name = "审计机构")
    private String auditInst;
    /**
     * 资产评估机构
     */
    @Excel(name = "资产评估机构")
    private String assetsEvaIns;
    /**
     * 律师事务所
     */
    @Excel(name = "律师事务所")
    private String lawFirm;
    /**
     * 发行公告日期
     */
    @Excel(name = "发行公告日期")
    private Date issAnnoDate;
    /**
     * 网下发行起始日期
     */
    @Excel(name = "网下发行起始日期")
    private Date offlineIssStardate;
    /**
     * 网下发行截止日期
     */
    @Excel(name = "网下发行截止日期")
    private Date offlineIssEnddate;
    /**
     * 网上发行冻结资金(亿元)
     */
    @Excel(name = "网上发行冻结资金(亿元)")
    private Double netIssFreeze;
    /**
     * 网下配售冻结资金(亿元)
     */
    @Excel(name = "网下配售冻结资金(亿元)")
    private Double offlinePlacFreeze;
    /**
     * 承销商认购余股(万股)
     */
    @Excel(name = "承销商认购余股(万股)")
    private Double unwSubcBalanstock;
    /**
     * 包销金额(万元)
     */
    @Excel(name = "包销金额(万元)")
    private Double underAmount;
    /**
     * 包销比例
     */
    @Excel(name = "包销比例")
    private Double underRatio;
    /**
     * 面值
     */
    @Excel(name = "面值")
    private Double faceValue;
    /**
     * 省份
     */
    @Excel(name = "省份")
    private String province;
    /**
     * 证监会行业(2012版)
     */
    @Excel(name = "证监会行业(2012版)")
    private String csrcIndustry;
    /**
     * Wind行业
     */
    @Excel(name = "Wind行业")
    private String windIndustry;
    /**
     * 证券类型
     */
    @Excel(name = "证券类型")
    private String bondType;
    /**
     * 交易所
     */
    @Excel(name = "交易所")
    private String bourse;


}
