package com.deloitte.crm.dto;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 正杰
 * @description: BondTradInfoDto
 * @date 2022/9/29
 */
@Data
@Accessors(chain = true)
public class BondTradInfoDto {

    /**
     * 债务主体德勤代码
     */
    private AttrValueMapDto entityBdCode;

    /**
     * 债务主体名称_分离后
     */
    private AttrValueMapDto entitySeparateName;

    /**
     * 债务主体名称_原始
     */
    private AttrValueMapDto entitySourceName;

    /**
     * 是否为集合债
     */
    private AttrValueMapDto collectiveBonds;

    /**
     * 是否为ABS
     */
    private AttrValueMapDto isAbs;

    /**
     * 债务主体统一社会信用代码
     */
    private AttrValueMapDto creditCode;

    /**
     * 债券交易代码
     */
    private AttrValueMapDto tradCode;

    /**
     * 债券全称
     */
    private AttrValueMapDto bondFullName;

    /**
     * 债券简称
     */
    private AttrValueMapDto bondShortName;

    /**
     * 公私募类型 0_公募 1_私募
     */
    private AttrValueMapDto raiseType;

    /**
     *债卷状态 0_存续 1_违约 2_已兑付
     */
    private AttrValueMapDto bondState;

    /**
     * 起息日
     */
    private AttrValueMapDto valueDate;

    /**
     * 到期兑付日
     */
    private AttrValueMapDto dueCashingDate;

    /**
     * 债务关系有效性 / 存续状态
     */
    private AttrValueMapDto survivalStatus;

    /**
     * 债务关系来源
     */
    private AttrValueMapDto bondSource;

    /**
     * 前发行人名称
     */
    private AttrValueMapDto issuerName;

    /**
     * 前发行人德勤主体代码
     */
    private AttrValueMapDto issuerBdCode;

    /**
     * 前发行人统一社会信用代码
     */
    private AttrValueMapDto issuerCreditCode;

    /**
     * 接收方名称
     */
    private AttrValueMapDto receiverName;

    /**
     * 接收方德勤主体代码
     */
    private AttrValueMapDto receiveBdCode;

    /**
     * 接收方统一社会信用代码
     */
    private AttrValueMapDto receiveCreditCode;

    /**
     * 是否违约
     */
    private AttrValueMapDto whetherViolation;

    /**
     * 违约类型
     */
    private AttrValueMapDto violationType;

    /**
     * 违约日期
     */
    private AttrValueMapDto violationDate;

}
