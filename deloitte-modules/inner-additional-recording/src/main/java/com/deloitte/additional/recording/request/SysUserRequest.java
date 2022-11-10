package com.deloitte.additional.recording.request;

import com.deloitte.common.core.annotation.Email;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @创建人 tangx
 * @创建时间 2022/11/10
 * @描述 用户新增Dto
 */
@Data
@ApiModel("用户新增请求体")
public class SysUserRequest implements Serializable {

    @ApiModelProperty("用户id。更新时必传")
    private Integer id;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email
    private String email;
    /**
     * 邮箱
     */
    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    private String name;
    /**
     * 邮箱
     */
    @ApiModelProperty("性别")
    @NotBlank(message = "性别不能为空")
    private String sex;

    /**
     * 状态：1：正常 0：注销
     */
    private String status;
    /**
     * 分组id，用,隔开
     */
    @ApiModelProperty(value = "分组id，用,隔开",example = "1,2")
    @NotBlank(message = "分组不能为空")
    private String groupid;
    /**
     * 角色id，用,隔开
     */
    @ApiModelProperty(value = "角色id，用,隔开",example = "1,2")
    @NotBlank(message = "分组不能为空")
    private String roles;


}
