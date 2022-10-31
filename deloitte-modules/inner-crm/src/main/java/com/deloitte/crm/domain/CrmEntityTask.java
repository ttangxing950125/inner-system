package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 角色7，根据导入的数据新增主体的任务对象 crm_entity_task
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Getter
@Setter
@ToString
public class CrmEntityTask
{
    private static final Long serialVersionUID = 1L;



    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 捕获时的主体名
     */
    @ApiModelProperty(value = "捕获时的主体名")
    private String entityName;

    @ApiModelProperty(value = "wind敞口名")
    private String windMaster;

    /**
     * 任务进度表的id
     */
    @ApiModelProperty(value = "查看进度的id")
    private Integer speedId;

    /**
     * 来源主表code
     */
    @ApiModelProperty(value = "来源主表code")
    private String dataCode;

    /**
     * 1-债券 bond_info、2-港股 stock_thk_info、3-股票  stock_cn_info
     */
    private Integer dataSource;

    /** 捕获渠道， crm_wind_task的task_category */
    @Excel(name = "捕获渠道， crm_wind_task的task_category")
    @ApiModelProperty(value = "捕获渠道")
    private String taskCategory;

    /** 1-债券、2-港股、3-股票 表中的id */
    @Excel(name = "1-债券、2-港股、3-股票 表中的id")
    @ApiModelProperty(value = "1-债券、2-港股、3-股票 表中的id")
    private Integer sourceId;

    /** 1-债券、2-港股、3-股票 */
    @Excel(name = "1-债券 、2-港股、3-股票")
    @ApiModelProperty(value = "1-债券 、2-港股、3-股票")
    private Integer sourceType;

    /** 展示给前端的数据 */
    @Excel(name = "展示给前端的数据")
    @ApiModelProperty(value = "展示给前端的数据")
    private String dataShow;

    /** 任务日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "任务日期 yyyy-MM-dd")
    private Date taskDate;

    /** 任务处理状态 0-未处理 1-已有主体 2-新增主体 */
    @Excel(name = "任务处理状态 0-未处理 1-已有主体 2-新增主体")
    @ApiModelProperty(value = "任务处理状态 0-未处理 1-已有主体 2-新增主体")
    private Integer state;

    /** 处理人，完成人 */
    @Excel(name = "处理人，完成人")
    @ApiModelProperty(value = "处理人，完成人")
    private String handleUser;

    /**
     * 引发主体新增的文件记录json
     */
    @ApiModelProperty(value = "引发主体新增的文件记录json")
    private String details;

    /**
     * 债券代码，债券简称
     * 股票代码，股票简称之类
     */
    private String infos;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;
}
