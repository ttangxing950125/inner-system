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
public class ApplyDataExtractionVo implements Serializable {

    @ApiModelProperty(value = "字段代码")
    private String code;

    @ApiModelProperty(value = "字段中文名称")
    private String name;

    @ApiModelProperty(value = "数据时间")
    private String reportDate;

    @ApiModelProperty(value = "推荐数据")
    private String suggestValue;

    @ApiModelProperty(value = "数据缺失率")
    private String dataMissRate;

    @ApiModelProperty(value = "业务场景")
    private String businessScene;
}
