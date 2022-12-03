package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 中间层基础数据字典
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiddleDataConfigVo implements Serializable {

    @ApiModelProperty(value = "中间层字段代码")
    private String code;

    @ApiModelProperty(value = "中间层字段中文名称")
    private String name;

    @ApiModelProperty(value = "配置公式描述")
    private String evidenceFormulaDescribe;

    @ApiModelProperty(value = "精度")
    private Double accuracy;

    @ApiModelProperty(value = "单位")
    private String unit;
}
