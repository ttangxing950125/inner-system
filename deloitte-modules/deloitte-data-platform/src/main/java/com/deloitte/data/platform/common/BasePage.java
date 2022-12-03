package com.deloitte.data.platform.common;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description 分页通用请求参数
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
public class BasePage {
    @ApiModelProperty(value = "页数", example = "1")
    private Integer pageNum;
    @ApiModelProperty(value = "每页条数", example = "10")
    private Integer pageSize;
}
