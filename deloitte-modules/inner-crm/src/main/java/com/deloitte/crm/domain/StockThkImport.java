package com.deloitte.crm.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author 正杰
 * @description: StockThkImport
 * @date 2022/10/30
 */
@Data
public class StockThkImport {

    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;

    /** 导入时间 */
    @ExcelIgnore
    private String importTime;

    /** 证券代码 */
    @ExcelProperty(value ="证券代码",index = 0)
    private String bondCode;

    /** 证券简称 */
    @ExcelProperty(value ="证券简称")
    private String bondShortName;

    /** 上市日期 */
    @ExcelProperty(value ="上市日期")
    private String ipoDate;

    /** 上市地点 */
    @ExcelProperty(value ="上市地点")
    private String ipoPlace;

    /** 上市板 */
    @ExcelProperty(value ="上市板")
    private String ipoVersion;

    /** 摘牌日期 */
    @ExcelProperty(value ="摘牌日期")
    private String delistingDate;

    /** 戴帽摘帽时间 */
    @ExcelProperty(value ="戴帽摘帽时间")
    private String wearingRemovingHatDate;

    /** 证券曾用名 */
    @ExcelProperty(value ="证券曾用名")
    private String bondNameHis;

    /** Wind代码 */
    @ExcelProperty(value ="Wind代码")
    private String windCode;

    /** 同公司港股Wind代码 */
    @ExcelProperty(value ="同公司港股Wind代码")
    private String thkWindCode;

    /** 公司中文名称 */
    @ExcelProperty(value ="公司中文名称")
    private String entityName;

    /** 金融机构类型 */
    @ExcelProperty(value ="金融机构类型")
    private String bondType;

    /** 成立日期 */
    @ExcelProperty(value ="成立日期")
    private String incorporationDate;

    /** 经营范围 */
    @ExcelProperty(value ="经营范围")
    private String businessRange;

    /** 公司简介 */
    @ExcelProperty(value ="公司简介")
    private String entityIntroduce;

    /** 所属行政区划 省级 */
    @ExcelProperty(value ="所属行政区划\n" +
            "[行政区划级别] 省级\n" +
            "[交易日期] 最新收盘日")
    private String belProvinceAdministrative;

    /** 所属行政区划 地级 */
    @ExcelProperty(value ="所属行政区划\n" +
            "[行政区划级别] 地级\n" +
            "[交易日期] 最新收盘日")
    private String belLandAdministrative;

    /** 所属行政区划 县级 */
    @ExcelProperty(value ="所属行政区划\n" +
            "[行政区划级别] 县级\n" +
            "[交易日期] 最新收盘日")
    private String belCountyAdministrative;

    /** 统一社会信用代码 */
    @ExcelProperty(value ="统一社会信用代码")
    private String creditCode;

    /** 公司发行股票一览 */
    @ExcelProperty(value ="公司发行股票一览")
    private String entityIssStock;

    /** 公司发行债券一览 */
    @ExcelProperty(value ="公司发行债券一览")
    private String entityIssBond;

    /** 公司曾用名 */
    @ExcelProperty(value ="公司曾用名\n" +
            "[交易日期] 最新收盘日")
    private String entityNameHis;

    /** 所属证监会行业名称 */
    @ExcelProperty(value ="所属证监会行业名称\n" +
            "[交易日期] 最新收盘日\n" +
            "[行业级别] 全部明细")
    private String belCsrcIndustryName;

    /** 所属Wind行业名称 */
    @ExcelProperty(value ="所属Wind行业名称\n" +
            "[行业级别] 全部明细")
    private String belIndustryWindName;

    /** 所属申万行业名称(2014) */
    @ExcelProperty(value ="所属申万行业名称(2014)\n" +
            "[行业级别] 全部明细")
    private String belIndustrySwNameOld;

    /** 所属申万行业名称(2021) */
    @ExcelProperty(value ="所属申万行业名称(2021)\n" +
            "[交易日期] 最新收盘日\n" +
            "[行业级别] 全部明细")
    private String belIndustrySwNameNew;

    /** 所属国民经济行业分类 */
    @ExcelProperty(value ="所属国民经济行业分类\n" +
            "[交易日期] 最新收盘日\n" +
            "[行业级别] 全部明细")
    private String belCountryEconomy;

    /** 审计机构 */
    @ExcelProperty(value ="审计机构")
    private String auditOrganization;

    /** 创建时间 */
    @ExcelIgnore
    private Date created;

    /** 更新时间 */
    @ExcelIgnore
    private Date updated;

}
