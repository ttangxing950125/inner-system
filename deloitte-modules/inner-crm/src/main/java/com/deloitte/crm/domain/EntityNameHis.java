package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 【请填写功能名称】对象 entity_name_his
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Data
@Accessors(chain = true)
public class EntityNameHis implements Serializable
{
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /** $column.columnComment */
    private Long id;

    /** 1-企业主体 2-政府 */
    @Excel(name = "1-企业主体 2-政府")
    private Integer entityType;

    /** 生效状态 0-失效 1-生效 */
    @Excel(name = "生效状态 0-失效 1-生效")
    private Integer status;

    /** 德勤code */
    @Excel(name = "德勤code")
    private String dqCode;

    /** 改名日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "改名日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date happenDate = new Date();

    /** 曾用名 */
    @Excel(name = "曾用名")
    private String oldName;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 记录新增来源 1-修改主体名称自动生成 2-曾用名管理中操作 */
    @Excel(name = "记录新增来源 1-修改主体名称自动生成 2-曾用名管理中操作")
    private Integer source;

    /** 创建人，为null的话就是系统创建 */
    @Excel(name = "创建人，为null的话就是系统创建")
    private String creater;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
