package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.crm.annotation.Attrs;
import com.deloitte.crm.annotation.UpdateLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 【请填写功能名称】对象 entity_info
 *
 * @author deloitte
 * @date 2022-09-21
 */
@TableName("entity_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EntityInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 企业名
     */
    @Excel(name = "企业名")
    @UpdateLog(fieldName = "企业名称", tableFieldName = "entity_name")
    private String entityName;

    /**
     * IB+自000001开始排序，每个企业唯一
     */
    @Excel(name = "IB+自000001开始排序，每个企业唯一")
    private String entityCode;

    /**
     * 报告类型
     */
    @Excel(name = "报告类型")
    @UpdateLog(fieldName = "年报列示类型", tableFieldName = "report_type")
    private String reportType;


    /**
     * entity_info的entity_code
     */
    @Excel(name = "wind行业划分")
    private String windMaster;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "申万行业划分")
    private String shenWanMaster;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "产业链CICS行业划分明细")
    @Attrs(attrId = 649, attrName = "产业链CICS行业划分明细")
    private String cicsIndustryDetails;

    /**
     * 统一社会信用代码
     */
    @Excel(name = "统一社会信用代码")
    @UpdateLog(fieldName = "统一社会信用代码", tableFieldName = "credit_code")
    private String creditCode;

    /**
     * 是否上市 0-未上市 1-已上市
     */
    @Excel(name = "是否上市 0-未上市 1-已上市")
    private Integer list;

    /**
     * 是否发债 0-未发债 1-已发债
     */
    @Excel(name = "是否发债 0-未发债 1-已发债")
    private Integer issueBonds;


    /**
     * 是否金融机构 0-否 1-是
     */
    @Excel(name = "是金融机构 0-否 1-是")
    private Integer finance;

    /**
     * 统一社会信用代码是否异常 0-正常 1-异常
     */

    /**
     * 年报示例类型
     */
    @ApiModelProperty(value = "年报示例类型")
    @NotNull(message = "年报示例类型不能为空")
    private String listType;

    /**
     * 统一社会信用代码是否异常 0-正常 1-异常
     */
    @Excel(name = "统一社会信用代码是否异常 0-正常 1-异常")
    private Integer creditError;

    /**
     * 社会信用代码异常备注
     */
    private String creditErrorRemark;

    /**
     * 若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容：
     * 1、吊销
     * 2、注销
     * 3、非大陆注册机构
     * 4、其他未知原因
     * 5、正常
     */
    @Excel(name = "若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容：1、吊销2、注销3、非大陆注册机构4、其他未知原因5、正常")
    private Integer creditErrorType;

    /**
     * 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分
     * <p>
     * 每次有操作曾用名表的时候，重新更新这个字段
     */
    @Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分每次有操作曾用名表的时候，重新更新这个字段")
    private String entityNameHis;

    /**
     * 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注
     */
    @Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注")
    private String entityNameHisRemarks;

    /**
     * 创建这条数据的用户名
     */
    @Excel(name = "创建这条数据的用户名")
    private String creater;

    /**
     * 最后一次更新这条数据的用户
     */
    @Excel(name = "最后一次更新这条数据的用户")
    private String updater;

    /**
     * 主体状态 是否生效 0-失效 1-生效
     */
    @Excel(name = "主体状态")
    private Integer status;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updated;
    /**
     * 所属证监会名称
     */
    private String entityIndustrySsc;
    /**
     * 审计机构
     */
    private String entityAuditinstitNew;

    /**
     * 历史是否上市 0 否 1 是
     */
    private String entityStockTag;
    /**
     * 历史是否发债 0 否 1 是
     */
    private String entityBondTag;


    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
