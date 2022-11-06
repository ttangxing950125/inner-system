package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (ModelTaskDetails)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ModelTaskDetails implements Serializable {
    private static final long serialVersionUID = -99454608954955321L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 主任务id
     */     @Excel(name = "主任务id")
    private String taskNo;
    /**
     * 任务类型 参考sys_dict task_type
     */     @Excel(name = "任务类型 参考sys_dict task_type")
    private Integer taskType;
    /**
     * 子任务状态 0-失败 1-成功
     */     @Excel(name = "子任务状态 0-失败 1-成功")
    private Boolean taskFlag;
    /**
     * 任务结果描述，任务失败必须有描述
     */     @Excel(name = "任务结果描述，任务失败必须有描述")
    private String msg;
    /**
     * 子任务创建时间
     */     @Excel(name = "子任务创建时间")
    private Date created;


}
