package com.deloitte.crm.vo;

import com.deloitte.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author PenTang
 * @date 2022/09/27 18:55
 */
@Data
@Accessors(chain = true)
public class EntityByIondVo
{
    /**
     * 债券代码
     */
    @ApiModelProperty(value="债券代码")
    @NotNull(message = "债券代码不能为空")
   private String stockCode;
    /**
     * 债券名称
     */
    @ApiModelProperty(value="债券名称")
    @NotNull(message = "债券名称不能为空")
    private String  bondName;


    /**
     * 债券简称
     */
    @ApiModelProperty(value="债券简称")
    @NotNull(message = "债券简称不能为空")
   private  String bondShortName;


    /** 企业全称 */
    @Excel(name = "企业全称")
    @ApiModelProperty(value="企业全称")
    @NotNull(message = "企业全称不能为空")
    private String entityName;


    /** 统一社会信用代码 */
    @ApiModelProperty(value="统一社会信用代码")
    private String creditCode;

    /** 统一社会信用代码是否异常 0-正常 1-异常 */
    @ApiModelProperty(value="统一社会信用代码是否异常 0-正常 1-异常")
    @NotNull(message = "统一社会信用代码是否异常不能为空")
    private Integer creditError;


    /** 社会信用代码异常备注 */
    @ApiModelProperty(value="社会信用代码异常备注")
    private String creditErrorRemark;

    /** 起息日*/
    @ApiModelProperty(value="起息日")
    @NotNull(message = "起息日或者上市日期不能为空")
    private String  startXiDate ;

    /** 到期日*/
    @ApiModelProperty(value="到期日")
    @NotNull(message = "到期日或者退市日期不能为空")
    private  String  endDate ;

    /** 债券类型*/
    @ApiModelProperty(value="债券类型")
    @NotNull(message = "债券类型不能为空")
    private  Integer  bondType;

    /** 是否金融机构 0-否 1-是 */
    @ApiModelProperty(value="是否金融机构 0-否 1-是")
    @NotNull(message = "是否金融机构不能为空")
    private Integer finance;

    /** 年报示例类型*/
    @ApiModelProperty(value="年报示例类型")
    @NotNull(message = "年报示例类型不能为空")
    private String anRportType;

    /** 金融机构子行业*/
    @ApiModelProperty(value="金融机构子行业")
    private String  financeSubIndu;

    /** 主体曾用名*/
    @ApiModelProperty(value="主体曾用名")

    private String entityNameHis;




}
