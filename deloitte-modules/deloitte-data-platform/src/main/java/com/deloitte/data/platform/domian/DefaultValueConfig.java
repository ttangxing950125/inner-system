package com.deloitte.data.platform.domian;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 科目默认值配置表
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="DefaultValueConfig对象", description="科目默认值配置表 ")
public class DefaultValueConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "财报编码")
    private String code;

    @ApiModelProperty(value = "默认值 1 空值(NULL)，2 零值(0)，3 异常值(-999999.9999)")
    private BigDecimal defaultValue;

    @ApiModelProperty(value = "状态 0 禁用，1 启用")
    private String status;
}
