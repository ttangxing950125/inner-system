package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 质量检测通过比例
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QualityTestRateVo {

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "系统质检通过比例")
    private String systemInspectionRate;

    @ApiModelProperty(value = "人工质检通过比例")
    private String artificialInspectionRate;

    @ApiModelProperty(value = "总的通过比例")
    private String totalRate;
}
