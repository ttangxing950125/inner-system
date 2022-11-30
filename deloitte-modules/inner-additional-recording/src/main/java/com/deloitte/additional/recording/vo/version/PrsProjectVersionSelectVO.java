package com.deloitte.additional.recording.vo.version;

import com.deloitte.common.core.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @创建人 tangx
 * @创建时间 2022/11/23
 * @描述 版本下拉选择响应体
 */
@ApiModel("版本下拉选择响应体")
@Data
public class PrsProjectVersionSelectVO {

    @ApiModelProperty("id")
    private Integer id;
    /**
     * 版本名称
     */
    @ApiModelProperty(name = "版本名称")
    private String name;
}
