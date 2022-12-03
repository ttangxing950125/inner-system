package com.deloitte.data.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 公式中对应的数据
 *
 * @author XY
 * @date 2022/11/18
 */
@Data
@ApiModel("公式中对应的数据")
public class FormulaItemDataDto {

    @ApiModelProperty(value = "公式项编码")
    private String code;

    @ApiModelProperty(value = "公式项值")
    private String value;

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "上报日期")
    private LocalDate reportDate;
}
