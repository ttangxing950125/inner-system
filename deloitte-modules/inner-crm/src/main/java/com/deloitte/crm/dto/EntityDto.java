package com.deloitte.crm.dto;

import com.deloitte.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 正杰
 * @description: EntityDto
 * @date 2022/9/23
 */
@Data
@Accessors(chain = true)
public class EntityDto {

    /** $column.columnComment */
    @ApiModelProperty(name="id",value="主键")
    private Integer id;

    /** 企业名 */
    @Excel(name = "企业名")
    @ApiModelProperty(name="entityName",value="企业名")
    private String entityName;

    /** IB+自000001开始排序，每个企业唯一 */
    @Excel(name = "IB+自000001开始排序，每个企业唯一")
    @ApiModelProperty(name="entityCode",value="IB+自000001开始排序，每个企业唯一")
    private String entityCode;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    @ApiModelProperty(name="creditCode",value="统一社会信用代码")
    private String creditCode;

    /** 是否上市 0-未上市 1-已上市 */
    @Excel(name = "是否上市 0-未上市 1-已上市")
    @ApiModelProperty(name="list",value="是否上市 0-未上市 1-已上市")
    private Integer list;

    /** 是否发债 0-未发债 1-已发债 */
    @Excel(name = "是否发债 0-未发债 1-已发债")
    @ApiModelProperty(name="issueBonds",value="是否发债 0-未发债 1-已发债")
    private Integer issueBonds;


    /** 是否金融机构 0-否 1-是 */
    @Excel(name = "是金融机构 0-否 1-是")
    @ApiModelProperty(name="finance",value="是金融机构 0-否 1-是")
    private Integer finance;

    /** 统一社会信用代码是否异常 0-正常 1-异常 */
    @Excel(name = "统一社会信用代码是否异常 0-正常 1-异常")
    @ApiModelProperty(name="creditError",value="统一社会信用代码是否异常 0-正常 1-异常")
    private Integer creditError = 1;

    /** 若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容：
     1、吊销
     2、注销
     3、非大陆注册机构
     4、其他未知原因
     5、正常 */
    @Excel(name = "若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容：1、吊销2、注销3、非大陆注册机构4、其他未知原因5、正常")
    @ApiModelProperty(name="creditErrorType",value="若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容：1、吊销2、注销3、非大陆注册机构4、其他未知原因5、正常")
    private Integer creditErrorType;

    /** 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分

     每次有操作曾用名表的时候，重新更新这个字段 */
    @Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分每次有操作曾用名表的时候，重新更新这个字段")
    @ApiModelProperty(name="entityNameHis",value="汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分每次有操作曾用名表的时候，重新更新这个字段")
    private String entityNameHis;

    /** 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注 */
    @Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注")
    @ApiModelProperty(name="entityNameHisRemarks",value="汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注")
    private String entityNameHisRemarks;

    /** 创建这条数据的用户名 */
    @Excel(name = "创建这条数据的用户名")
    @ApiModelProperty(name="creater",value="创建这条数据的用户名")
    private String creater;

    /** 最后一次更新这条数据的用户 */
    @Excel(name = "最后一次更新这条数据的用户")
    @ApiModelProperty(name="updater",value="最后一次更新这条数据的用户")
    private String updater;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty(name="created",value="创建时间")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty(name="created",value="修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;

    /** 曾用名 */
    @Excel(name = "曾用名")
    @ApiModelProperty(name="oldName",value="曾用名")
    private String oldName;

}
