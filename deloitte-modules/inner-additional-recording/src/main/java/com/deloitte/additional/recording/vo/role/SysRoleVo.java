package com.deloitte.additional.recording.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @创建人 tangx
 * @创建时间 2022/11/11
 * @描述 角色响应体
 */
@Data
@ApiModel("角色响应体")
public class SysRoleVo implements Serializable {

    @ApiModelProperty("id")
    private Integer id;
    /**
     * 角色名称
     */
    @ApiModelProperty(name = "角色名称")
    private String name;
    /**
     * 角色状态
     */
    @ApiModelProperty(name = "角色状态")
    private String status;
}
