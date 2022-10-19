package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 可转债发行预案(BondConvertibleInfo)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-13 13:39:43
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BondConvertibleInfo implements Serializable {
    private static final long serialVersionUID = -29871335378267450L;
    /**
     * 主键自动增加
     */
//    @Excel(name = "主键自动增加")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 导入时间
     */
//    @Excel(name = "导入时间,CURRENT_TIMESTAMP")
    private Date importTime;
    /**
     * 1-新增 2-修改
     */
//    @Excel(name = "1-新增 2-修改")
    private Integer changeType;
    /**
     * crm_wind_task的id
     */
//    @Excel(name = "crm_wind_task的id")
    private Integer taskId;
    /**
     * 序号
     */
    @Excel(name = "序号")
    private Integer number;
    /**
     * 公告日期
     */
    @Excel(name = "公告日期")
    private Date noticeDate;
    /**
     * 公司代码
     */
    @Excel(name = "公司代码")
    private String code;
    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String name;
    /**
     * 方案进度
     */
    @Excel(name = "方案进度")
    private String projectProgress;
    /**
     * 预案公告日
     */
    @Excel(name = "预案公告日")
    private Date planDate;
    /**
     * 股东大会公告日
     */
    @Excel(name = "股东大会公告日")
    private Date shareHoldersDate;
    /**
     * 受理日期
     */
    @Excel(name = "受理日期")
    private Date acceptedDate;
    /**
     * 发审委通过公告日
     */
    @Excel(name = "发审委通过公告日")
    private Date iecPassDate;
    /**
     * 审核结果公告日期
     */
    @Excel(name = "审核结果公告日期")
    private Date auditResultsDate;
    /**
     * 证监会核准公告日
     */
    @Excel(name = "证监会核准公告日")
    private Date csrcApproveDate;
    /**
     * 网上申购日
     */
    @Excel(name = "网上申购日")
    private Date interneTappDate;
    /**
     * 网下申购日
     */
    @Excel(name = "网下申购日")
    private Date offlineDate;
    /**
     * 原股东股权登记日
     */
    @Excel(name = "原股东股权登记日")
    private Date registrationShareHoldersDate;
    /**
     * 原股东配售日
     */
    @Excel(name = "原股东配售日")
    private Date stockHoldersDate;
    /**
     * 网上中签率公告日
     */
    @Excel(name = "网上中签率公告日")
    private Date onlineSuccessRateDate;
    /**
     * 网上中签结果公告日
     */
    @Excel(name = "网上中签结果公告日")
    private Date onlineSuccessResultDate;
    /**
     * 网上缴款日
     */
    @Excel(name = "网上缴款日")
    private Date onlinePaymentDate;
    /**
     * 上市日
     */
    @Excel(name = "上市日")
    private Date listingDate;
    /**
     * 发行方式
     */
    @Excel(name = "发行方式")
    private String issuingWay;
    /**
     * 发行期限(年)
     */
    @Excel(name = "发行期限(年)")
    private Integer issuingYear;
    /**
     * 利率类型
     */
    @Excel(name = "利率类型")
    private String issuingType;
    /**
     * 利率(%)
     */
    @Excel(name = "利率(%)")
    private String rate;
    /**
     * 发行规模(亿元)
     */
    @Excel(name = "发行规模(亿元)")
    private BigDecimal issuingScale;
    /**
     * 利率条款
     */
    @Excel(name = "利率条款")
    private String issuingClause;
    /**
     * 付息频率
     */
    @Excel(name = "付息频率")
    private String frequencyPayment;
    /**
     * 付息说明
     */
    @Excel(name = "付息说明")
    private String paymentDesc;
    /**
     * 主承销商
     */
    @Excel(name = "主承销商")
    private String leadUnderwriter;
    /**
     * 赎回条款
     */
    @Excel(name = "赎回条款")
    private String callProvision;

    /**
     * 回售条款
     */
    @Excel(name = "回售条款")
    private String putProvision;
    /**
     * 转股条款
     */
    @Excel(name = "转股条款")
    private String convertibleTerms;
    /**
     * 特别向下修正条款
     */
    @Excel(name = "特别向下修正条款")
    private String downwardRevisionClause;
    /**
     * 证监会行业
     */
    @Excel(name = "证监会行业")
    private String scrcIndustry;
    /**
     * Wind行业
     */
    @Excel(name = "Wind行业")
    private String windIndustry;
    /**
     * 申万行业
     */
    @Excel(name = "申万行业")
    private String allIndustries;
    /**
     * 是否是可转债
     */
    @Excel(name = "是否是可转债")
    private String convertibleBondIs;
    /**
     * 转债代码
     */
    @Excel(name = "转债代码")
    private String bondThatCode;
    /**
     * 转债简称
     */
    @Excel(name = "转债简称")
    private String bondThatName;
    /**
     * 发行时债项评级
     */
    @Excel(name = "发行时债项评级")
    private String issueDateRate;
    /**
     * 转股价格
     */
    @Excel(name = "转股价格")
    private String bookValuePlan;
    /**
     * 发行时正股昨收
     */
    @Excel(name = "发行时正股昨收")
    private String sharesClosedYesterday;
    /**
     * 网上申购代码
     */
    @Excel(name = "网上申购代码")
    private String onlineSubscriptionCode;
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


}
