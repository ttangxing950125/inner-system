package com.deloitte.data.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 基础数据
 *
 * @author XY
 * @date 2022/11/18
 */
@Data
@ApiModel("批量查询基础数据")
public class ListBaseFinDataDto {

    @ApiModelProperty(value = "主体编码")
    private List<String> entityCodeList;

    @ApiModelProperty(value = "年份")
    private Integer year;

    @ApiModelProperty(value = "上报时间")
    private LocalDate reportDate;

    @ApiModelProperty(value = "财报编码")
    private String code;
}
