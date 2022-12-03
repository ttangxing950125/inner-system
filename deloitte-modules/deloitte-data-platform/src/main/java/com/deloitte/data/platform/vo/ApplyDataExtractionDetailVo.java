package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据提取详情
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDataExtractionDetailVo implements Serializable {

    @ApiModelProperty(value = "主体名称")
    private String entityName;

    @ApiModelProperty(value = "主体代码")
    private String entityCode;

    @ApiModelProperty(value = "数据时间")
    private String reportDate;

    @ApiModelProperty(value = "推荐值")
    private String suggestValue;
}
