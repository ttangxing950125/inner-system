package com.deloitte.crm.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import java.util.Objects;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.crm.utils.EqualsUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * IPO-审核申报(CnCheckDeclare)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:47
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CnCheckDeclare implements Serializable {
    private static final long serialVersionUID = -69832136099091659L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * wind_task 的id
     */
    private Integer  taskId ;

    /**
     * 导入日期
     */
    private Date importTime;

    /**
     * 数据变化类型 1-新增 2-更新
     */
    private Integer  changeType;


    /**
     * 代码
     */
    @Excel(name = "代码")
    private String code;
    /**
     * 最新公告日
     */
    @Excel(name = "最新公告日")
    private Date annoDate;
    /**
     * 企业名称
     */
    @Excel(name = "企业名称")
    private String entityName;
    /**
     * 拟上市板
     */
    @Excel(name = "拟上市板")
    private String simulationListed;
    /**
     * 审核状态
     */
    @Excel(name = "审核状态")
    private String auditStatus;
    /**
     * 申报进程
     */
    @Excel(name = "申报进程")
    private String declarationProcess;
    /**
     * 是否已预披露
     */
    @Excel(name = "是否已预披露")
    private String isDisclose;
    /**
     * 转板上市
     */
    @Excel(name = "转板上市")
    private String ipoTransferBoard;
    /**
     * 发行制度
     */
    @Excel(name = "发行制度")
    private String issRule;
    /**
     * 所属行业(CSRC公布)
     */
    @Excel(name = "所属行业(CSRC公布)")
    private String csrcBelIndustry;
    /**
     * 预计发行股数(万股)
     */
    @Excel(name = "预计发行股数(万股)")
    private BigDecimal estIssNum;
    /**
     * 预计发行后总股本(万股)
     */
    @Excel(name = "预计发行后总股本(万股)")
    private BigDecimal issPredicAfterStock;
    /**
     * 拟募集资金(万元)
     */
    @Excel(name = "拟募集资金(万元)")
    private BigDecimal predicFund;
    /**
     * 企业注册地
     */
    @Excel(name = "企业注册地")
    private String entityRegisterAddress;
    /**
     * 城市
     */
    @Excel(name = "城市")
    private String city;
    /**
     * 保荐机构
     */
    @Excel(name = "保荐机构")
    private String sponsorOrgan;
    /**
     * 保荐代表人
     */
    @Excel(name = "保荐代表人")
    private String sponsor;
    /**
     * 保荐项目组成员
     */
    @Excel(name = "保荐项目组成员")
    private String sponsorOrganTeam;
    /**
     * 会计师事务所
     */
    @Excel(name = "会计师事务所")
    private String accountingFirm;
    /**
     * 签字会计师
     */
    @Excel(name = "签字会计师")
    private String signingAccountant;
    /**
     * 律师事务所
     */
    @Excel(name = "律师事务所")
    private String lawFirm;
    /**
     * 签字律师
     */
    @Excel(name = "签字律师")
    private String signingAttorney;
    /**
     * 本次审核状态变更
     */
    @Excel(name = "本次审核状态变更")
    private String auditStatusChange;
    /**
     * 首次公告日 
     */
    @Excel(name = "首次公告日")
    private Date firstAnnoDate;
    /**
     * IPO申报预披露日
     */
    @Excel(name = "IPO申报预披露日")
    private Date ipoDeclareDiscloseDate;
    /**
     * IPO申报稿首次报送时间
     */
    @Excel(name = "IPO申报稿首次报送时间")
    private Date ipoDeclareFirstsendDate;
    /**
     * 受理日期
     */
    @Excel(name = "受理日期")
    private Date acceptDate;
    /**
     * 审核日期
     */
    @Excel(name = "审核日期")
    private Date auditDate;
    /**
     * 核准日期
     */
    @Excel(name = "核准日期")
    private Date calibrationDate;
    /**
     * 最新一年营业收入(万元)
     */
    @Excel(name = "最新一年营业收入(万元)")
    private BigDecimal revenueLastyear;
    /**
     * 最新一年净利润(万元)
     */
    @Excel(name = "最新一年净利润(万元)")
    private BigDecimal netLastyear;

    /**
     * 公司电话
     */
    @Excel(name = "公司电话")
    private String entityPhone;

    /**
     * 公司网址
     */
    @Excel(name = "公司网址")
    private String entityWeb;
    /**
     * 电子邮箱
     */
    @Excel(name = "电子邮箱")
    private String entityEmail;
    /**
     * 注册地址
     */
    @Excel(name = "注册地址")
    private String registerAddress;
    /**
     * 董事会秘书
     */
    @Excel(name = "董事会秘书")
    private String boardSecretary;
    /**
     * Wind行业
     */
    @Excel(name = "Wind行业")
    private String windIndustry;

    @Override
    public boolean equals(Object o) {
        return EqualsUtil.equalsAnnoField(o, this, Excel.class);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, annoDate, entityName, simulationListed, auditStatus, declarationProcess, isDisclose, ipoTransferBoard, issRule, csrcBelIndustry, estIssNum, issPredicAfterStock, predicFund, entityRegisterAddress, city, sponsorOrgan, sponsor, sponsorOrganTeam, accountingFirm, signingAccountant, lawFirm, signingAttorney, auditStatusChange, firstAnnoDate, ipoDeclareDiscloseDate, ipoDeclareFirstsendDate, acceptDate, auditDate, calibrationDate, revenueLastyear, netLastyear, entityPhone, entityWeb, entityEmail, registerAddress, boardSecretary, windIndustry);
    }
}
