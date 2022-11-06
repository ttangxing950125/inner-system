package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (TranspreQualinfo3rd)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TranspreQualinfo3rd implements Serializable {
    private static final long serialVersionUID = -52051337566664886L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 补录版本id
     */     @Excel(name = "补录版本id")
    private Integer plusVersion;
    /**
     * 补录敞口id
     */     @Excel(name = "补录敞口id")
    private Integer plusModelid;
    /**
     * 补录指标id
     */     @Excel(name = "补录指标id")
    private Integer plusQualid;
    /**
     * 补录evd_id，定性为0，定量要去关联出来
     */     @Excel(name = "补录evd_id，定性为0，定量要去关联出来")
    private Integer plusRuleid;
    /**
     * 单位，定性为空，定量要去关联出来
     */     @Excel(name = "单位，定性为空，定量要去关联出来")
    private String plusUnit;
    /**
     * (A)
     */     @Excel(name = "(A)")
    private String plusOption;
    /**
     * 值转换倍率，p*pt_times = t  (单位为%,带公式的数据库存的为小数值，不用乘以0.01)
     */     @Excel(name = "值转换倍率，p*pt_times = t  (单位为%,带公式的数据库存的为小数值，不用乘以0.01)")
    private Double ptTimes;
         @Excel(name = "${column.comment}")
    private Integer pareQualinfo;
    /**
     * 保留小数位数，null不做限制
     */     @Excel(name = "保留小数位数，null不做限制")
    private Integer decPlace;
    /**
     * 版本的前缀
     */     @Excel(name = "版本的前缀")
    private String prefix;
         @Excel(name = "${column.comment}")
    private Date addTime;
    /**
     * 中心库敞口id
     */     @Excel(name = "中心库敞口id")
    private String tarMid;
    /**
     * 中心指标名称
     */     @Excel(name = "中心指标名称")
    private String tarQualname;
    /**
     * 中心指标id
     */     @Excel(name = "中心指标id")
    private String tarQualid;
    /**
     * 1-定性，2-定量，3-政府
     */     @Excel(name = "1-定性，2-定量，3-政府")
    private String tarType;
    /**
     * 目标表拼接单位
     */     @Excel(name = "目标表拼接单位")
    private String tarUnit;
    /**
     * 真实数据单位
     */     @Excel(name = "真实数据单位")
    private String tarDataUnit;
    /**
     * 中心库敞口
     */     @Excel(name = "中心库敞口")
    private String useModel;
         @Excel(name = "${column.comment}")
    private String remark;
    /**
     * 数据年份
     */     @Excel(name = "数据年份")
    private String dataYear;
    /**
     * 已经检查的id
     */     @Excel(name = "已经检查的id")
    private Integer checkedAttention;
         @Excel(name = "${column.comment}")
    private Integer working;
    /**
     * 补录指标code
     */     @Excel(name = "补录指标code")
    private String plusQualcode;


}
