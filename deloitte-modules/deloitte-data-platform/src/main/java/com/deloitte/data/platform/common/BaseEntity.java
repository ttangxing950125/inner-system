package com.deloitte.data.platform.common;


import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础实体类
 *
 * @author fangliu
 */
@Data
public class BaseEntity extends IdEntity {

	@ApiModelProperty(value = "创建人")
	@TableField(value = "created_by", fill = FieldFill.INSERT)
	private String createdBy;

	@JsonFormat(pattern=DatePattern.NORM_DATETIME_PATTERN,timezone="GMT+8")
	@ApiModelProperty(value = "创建时间")
	@TableField(value = "created_time", fill = FieldFill.INSERT)
	private LocalDateTime createdTime;

	@ApiModelProperty(value = "更新人")
	@TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
	private String updatedBy;

	@JsonFormat(pattern=DatePattern.NORM_DATETIME_PATTERN,timezone="GMT+8")
	@ApiModelProperty(value = "更新时间")
	@TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updatedTime;

	@ApiModelProperty(value = "逻辑删除 0 正常，1 删除")
	private String isDeleted;
}
