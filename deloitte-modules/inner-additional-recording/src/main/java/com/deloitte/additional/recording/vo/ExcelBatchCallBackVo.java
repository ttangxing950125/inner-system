package com.deloitte.additional.recording.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@HeadRowHeight(15)
@ContentRowHeight(10)
@ColumnWidth(25)
public class ExcelBatchCallBackVo {

    private Integer id;

    @ExcelProperty("主体名称")
    private String entityName;

    @ExcelProperty("数据年份")
    private String timeValue;

    @ExcelProperty("evidence")
    private String evidenceName;

    @ExcelProperty("补录人员")
    private String collectionName;

    @ExcelProperty("异常备注")
    private String desc;

    /**
     * 补录人员最近提交的时间
     */
    private Date subTime;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 任务状态 参考字典表collStat
     -1	已关闭
     0	待分配
     1	补录中
     2	审核打回
     3	审核中
     4	审核通过
     5	无法录入

     */
    private Integer status;
}
