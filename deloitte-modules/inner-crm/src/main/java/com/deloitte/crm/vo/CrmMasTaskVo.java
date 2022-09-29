package com.deloitte.crm.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
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
    private Long id;

    @Excel(name = "德勤code")
    private String entityCode;

    @Excel(name = "来源")
    private String sourceName;

    private String entityName;

    private String creditCode;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date taskDate;

    @Excel(name = "任务处理状态 0-未处理 1-已处理")
    private Integer state;

    @Excel(name = "处理人，完成人")
    private String handleUser;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

}
