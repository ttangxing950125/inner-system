package com.deloitte.data.platform.domian;

import java.time.LocalDateTime;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据校验模型表
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ModelDataCheck对象", description="数据校验模型表 ")
public class ModelDataCheck extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "校验的科目编码")
    private String checkCode;

    @ApiModelProperty(value = "公式")
    private String checkFormula;

    @ApiModelProperty(value = "描述")
    private String checkDescribe;
}
