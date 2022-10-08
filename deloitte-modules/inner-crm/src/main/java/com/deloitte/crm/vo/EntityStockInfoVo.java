package com.deloitte.crm.vo;

import com.deloitte.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author PenTang
 * @date 2022/09/28 16:33
 */

@Data
@Accessors(chain = true)
public class EntityStockInfoVo {

    /**
     * 股票类型
     */
    @ApiModelProperty(value="股票类型")
    @NotNull(message = "不能为空")
    private  String StockType;

    /**
     * 股票代码
     */
    @ApiModelProperty(value="股票代码")
    @NotNull(message = "不能为空")
    private  String StockCode;

    /**
     * 股票简称
     */
    @ApiModelProperty(value="股票简称")
    @NotNull(message = "不能为空")
    private  String StockShortName;


    /** 企业全称 */
    @Excel(name = "企业全称")
    @ApiModelProperty(value="企业全称")
    @NotNull(message = "不能为空")
    private String entityName;


    /** 统一社会信用代码 */
    @ApiModelProperty(value="统一社会信用代码")
    @NotNull(message = "不能为空")
    private String creditCode;

    /** 统一社会信用代码是否异常 0-正常 1-异常 */
    @ApiModelProperty(value="统一社会信用代码是否异常 0-正常 1-异常")
    @NotNull(message = "不能为空")
    private Integer creditError;


    /** 社会信用代码异常备注 */
    @ApiModelProperty(value="社会信用代码异常备注")
    @NotNull(message = "不能为空")
    private Integer creditErrorRemark;


    /** 上市日期*/
    @ApiModelProperty(value="上市日期")
    @NotNull(message = "不能为空")
    private String  StartXiDate ;

    /** 退市日期*/
    @ApiModelProperty(value="退市日期")
    @NotNull(message = "不能为空")
    private  String  endDate ;

    /** 上市板块*/
    @ApiModelProperty(value="上市板块")
    @NotNull(message = "不能为空")
    private String lisSec ;

    /** 交易所*/
    @ApiModelProperty(value="交易所")
    @NotNull(message = "不能为空")
    private  String  exchange ;

    /** 是否金融机构 0-否 1-是 */
    @ApiModelProperty(value="是否金融机构 0-否 1-是")
    @NotNull(message = "不能为空")
    private Integer finance;

    /** 年报示例类型*/
    @ApiModelProperty(value="年报示例类型")
    @NotNull(message = "不能为空")
    private String anRportType;

    /** 金融机构子行业*/
    @ApiModelProperty(value="金融机构子行业")
    @NotNull(message = "不能为空")
    private String  financeSubIndu;

    /** 主体曾用名*/
    @ApiModelProperty(value="主体曾用名")
    @NotNull(message = "不能为空")
    private String entityNameHis;


}