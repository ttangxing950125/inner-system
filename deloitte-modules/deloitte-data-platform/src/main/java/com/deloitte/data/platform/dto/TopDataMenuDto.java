package com.deloitte.data.platform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 基础数据字典
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopDataMenuDto{
    @ApiModelProperty(value = "0:全部、1:基础层、2:中间层、3:指标层、4:主体信息")
    @NotNull
    private Integer type;

    @ApiModelProperty(value = "关键字/英文名称/中文名称")
    private String keyWord;

    @ApiModelProperty(value = "限制条数")
    private Integer limitNum;
}
