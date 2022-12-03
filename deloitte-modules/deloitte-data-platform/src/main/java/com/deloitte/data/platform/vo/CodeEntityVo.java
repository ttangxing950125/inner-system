package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 基础数据字典
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeEntityVo implements Serializable {

    @ApiModelProperty(value = "主体名称")
    private String entityName;

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "统一社会信用代码")
    private String creditCode;

    @ApiModelProperty(value = "是否通过质检 ")
    private String isInspection;

    @ApiModelProperty(value = "是否通过系统质检 ")
    private String isSystemInspection;

    @ApiModelProperty(value = "是否通过人工质检 ")
    private String isArtificialInspection;

    @ApiModelProperty(value = "数据Id")
    private Long dataId;

    @ApiModelProperty(value = "年份")
    private String year;
}
