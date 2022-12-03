package com.deloitte.data.platform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
public class BaseDataDetailExportDto {

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "财务数据配置代码")
    private String code;

    @ApiModelProperty(value = "年份")
    @NotEmpty(message = "年份不能为空")
    @Size(max = 1,message = "导出数据太大，请选择一个年份")
    private List<String> years;

    @ApiModelProperty(value = "数据来源(1 wind数据库，2 wind客户端，3 同花顺，4 OCR)")
    private List<String> sources;
}
