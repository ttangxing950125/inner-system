package com.deloitte.crm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 正杰
 * @description: BondVo
 * @date 2022/9/28
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "BondVo",description = "模糊匹配查询 债券 返回结果")
public class BondVo {

    @ApiModelProperty(value="债券id")
    private Long id;

    @ApiModelProperty("德勤内部债券代码")
    private String bondCode;

    @ApiModelProperty(value="交易代码")
    private String transactionCode;

    @ApiModelProperty(value="债券全称")
    private String fullName;

    @ApiModelProperty(value="债券简称")
    private String shortName;

    @ApiModelProperty(value="存续状态")
    private String debtRaisingType;

    @ApiModelProperty(value="债募类型")
    private Integer raiseType;

    @ApiModelProperty(value="是否违约")
    private Integer bondState;

}
