package com.deloitte.additional.recording.vo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/16/15:58
 * @Description: Excel导入导出优先采用EasyExcel 支持大数据量
 * @see com.alibaba.excel.annotation.write.style.HeadRowHeight
 * @see com.alibaba.excel.annotation.write.style.ContentRowHeight
 * @see com.alibaba.excel.annotation.write.style.ColumnWidth
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@HeadRowHeight(15)
@ContentRowHeight(10)
@ColumnWidth(25)
public class EvidenceDataExcelVo {
    //年份", "版本", "敞口", "指标名称", "evidnece名称", "样本总数", "缺失", "缺失率", "最小值", "最大值", "前5%", "前25%", "前50%", "前75%", "前95%", "更新时间

    @ExcelProperty("年份")
    private String timeValue;

    @ExcelProperty("版本名称")
    private String versioNname;

    @ExcelProperty("敞口")
    private String masterName;

    @ExcelProperty("指标名称")
    private String modelName;

    @ExcelProperty("evidnece名称")
    private String evidneceName;
    /**
     * 样本总数
     */

    @ExcelProperty("样本总数")
    private String total;
    /**
     * 缺失
     */
    @ExcelProperty("缺失")
    private String miss;
    /**
     * 缺失率
     */
    @ExcelProperty("缺失率")
    private String missRate;
    /**
     * 最小值
     */
    @ExcelProperty("最小值")
    private String minValue;
    /**
     * 最大值
     */
    @ExcelProperty("最大值")
    private String maxValue;
    /**
     * 前5%，p表示百分比，a-j表示1-9
     */
    @ExcelProperty("前5%")
    private String pe;
    /**
     * 前25%，p表示百分比，a-j表示1-9
     */
    @ExcelProperty("前25%")
    private String pbe;
    /**
     * 前50%，p表示百分比，a-j表示1-9
     */
    @ExcelProperty("50%")
    private String pe0;
    /**
     * 前75%，p表示百分比，a-j表示1-9
     */
    @ExcelProperty("75%")
    private String pge;
    /**
     * 前95%，p表示百分比，a-j表示1-9
     */
    @ExcelProperty("95%")
    private String pie;
}
