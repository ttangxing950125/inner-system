package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 基础层数据字典
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseDataConfigVo implements Serializable {

    @ApiModelProperty(value = "展示顺序")
    private String seq;

    @ApiModelProperty(value = "字段代码")
    private String code;

    @ApiModelProperty(value = "字段中文名称")
    private String name;

    @ApiModelProperty(value = "数据类型")
    private String dataType;

    @ApiModelProperty(value = "精度")
    private Double accuracy;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "币种")
    private String currency;
}
