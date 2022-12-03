package com.deloitte.data.platform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
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
public class MiddleDataExportDto{
    @ApiModelProperty(value = "实体代码")
    @NotEmpty(message = "实体代码不能为空")
    private String entityCode;

    @ApiModelProperty(value = "关键字/英文名称/中文名称")
    private String keyWord;

    @ApiModelProperty(value = "年份")
    @NotEmpty(message = "年份不能为空")
    private List<String> years;

    @ApiModelProperty(value = "菜单代码")
    private String code;
}
