package com.deloitte.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 正杰
 * @description: CrmEntityTaskVo
 * @date 2022/9/29
 */
@Data
@Accessors(chain = true)
public class CrmEntityTaskVo {

    @ApiModelProperty(value = "任务id")
    private Integer id;

    @ApiModelProperty(value = "捕获渠道， crm_wind_task的task_category")
    private String taskCategory;

    @ApiModelProperty(value = "1-债券、2-港股、3-股票 表中的id source指导入的表")
    private Integer sourceId;

    @ApiModelProperty(value = "1-债券 bond_new_iss、2-港股 thk_sec_iss_info、3-股票 ")
    private Integer sourceType;

    @ApiModelProperty(value = "来源主表code")
    private String dataCode;

    @ApiModelProperty(value = "1-债券 bond_info、2-港股 stock_thk_info、3-股票  stock_cn_info")
    private Integer dataSource;

    @ApiModelProperty(value = "展示给前端的数据")
    private String dataShow;

    /**
     * 引发主体新增的文件记录json
     */

    @ApiModelProperty(value = "引发主体新增的文件记录json")
    private String details;

    /**
     * 债券代码，债券简称
     * 股票代码，股票简称之类
     */
    @ApiModelProperty(value = "债券代码，债券简称 ； 股票代码，股票简称之类")
    private String infos;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date taskDate;

    @ApiModelProperty(value = "任务处理状态 0-未处理 1-已有主体 2-新增主体")
    private Integer state;

    @ApiModelProperty(value = "处理人，完成人")
    private String handleUser;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date update;

    @ApiModelProperty(value = "债券简称")
    private String bondShortName;

    @ApiModelProperty(value = "债券全称")
    private String bondFullName;

}
