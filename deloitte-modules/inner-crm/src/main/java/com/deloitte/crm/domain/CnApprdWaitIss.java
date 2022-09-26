package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * IPO-审核通过尚未发行(CnApprdWaitIss)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:46
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CnApprdWaitIss implements Serializable {
    private static final long serialVersionUID = 450243973827664054L;
    /**
     * 主键
     */
    @Excel(name = "主键")
    private Integer id;
    /**
     * 代码
     */
    @Excel(name = "代码")
    private String code;
    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String entityName;
    /**
     * 省份
     */
    @Excel(name = "省份")
    private String province;
    /**
     * 城市
     */
    @Excel(name = "城市")
    private String city;
    /**
     * 审核通过会议日期
     */
    @Excel(name = "审核通过会议日期")
    private Date approvedDate;
    /**
     * 上市板
     */
    @Excel(name = "上市板")
    private String ipoBoard;
    /**
     * 主承销商
     */
    @Excel(name = "主承销商")
    private String mainUnw;
    /**
     * 预计发行股数(万股)
     */
    @Excel(name = "预计发行股数(万股)")
    private Double estIssNum;
    /**
     * 预计募集资金(万元)
     */
    @Excel(name = "预计募集资金(万元)")
    private Double estFundNum;
    /**
     * 交易所
     */
    @Excel(name = "交易所")
    private String exchange;


}
