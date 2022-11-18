package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务表，补录平台的耗时操作全部扔到任务里面来执行(ModelTask)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ModelTask implements Serializable {
    private static final long serialVersionUID = 397608005631103561L;
    @Excel(name = "${column.comment}")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 任务编号,雪花id
     */
    @Excel(name = "任务编号,雪花id")
    private String taskNo;
    /**
     * 任务操作的数据年份
     */
    @Excel(name = "任务操作的数据年份")
    private Integer taskYear;
    /**
     * 任务附件文件路径
     */
    @Excel(name = "任务附件文件路径")
    private String taskFile;
    /**
     * 任务附件名
     */
    @Excel(name = "任务附件名")
    private String taskFileName;
    /**
     * 任务备注
     */
    @Excel(name = "任务备注")
    private String taskRemark;
    /**
     * 任务类型 参考sys_dict task_type
     */
    @Excel(name = "任务类型 参考sys_dict task_type")
    private Integer taskType;
    /**
     * 任务状态 参考sys_dict task_status
     */
    @Excel(name = "任务状态 参考sys_dict task_status")
    private Integer taskStatus;
    /**
     * 执行失败的子任务量
     */
    @Excel(name = "执行失败的子任务量")
    private Integer proceTotal;
    /**
     * 子任务量
     */
    @Excel(name = "子任务量")
    private Integer taskTotal;
    /**
     * 创建任务的用户id
     */
    @Excel(name = "创建任务的用户id")
    private String creater;
    @Excel(name = "${column.comment}")
    private Date created;
    /**
     * 完成任务时间
     */
    @Excel(name = "完成任务时间")
    private Date finishTime;
    @Excel(name = "${column.comment}")
    private Date updated;


}
