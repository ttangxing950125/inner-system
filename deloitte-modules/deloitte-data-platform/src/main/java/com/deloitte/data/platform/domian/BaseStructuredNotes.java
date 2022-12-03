package com.deloitte.data.platform.domian;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 结构化附注表
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="BaseStructuredNotes对象", description="结构化附注表 ")
public class BaseStructuredNotes extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "附注编码")
    private String code;

    @ApiModelProperty(value = "附注值")
    private String value;

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "报告期")
    private LocalDate reportDate;

    @ApiModelProperty(value = "公告日期")
    private LocalDate announcementDate;
}
