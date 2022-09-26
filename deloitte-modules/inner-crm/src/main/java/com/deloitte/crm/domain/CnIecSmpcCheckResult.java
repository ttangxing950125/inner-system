package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * IPO-发审委上市委审核结果(CnIecSmpcCheckResult)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:48
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CnIecSmpcCheckResult implements Serializable {
    private static final long serialVersionUID = 187504378729991655L;
    @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 临时代码
     */
    @Excel(name = "临时代码")
    private String tempCode;
    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String entityName;
    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;
    /**
     * 上市板
     */
    @Excel(name = "上市板")
    private String ipoBoard;
    /**
     * 审核结果
     */
    @Excel(name = "审核结果")
    private String checkResult;
    /**
     * 预计发行股数(万股)
     */
    @Excel(name = "预计发行股数(万股)")
    private Double estIssNum;
    /**
     * 预计发行后总股本(万股)
     */
    @Excel(name = "预计发行后总股本(万股)")
    private Double estIssTotalEquity;
    /**
     * 预计募集资金(万元)
     */
    @Excel(name = "预计募集资金(万元)")
    private Double estFundNum;
    /**
     * 会议年度
     */
    @Excel(name = "会议年度")
    private Integer meetingYear;
    /**
     * 会议届次
     */
    @Excel(name = "会议届次")
    private Integer meetingSession;
    /**
     * 会议日期 yyyy-MM-dd
     */
    @Excel(name = "会议日期 yyyy-MM-dd")
    private Date meetingDate;
    /**
     * 审核委员
     */
    @Excel(name = "审核委员")
    private String reviewers;
    /**
     * 发审会问题
     */
    @Excel(name = "发审会问题")
    private String iecQuestion;
    /**
     * 保荐机构
     */
    @Excel(name = "保荐机构")
    private String sponsor;
    /**
     * 会计师事务所
     */
    @Excel(name = "会计师事务所")
    private String accountingFirm;
    /**
     * 律师事务所
     */
    @Excel(name = "律师事务所")
    private String lawFirm;
    /**
     * 省份
     */
    @Excel(name = "省份")
    private String province;
    /**
     * 注册资本(万元)
     */
    @Excel(name = "注册资本(万元)")
    private Double regCapital;
    /**
     * 主要产品及业务
     */
    @Excel(name = "主要产品及业务")
    private String prodBusiness;
    /**
     * 交易所
     */
    @Excel(name = "交易所")
    private String exchange;
    /**
     * 证监会行业
     */
    @Excel(name = "证监会行业")
    private String csrcIndustry;
    /**
     * 被否原因
     */
    @Excel(name = "被否原因")
    private String refuseReason;


}
