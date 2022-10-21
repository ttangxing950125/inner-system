package com.deloitte.crm.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 正杰
 * @description: CrmMasTaskVo
 * @date 2022/9/29
 */
@Data
@Accessors(chain = true)
public class CrmMasTaskVo {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Excel(name = "德勤code")
    @ApiModelProperty(value = "德勤code")
    private String entityCode;

    @Excel(name = "来源")
    @ApiModelProperty(value = "来源")
    private String sourceName;

    @ApiModelProperty(value = "主体名称")
    private String entityName;

    @ApiModelProperty(value = "统一社会信用代码")
    private String creditCode;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty(value = "任务日期")
    private Date taskDate;

    @Excel(name = "任务处理状态 0-未处理 1-已处理")
    @ApiModelProperty(value = "任务完成状态 0为未完成 1为已处理")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @Excel(name = "处理人，完成人")
    @ApiModelProperty(value = "处理人，完成人")
    private String handleUser;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty(value = "创建时间")
    private Date created;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty(value = "修改日期")
    private Date updated;

}