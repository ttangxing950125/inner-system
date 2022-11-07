package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (EntityInfo)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EntityInfo implements Serializable {
    private static final long serialVersionUID = 337891707856428940L;
    @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 企业名
     */
    @Excel(name = "企业名")
    private String entityName;
    /**
     * IB+自000001开始排序，每个企业唯一
     */
    @Excel(name = "IB+自000001开始排序，每个企业唯一")
    private String entityCode;
    /**
     * 统一社会信用代码
     */
    @Excel(name = "统一社会信用代码")
    private String creditCode;
    /**
     * wind万得的code，这个code对应关系不清楚，可能不会用
     */
    @Excel(name = "wind万得的code，这个code对应关系不清楚，可能不会用")
    private String entityCodeWind;
    /**
     * 当前是否上市 0-未上市 1-已上市
     */
    @Excel(name = "当前是否上市 0-未上市 1-已上市")
    private Object list;
    /**
     * 是否金融机构
     */
    @Excel(name = "是否金融机构")
    private Boolean finance;
    /**
     * 当前是否发债 0-未发债 1-已发债
     */
    @Excel(name = "当前是否发债 0-未发债 1-已发债")
    private Object issueBonds;
    /**
     * 公司简介
     */
    @Excel(name = "公司简介")
    private String entityIntro;
    /**
     * 历史是否上市 0 否 1 是
     */
    @Excel(name = "历史是否上市 0 否 1 是")
    private Integer entityStockTag;
    /**
     * 历史是否发债
     */
    @Excel(name = "历史是否发债")
    private Integer entityBondTag;
    /**
     * 所属证监会名称
     */
    @Excel(name = "所属证监会名称")
    private String entityIndustrySsc;
    /**
     * 申万行业2014
     */
    @Excel(name = "申万行业2014")
    private String entityIndustrySw2014;
    /**
     * 国民行业
     */
    @Excel(name = "国民行业")
    private String entityIndustryCns;
    /**
     * 审计机构
     */
    @Excel(name = "审计机构")
    private String entityAuditinstitNew;
    /**
     * 是否生效 0-失效 1-生效
     */
    @Excel(name = "是否生效 0-失效 1-生效")
    private Boolean status;
    /**
     * 统一社会信用代码是否异常 0-正常 1-异常
     */
    @Excel(name = "统一社会信用代码是否异常 0-正常 1-异常")
    private Boolean creditError;
    /**
     * 社会信用代码异常备注
     */
    @Excel(name = "社会信用代码异常备注")
    private String creditErrorRemark;
    /**
     * 若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容：
     1、吊销
     2、注销
     3、非大陆注册机构
     4、其他未知原因
     5、正常
     */
    @Excel(name = "若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容： 1、吊销 2、注销 3、非大陆注册机构 4、其他未知原因            5、正常")
            private Integer creditErrorType;
            /**
             * 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分

            每次有操作曾用名表的时候，重新更新这个字段
            */@Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分 每次有操作曾用名表的时候，重新更新这个字段")
            private String entityNameHis;
            /**
             * 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注
            */@Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注")
            private String entityNameHisRemarks;
            /**
             * 财报列示类型
            */@Excel(name = "财报列示类型")
            private String listType;
            /**
             * 报告类型
            */@Excel(name = "报告类型")
            private String reportType;
            /**
             * wind行业划分
            */@Excel(name = "wind行业划分")
            private String windMaster;
            /**
             * 申万行业划分
            */@Excel(name = "申万行业划分")
            private String shenWanMaster;
            /**
             * 创建这条数据的用户名
            */@Excel(name = "创建这条数据的用户名")
            private String creater;
            /**
             * 最后一次更新这条数据的用户
            */@Excel(name = "最后一次更新这条数据的用户")
            private String updater;
            @Excel(name = "${column.comment}")
            private Date created;
            @Excel(name = "${column.comment}")
            private Date updated;
            /**
             * 产业链CICS行业划分明细
            */@Excel(name = "产业链CICS行业划分明细")
            private String cicsIndustryDetails;
            /**
             * 1-企业主体 2-政府主体
            */@Excel(name = "1-企业主体 2-政府主体")
            private Integer entityType;
            /**
             * 上级政府编码，政府用
            */@Excel(name = "上级政府编码，政府用")
            private String preGovCode;
}
