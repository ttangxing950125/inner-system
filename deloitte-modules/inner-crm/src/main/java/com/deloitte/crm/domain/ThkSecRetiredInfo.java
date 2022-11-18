package com.deloitte.crm.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * 证券发行-港股-已退市证券一览(ThkSecRetiredInfo)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-01 11:45:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ThkSecRetiredInfo implements Serializable {
    private static final long serialVersionUID = -72247491153070877L;
    /**
     * 主键自动增加
     */
//    @Excel(name = "主键自动增加")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 导入时间
     */
//    @Excel(name = "导入时间")
    private Date importTime;
    /**
     * 新增 1新增 2修改 null没有变化
     */
//    @Excel(name = "新增 1新增 2修改 null没有变化")
    private Integer changeType;
    /**
     * taskId
     */
//    @Excel(name = "taskId")
    private Integer taskId;
    /**
     * 代码
     */
    @Excel(name = "代码")
    private String code;
    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;
    /**
     * 公司中文名称
     */
    @Excel(name = "公司中文名称")
    private String companyCn;
    /**
     * 公司英文名称
     */
    @Excel(name = "公司英文名称")
    private String companyEnglish;
    /**
     * 法定股本(百万元)
     */
    @Excel(name = "法定股本(百万元)")
    private BigDecimal statutoryCapital;
    /**
     * 币种
     */
    @Excel(name = "币种")
    private String currency;
    /**
     * 发行股本(百万元)
     */
    @Excel(name = "发行股本(百万元)")
    private BigDecimal ssuingEquity;
    /**
     * 主营业务
     */
    @Excel(name = "主营业务")
    private String mainBusiness;
    /**
     * 集团主席
     */
    @Excel(name = "集团主席")
    private String chairmanGroup;
    /**
     * 公司秘书
     */
    @Excel(name = "公司秘书")
    private String companySecretary;
    /**
     * 注册地址
     */
    @Excel(name = "注册地")
    private String registered;
    /**
     * 注册地详细地址
     */
    @Excel(name = "注册地详细地址")
    private String detailedAddress;
    /**
     * 办公地址
     */
    @Excel(name = "办公地址")
    private String officeAddress;
    /**
     * 公司网址
     */
    @Excel(name = "公司网址")
    private String companyWebSite;
    /**
     * 电邮地址
     */
    @Excel(name = "电邮地址")
    private String emailAddress;
    /**
     * 电话
     */
    @Excel(name = "电话")
    private String phone;
    /**
     * 传真
     */
    @Excel(name = "传真")
    private String fax;
    /**
     * 年结日
     */
    @Excel(name = "年结日")
    private String junction;
    /**
     * 所属Wind行业
     */
    @Excel(name = "所属Wind行业")
    private String belWind;
    /**
     * 所属行业(HS)
     */
    @Excel(name = "所属行业(HS)")
    private String hsLndustry;
    /**
     * 摘牌日期
     */
    @Excel(name = "摘牌日期")
    private Date delistingDate;
    /**
     * 终止上市类型
     */
    @Excel(name = "终止上市类型")
    private String terminationType;
    /**
     * 序号
     */
//    @Excel(name = "序号")
//    private String number;
}
