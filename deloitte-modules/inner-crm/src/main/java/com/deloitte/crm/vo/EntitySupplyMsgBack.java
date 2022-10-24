package com.deloitte.crm.vo;

import com.deloitte.crm.annotation.Attrs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 角色3，4，5补充录入查询和回显 实体类
 *
 * @author 冉浩岑
 * @date 2022/10/24 11:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EntitySupplyMsgBack {
//    统一基础信息

    /** 来源---来源于任务列表 */
    private String source;
    /** 企业名称 */
    private String entityName;
    /** 统一社会信用代码 */
    private String creditCode;
    /** wind行业划分 */
    private String windMaster;
    /** 申万行业划分 */
    private String shenWanMaster;

    /** 备注 */
    private String remarks;

    //角色 3 属性字段
    /** 所属地区 */
    @Attrs(attrId = 983, attrName = "所属地区")
    private String belPlace;
    /** 所属辖区 */
    @Attrs(attrId = 658, attrName = "所属辖区")
    private String belJurisdiction;
    /** 对口监管机构 */
    @Attrs(attrId = 657, attrName = "对口监管机构")
    private String regulators;

    //角色 4 属性字段
    /** 政府持股方式 */
    @Attrs(attrId = 676, attrName = "政府持股方式")
    private String shareMethod;
    /** 政府对当前城投支持力度 */
    @Attrs(attrId = 677, attrName = "政府对当前城投支持力度")
    private String support;
    /** 政府对当前城投支持力度判断依据 */
    @Attrs(attrId = 981, attrName = "政府对当前城投支持力度判断依据")
    private String judgment;
    /** 政府部门实际持股比例 */
    @Attrs(attrId = 678, attrName = "政府部门实际持股比例")
    private String shareRatio;
    /** 政府部门实际持股比例年份 */
    @Attrs(attrId = 982, attrName = "政府部门实际持股比例年份")
    private String shareRatioYear;

    //角色 5 属性字段
    /** 财报列示类型 */
    private String listType;
    /** 关注报告类型 */
    private String reportType;


}
