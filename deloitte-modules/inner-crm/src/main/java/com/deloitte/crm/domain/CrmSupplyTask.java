package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.deloitte.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 【请填写功能名称】对象 crm_supply_task
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Data
public class CrmSupplyTask implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 德勤code */
    @Excel(name = "德勤code")
    private String entityCode;

    /** sys_role的id */
    @Excel(name = "sys_role的id")
    private Long roleId;

    /** 任务日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务日期", width = 30, dateFormat = "yyyy-MM-dd")
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

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 来源 */
    @Excel(name = "来源")
    @TableField("`from`")
    private String from;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
