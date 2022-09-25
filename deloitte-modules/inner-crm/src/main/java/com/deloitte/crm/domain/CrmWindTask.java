package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色1的每日任务，导入wind文件的任务对象 crm_wind_task
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Getter
@Setter
@ToString
public class CrmWindTask implements Serializable
{
    private static final Long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** task_cate_id*/
    @Excel(name = "task_cate_id")
    private Integer taskCateId;

    /** crm_task_dict的id */
    @Excel(name = "crm_task_dict的id")
    private Integer taskDictId;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String taskDesc;

    /** 任务日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskDate;

    /** 任务文件名 */
    @Excel(name = "任务文件名")
    private String taskFileName;

    /** 任务分类 */
    @Excel(name = "任务分类")
    private String taskCategory;

    /** 是否导入过该任务的文件  0-否 1-是 */
    @Excel(name = "是否导入过该任务的文件  0-否 1-是")
    private Integer imported;

    /** 是否确认过该任务的新增记录  0-否 1-是 */
    @Excel(name = "是否确认过该任务的新增记录  0-否 1-是")
    private Integer confirmInsert;

    /** 是否确认过该任务的更新记录  0-否 1-是 */
    @Excel(name = "是否确认过该任务的更新记录  0-否 1-是")
    private Integer confirmUpdate;

    /** 该条任务是否完成   0-否 1-是 */
    @Excel(name = "该条任务是否完成   0-否 1-是 2-导入中")
    private Integer complete;

    /** 任务完成人 */
    @Excel(name = "任务完成人")
    private Integer handleUser;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String remarks;
}
