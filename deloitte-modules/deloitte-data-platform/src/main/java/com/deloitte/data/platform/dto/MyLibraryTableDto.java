package com.deloitte.data.platform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 我的库表
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyLibraryTableDto {

    @ApiModelProperty(value = "字典编码")
    private String code;

    @ApiModelProperty(value = "父级字典编码")
    private String parentCode;

    @ApiModelProperty(value = "子类")
    MyLibraryTableDto child;
}
