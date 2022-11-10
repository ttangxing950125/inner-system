package com.deloitte.additional.recording.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @创建人 tangx
 * @创建时间 2022/11/10
 * @描述 用户响应体
 */

@Data
@ApiModel("用户响应体")
public class SysUserVO implements Serializable {

    @ApiModelProperty("用户id")
    private Integer id;
    /**
     * 登陆名
     */
    @ApiModelProperty("登陆名")
    private String loginname;
    /**
     * 显示名称
     */
    @ApiModelProperty("显示名称")
    private String name;
    /**
     * 登陆密码
     */
    @ApiModelProperty("登陆密码")
    private String pwd;
    /**
     * 状态：1：正常 0：注销
     */
    @ApiModelProperty("状态：1：正常 0：注销")
    private String status;
    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String phone;
    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String sex;
    /**
     * 电子邮件
     */
    @ApiModelProperty("电子邮件")
    private String email;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date created;

    @ApiModelProperty("所拥有角色名称")
    private String rolename;
    @ApiModelProperty("所属小组名称")
    private String gname;
    @ApiModelProperty("所属小组名称")
    private String group_name;

}
