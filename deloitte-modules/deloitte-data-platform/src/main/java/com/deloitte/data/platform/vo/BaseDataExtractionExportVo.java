package com.deloitte.data.platform.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
public class BaseDataExtractionExportVo implements Serializable {

    @ExcelProperty(value = "主体")
    @ExcelIgnore
    private String code;

    @ExcelProperty(value = "企业代码")
    @ExcelIgnore
    private String entityCode;

    @ExcelProperty(value = "企业名")
    private String entityName;

    @ExcelProperty(value = "统一社会信用代码")
    private String creditCode;

    @ExcelProperty(value = "年份")
    private String year;

    @ExcelProperty(value = "字段中文名称")
    private String name;

    @ExcelProperty(value = "数据时间")
    @ExcelIgnore
    private String reportDate;

    @ExcelProperty(value = "数据优先级")
    private String dataPriority;

    @ExcelProperty(value = "数据缺失率")
    private String dataMissRate;

    @ExcelProperty(value = "WIND")
    private String windRate;

    @ExcelProperty(value = "同花顺")
    private String flushRate;

    @ExcelProperty(value = "自动化")
    private String ocrRate;

    @ExcelProperty(value = "人工补录")
    private String artificialAddRecordRate;

    @ExcelProperty(value = "数据核查值域")
    private String thresholdValue;
}
