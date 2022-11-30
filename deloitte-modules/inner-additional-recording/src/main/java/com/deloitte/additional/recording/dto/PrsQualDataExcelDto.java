package com.deloitte.additional.recording.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @创建人 tangx
 * @创建时间 2022/11/26
 * @描述 指标任务导入导出vo
 */
@ApiModel("指标任务导入导出DTO")
@Data
@ToString
public class PrsQualDataExcelDto implements Serializable {

    @ExcelProperty(value = "主体名称", index = 0)
    @ColumnWidth(40)
    private String entityName;

    @ExcelProperty(value = "证券编码", index = 1)
    @ColumnWidth(40)
    private String securitiesCode;

    @ExcelProperty(value = "主体统一社会信用代码", index = 2)
    @ColumnWidth(40)
    private String creditCode;

    @ExcelProperty(value = "指标值", index = 3)
    @ColumnWidth(15)
    private String qualValue;
    @ExcelProperty(value = "状态", index = 4)
    @ColumnWidth(15)
    private Integer taskStatus;
    @ExcelProperty(value = "执行选项", index = 5)
    @ColumnWidth(15)
    private Integer doStatus;
    @ExcelProperty(value = "账号名称", index =6)
    @ColumnWidth(15)
    private String userName;

    public Integer getTaskStatus() {
        if (this.taskStatus == null) {
            this.taskStatus = 1;
        }
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        if (this.taskStatus == null) {
            this.taskStatus = 1;//设置为未分配
        } else {
            this.taskStatus = taskStatus;
        }
    }
}
