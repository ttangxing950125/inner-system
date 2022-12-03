package com.deloitte.data.platform.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * @author fangliu
 * @date 2022/11/14 11:40:39
 */
@Data
@ApiModel(value="EntityInfo对象", description="")
public class EntityInfo{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "企业名")
    private String entityName;

    @ApiModelProperty(value = "IB+自000001开始排序，每个企业唯一")
    private String entityCode;

    @ApiModelProperty(value = "统一社会信用代码")
    private String creditCode;

    @ApiModelProperty(value = "wind万得的code，这个code对应关系不清楚，可能不会用")
    private String entityCodeWind;

    @ApiModelProperty(value = "当前是否上市 0-未上市 1-已上市")
    private Boolean list;

    @ApiModelProperty(value = "是否金融机构")
    private Boolean finance;

    @ApiModelProperty(value = "当前是否发债 0-未发债 1-已发债")
    private Boolean issueBonds;

    @ApiModelProperty(value = "公司简介")
    private String entityIntro;

    @ApiModelProperty(value = "历史是否上市 0 否 1 是")
    private Integer entityStockTag;

    @ApiModelProperty(value = "历史是否发债")
    private Integer entityBondTag;

    @ApiModelProperty(value = "所属证监会名称")
    private String entityIndustrySsc;

    @ApiModelProperty(value = "申万行业2014")
    private String entityIndustrySw2014;

    @ApiModelProperty(value = "国民行业")
    private String entityIndustryCns;

    @ApiModelProperty(value = "审计机构")
    private String entityAuditinstitNew;

    @ApiModelProperty(value = "是否生效 0-失效 1-生效")
    private Boolean status;

    @ApiModelProperty(value = "统一社会信用代码是否异常 0-正常 1-异常")
    private Boolean creditError;

    @ApiModelProperty(value = "社会信用代码异常备注")
    private String creditErrorRemark;

    @ApiModelProperty(value = "若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容： 1、吊销 2、注销 3、非大陆注册机构 4、其他未知原因 5、正常")
    private Integer creditErrorType;

    @ApiModelProperty(value = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分 每次有操作曾用名表的时候，重新更新这个字段")
    private String entityNameHis;

    @ApiModelProperty(value = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注")
    private String entityNameHisRemarks;

    @ApiModelProperty(value = "财报列示类型")
    private String listType;

    @ApiModelProperty(value = "报告类型")
    private String reportType;

    @ApiModelProperty(value = "wind行业划分")
    private String windMaster;

    @ApiModelProperty(value = "申万行业划分")
    private String shenWanMaster;

    @ApiModelProperty(value = "创建这条数据的用户名")
    private String creater;

    @ApiModelProperty(value = "最后一次更新这条数据的用户")
    private String updater;

    private LocalDateTime created;

    private LocalDateTime updated;

    @ApiModelProperty(value = "产业链CICS行业划分明细")
    private String cicsIndustryDetails;
}
