package com.deloitte.crm.domain;

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
 * 新债发行-推迟或取消发行债券(BondDelIss)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 12:18:59
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BondDelIss implements Serializable {
    private static final long serialVersionUID = 413984641997146462L;
    /**
     * 主键
     */
    @Excel(name = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * wind_task的id
     */
    private Integer taskId;

    private Date importTime;

    private Integer changeType;
    /**
     * 债券代码
     */
    @Excel(name = "债券代码")
    private String bondCode;
    /**
     * 债券简称
     */
    @Excel(name = "债券简称")
    private String bondShortName;
    /**
     * 发生日期 yyyy-MM-dd
     */
    @Excel(name = "发生日期")
    private Date happenDate;
    /**
     * 公告日期 yyyy-MM-dd
     */
    @Excel(name = "公告日期")
    private String annoDate;
    /**
     * 计划发行规模(亿)
     */
    @Excel(name = "计划发行规模(亿)")
    private Double issScalePlan;
    /**
     * 事件
     */
    @Excel(name = "事件")
    private String event;
    /**
     * 主承销商
     */
    @Excel(name = "主承销商")
    private String mainUnw;
    /**
     * 簿记管理人
     */
    @Excel(name = "簿记管理人")
    private String bookKeeper;
    /**
     * 发行起始日 yyyy-MM-dd
     */
    @Excel(name = "发行起始日")
    private Date issStartDate;
    /**
     * 上市地点
     */
    @Excel(name = "上市地点")
    private String ipoAddr;
    /**
     * 发行期限(年)
     */
    @Excel(name = "发行期限(年)")
    private Double issTerm;
    /**
     * 特殊期限
     */
    @Excel(name = "特殊期限")
    private Double specialTerm;
    /**
     * 债券评级
     */
    @Excel(name = "债券评级")
    private String bondGrade;
    /**
     * 主体评级
     */
    @Excel(name = "主体评级")
    private String entityGrade;
    /**
     * 票面利率(%)
     */
    @Excel(name = "票面利率(%)")
    private Double couponRate;
    /**
     * 发行规模(亿)
     */
    @Excel(name = "发行规模(亿)")
    private Double issScale;
    /**
     * 利率类型
     */
    @Excel(name = "利率类型")
    private String rateType;
    /**
     * 发行人简称
     */
    @Excel(name = "发行人简称")
    private String issorShortName;
    /**
     * 企业性质
     */
    @Excel(name = "企业性质")
    private String coNature;
    /**
     * 省份
     */
    @Excel(name = "省份")
    private String province;
    /**
     * 所属Wind二级行业
     */
    @Excel(name = "所属Wind二级行业")
    private String winSecondIndustry;


}
