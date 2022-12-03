package com.deloitte.data.platform.vo;

import com.deloitte.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据提取
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiddleDataExtractionVo implements Serializable {

    @Excel(name = "字段代码")
    @ApiModelProperty(value = "字段代码")
    private String code;

    @Excel(name = "字段中文名称")
    @ApiModelProperty(value = "字段中文名称")
    private String name;

    @Excel(name = "数据时间")
    @ApiModelProperty(value = "数据时间")
    private String reportDate;

    @Excel(name = "推荐数据")
    @ApiModelProperty(value = "推荐数据")
    private String suggestValue;

    @Excel(name = "数据缺失率")
    @ApiModelProperty(value = "数据缺失率")
    private String dataMissRate;
}
