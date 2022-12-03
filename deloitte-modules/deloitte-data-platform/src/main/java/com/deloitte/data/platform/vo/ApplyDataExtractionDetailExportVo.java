package com.deloitte.data.platform.vo;

import com.alibaba.excel.annotation.ExcelProperty;
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
public class ApplyDataExtractionDetailExportVo implements Serializable {

    @ExcelProperty(value = "主体名称")
    private String entityName;

    @ExcelProperty(value = "统一社会信用代码")
    private String creditCode;

    @ExcelProperty(value = "年份")
    private String year;

    @ExcelProperty(value = "主体代码")
    private String entityCode;

    @ExcelProperty(value = "数据时间")
    private String reportDate;

    @ExcelProperty(value = "推荐值")
    private String suggestValue;
}
