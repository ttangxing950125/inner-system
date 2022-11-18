package com.deloitte.additional.recording.vo.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @创建人 tangx
 * @创建时间 2022/11/10
 * @描述 小组下拉选择框列表响应体
 */
@Data
@ApiModel("小组下拉选择框列表响应体")
public class SysGroupSelectVo implements Serializable {

    @ApiModelProperty("分组id")
    private Integer id;

    @ApiModelProperty("分组名称")
    private String groupName;


}
