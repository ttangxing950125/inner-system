package com.deloitte.crm.dto;

import com.deloitte.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @author 正杰
 * @description: EntityDto
 * @date 2022/9/23
 */
@Data
public class EntityDto {

    /** $column.columnComment */
    private Integer id;

    /** 企业名 */
    @Excel(name = "企业名")
    private String entityName;

    /** IB+自000001开始排序，每个企业唯一 */
    @Excel(name = "IB+自000001开始排序，每个企业唯一")
    private String entityCode;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String creditCode;

    /** 是否上市 0-未上市 1-已上市 */
    @Excel(name = "是否上市 0-未上市 1-已上市")
    private Integer list;

    /** 是否发债 0-未发债 1-已发债 */
    @Excel(name = "是否发债 0-未发债 1-已发债")
    private Integer issueBonds;


    /** 是否金融机构 0-否 1-是 */
    @Excel(name = "是金融机构 0-否 1-是")
    private Integer finance;

    /** 统一社会信用代码是否异常 0-正常 1-异常 */
    @Excel(name = "统一社会信用代码是否异常 0-正常 1-异常")
    private Integer creditError;

    /** 若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容：
     1、吊销
     2、注销
     3、非大陆注册机构
     4、其他未知原因
     5、正常 */
    @Excel(name = "若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容：1、吊销2、注销3、非大陆注册机构4、其他未知原因5、正常")
    private Integer creditErrorType;

    /** 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分

     每次有操作曾用名表的时候，重新更新这个字段 */
    @Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分每次有操作曾用名表的时候，重新更新这个字段")
    private String entityNameHis;

    /** 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注 */
    @Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注")
    private String entityNameHisRemarks;

    /** 创建这条数据的用户名 */
    @Excel(name = "创建这条数据的用户名")
    private String creater;

    /** 最后一次更新这条数据的用户 */
    @Excel(name = "最后一次更新这条数据的用户")
    private String updater;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated = new Date();

    /**
     * 曾用名
     */
    private String oldName;

}
