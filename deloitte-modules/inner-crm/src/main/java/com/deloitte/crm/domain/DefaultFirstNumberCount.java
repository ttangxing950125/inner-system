package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (DefaultFirstNumberCount)表实体类
 *
 * @author 吴鹏鹏ppp
 * @see default_first_number_count
 * @since 2022-10-09 10:50:32
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DefaultFirstNumberCount implements Serializable {
    private static final long serialVersionUID = 300511374903817814L;
    /**
     * 主键自动增加
     */
//    @Excel(name = "主键自动增加")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * wind_task的id
     */
//    @Excel(name = "wind_task的id")
    private Integer taskId;
    /**
     * 导入时间
     */
//    @Excel(name = "导入时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date importTime;
    /**
     * 1-新增 2-修改
     */
//    @Excel(name = "1-新增 2-修改")
    private Integer changeType;
    /**
     * 发行人
     */
    @Excel(name = "发行人")
    private String publisher;
    /**
     * 首次债券违约日期yyyy-MM-dd
     */
    @Excel(name = "首次债券违约日期")
    private Date fristDateCount;
    /**
     * 违约债券代码
     */
    @Excel(name = "违约债券代码")
    private String defaultBondsCode;
    /**
     * 债券简称
     */
    @Excel(name = "债券简称")
    private String defaultBondsDesc;
    /**
     * 摘要
     */
    @Excel(name = "摘要")
    private String abstracts;
    /**
     * 首次违约时债券余额(亿元)
     */
    @Excel(name = "首次违约时债券余额(亿元)")
    private Double defaultBondsBalance;
    /**
     * 存续债券余额(亿元)
     */
    @Excel(name = "存续债券余额(亿元)")
    private Double defaultBondsExistence;
    /**
     * 已实质违约债券余额(亿元)
     */
    @Excel(name = "已实质违约债券余额(亿元)")
    private Double defaultBondsEssence;
    /**
     * 尚未违约债券余额(亿元)
     */
    @Excel(name = "尚未违约债券余额(亿元)")
    private Double defaultBondsNot;
    /**
     * 担保人
     */
    @Excel(name = "担保人")
    private String surety;
    /**
     * 最新主体评级
     */
    @Excel(name = "最新主体评级")
    private String latestTopicRating;
    /**
     * 发行时主体评级
     */
    @Excel(name = "发行时主体评级")
    private String subjectRating;
    /**
     * 违约前1月主体评级
     */
    @Excel(name = "违约前1月主体评级")
    private String monthAgoRating;
    /**
     * 企业性质
     */
    @Excel(name = "企业性质")
    private String coNature;
    /**
     * 是否上市公司-Y是 N否
     */
    @Excel(name = "是否上市公司")
    private String listedCompany;
    /**
     * 省份
     */
    @Excel(name = "省份")
    private String defaultProvince;
    /**
     * 所属Wind行业
     */
    @Excel(name = "所属Wind行业")
    private String belWind;
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
