package com.deloitte.data.platform.domian;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 德勤对象关系映射表
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="BaseOrm对象", description="德勤对象关系映射表 ")
public class BaseOrm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内部编码")
    private String innerCode;

    @ApiModelProperty(value = "外部编码")
    private String outsideCode;

    @ApiModelProperty(value = "内部名称")
    private String innerName;

    @ApiModelProperty(value = "外部名称")
    private String outsideName;

    @ApiModelProperty(value = "内部单位 1 元，2 万元，3 亿元，4 数值，5 文本")
    private String innerUnit;

    @ApiModelProperty(value = "外部单位 1 元，2 万元，3 亿元，4 数值，5 文本")
    private String outsideUnit;

    @ApiModelProperty(value = "类型 来源数据字典")
    private String ormType;
}
