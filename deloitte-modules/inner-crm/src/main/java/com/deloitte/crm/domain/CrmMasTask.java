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
 * 【请填写功能名称】对象 crm_mas_task
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Getter
@Setter
@ToString
public class CrmMasTask implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 德勤code */
    @Excel(name = "德勤code")
    private String entityCode;

    @Excel(name = "来源")
    private String sourceName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date taskDate;

    /** 任务处理状态 0-未处理 1-已处理 */
    @Excel(name = "任务处理状态 0-未处理 1-已处理")
    private Integer state;

    /** 处理人，完成人 */
    @Excel(name = "处理人，完成人")
    private String handleUser;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;
}
