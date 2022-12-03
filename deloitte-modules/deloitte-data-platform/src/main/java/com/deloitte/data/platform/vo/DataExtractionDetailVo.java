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
public class DataExtractionDetailVo implements Serializable {

    @ApiModelProperty(value = "主体名称")
    private String entityName;

    @ApiModelProperty(value = "主体代码")
    private String entityCode;

    @ApiModelProperty(value = "数据时间")
    private String reportDate;

    @ApiModelProperty(value = "推荐值来源  1 wind数据库，2 wind客户端，3 同花顺，4 OCR")
    private String suggestSource;

    @ApiModelProperty(value = "数据缺失率")
    private String dataMissRate;

    @ApiModelProperty(value = "WIND")
    private String windRate;

    @ApiModelProperty(value = "同花顺")
    private String flushRate;

    @ApiModelProperty(value = "自动化")
    private String ocrRate;

    @ApiModelProperty(value = "人工补录")
    private String artificialAddRecordRate;
}
