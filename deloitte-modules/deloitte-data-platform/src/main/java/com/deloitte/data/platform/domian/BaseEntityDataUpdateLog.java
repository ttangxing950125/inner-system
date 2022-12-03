package com.deloitte.data.platform.domian;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 基础主体数据更新日志 
 * </p>
 *
 * @author fangliu
 * @date 2022/11/15 11:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="BaseEntityDataUpdateLog对象", description="基础主体数据更新日志 ")
public class BaseEntityDataUpdateLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String entityCode;

    @ApiModelProperty(value = "备注")
    private String remark;
}
