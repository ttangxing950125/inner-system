package com.deloitte.crm.vo;

import com.deloitte.crm.domain.CrmWindTask;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/25
 */
@Getter
@Setter
@ToString
@ApiModel(value="WindTaskDetailsVo", description = "角色1导入wind文件页面所需展示的数据")
public class WindTaskDetailsVo {
    /**
     * 当日任务
     */
    @ApiModelProperty(value = "wind任务对象")
    private CrmWindTask windTask;

    /**
     * 任务名
     */
    @ApiModelProperty(value = "需要导入的文件名")
    private String taskFileName;


    /**
     * 0-未处理 1-已完成 2-导入中
     */
    @ApiModelProperty(value = "任务状态", notes = "0-未处理 1-已完成 2-导入中")
    private Integer taskStatus;

    /**
     * 展示到列表的数据
     */
    @ApiModelProperty(value = "展示到页面表格的数据", notes = "key为中文表头，value为数据")
    List<Map<String, Object>> data;

    /**
     * 表格头
     */
    @ApiModelProperty(value = "展示到页面表格的表头", notes = "key为中文表头，value为数据")
    List<String> header;
}
