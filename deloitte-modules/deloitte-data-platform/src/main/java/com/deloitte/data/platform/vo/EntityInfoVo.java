package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 企业实体
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntityInfoVo implements Serializable {

    @ApiModelProperty(value = "主体名称")
    private String entityName;

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "统一社会信用代码")
    private String creditCode;

    @ApiModelProperty(value = "债券编码")
    private String stockCode;

    @ApiModelProperty(value = "是否上市")
    private String list;

    @ApiModelProperty(value = "当前是否发债")
    private String issueBonds;

    @ApiModelProperty(value = "总的通过比例")
    private String totalRate;
}
