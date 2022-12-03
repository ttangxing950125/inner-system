package com.deloitte.data.platform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class ApplyDataExportDto{
    @ApiModelProperty(value = "菜单代码")
    private String code;

    @ApiModelProperty(value = "字段代码")
    private String entityCode;

    @ApiModelProperty(value = "关键字/英文名称/中文名称")
    private String keyWord;

    @ApiModelProperty(value = "年份")
    private List<String> years;
}
