package com.deloitte.data.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 财报数据校验入参
 *
 * @author XY
 * @date 2022/11/27
 */
@Data
@ApiModel("财报数据校验入参")
public class BaseFinDataCheckDto implements Serializable {

    @ApiModelProperty(value = "根据 year、type、quarter 计算出来的值", hidden = true)
    private LocalDate reportDate;

    @ApiModelProperty(value = "年份", required = true)
    @NotNull(message = "年份不能为空")
    private Integer year;

    @ApiModelProperty(value = "类型 默认年报（年报，季报）", notes = "（预留）暂时未使用", hidden = true)
    private String type;

    @ApiModelProperty(value = "季度（）", notes = "（预留）暂时未使用", hidden = true)
    private Integer quarter;

    /**
     * 这里得注意一下，如果传了科目code，需要把科目对应主体得全部科目查出来，因未该科目的勾稽关系很可能依赖其他科目
     */
//    @ApiModelProperty(value = "财报编码")
//    private String code;
            
    @ApiModelProperty(value = "主体Code集合")
    private List<String> entityCodeList;

}
