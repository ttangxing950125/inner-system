package com.deloitte.additional.recording.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @创建人 tangx
 * @创建时间 2022/11/10
 * @描述 用户分页请求体
 */
@Data
@ApiModel("用户分页请求体")
public class SysUserPageRequest extends PageReq {


    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("用户名")
    private String nickname;

    @ApiModelProperty("用户名")
    private String role;

    @ApiModelProperty("用户名")
    private String groups;
    @ApiModelProperty("用户状态")
    private String status;
}
