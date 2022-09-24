package com.deloitte.crm.vo;

import com.deloitte.crm.domain.CrmWindTask;
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
public class WindTaskDetailsVo {
    /**
     * 当日任务
     */
    private CrmWindTask windTask;

    /**
     * 任务名
     */
    private String taskFileName;


    /**
     * 0-未处理 1-已完成 2-导入中
     */
    private Integer taskStatus;

    /**
     * 展示到列表的数据
     */
    List<Map<String, Object>> data;

    /**
     * 表格头
     */
    List<String> header;
}
