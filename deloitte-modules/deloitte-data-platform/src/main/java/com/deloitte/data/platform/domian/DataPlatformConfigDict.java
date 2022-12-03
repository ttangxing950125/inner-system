package com.deloitte.data.platform.domian;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据平台配置字典
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="DataPlatformConfigDict对象", description="数据平台配置字典 ")
public class DataPlatformConfigDict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典编码")
    private String code;

    @ApiModelProperty(value = "字典名称")
    private String name;

    @ApiModelProperty(value = "父级字典编码")
    private String parentCode;

    @ApiModelProperty(value = "排序")
    private Integer seq;

    @ApiModelProperty(value = "备注")
    private String remark;
}
