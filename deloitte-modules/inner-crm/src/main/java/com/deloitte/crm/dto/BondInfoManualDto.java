package com.deloitte.crm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author 正杰
 * @description: 手动添加债券信息实体类
 * @date 2022/10/11
 */
@Data
@Accessors(chain = true)
public class BondInfoManualDto {

    @ApiModelProperty(name="bondFullName",value="债券全称")
    private String bondFullName;

    @ApiModelProperty(name="bondShortName",value="债券简称")
    private String bondShortName;

    @ApiModelProperty(name="oriCode",value="债券交易代码")
    private String oriCode;

    @ApiModelProperty(name="windTypeOne",value="wind债券类型 Ⅰ ")
    private String windTypeOne;

    @ApiModelProperty(name="windTypeTwo",value="wind债券类型 Ⅱ ")
    private String windTypeTwo;

    @ApiModelProperty(name="raiseType",value="公私募类型： 公募 0; 私募 1 ")
    private Integer raiseType;

    @ApiModelProperty(name="bondState",value="债券状态： 存续 0; 违约 1;已兑付 2")
    private Integer bondState;

    @ApiModelProperty(name="valueDate",value="起息日")
    private String valueDate;

    @ApiModelProperty(name="dueCashingDate",value="到期兑付日")
    private String dueCashingDate;

    @NotNull(message = "主体统一社会信用代码不能为空")
    @ApiModelProperty(name="entityCode",value="主体的code")
    private String entityCode;

}
