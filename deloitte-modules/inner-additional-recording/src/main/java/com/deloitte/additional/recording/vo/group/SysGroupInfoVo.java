package com.deloitte.additional.recording.vo.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @创建人 tangx
 * @创建时间 2022/11/10
 * @描述 分组详情响应体
 */
@Data
@ApiModel("分组详情响应体")
public class SysGroupInfoVo implements Serializable {

    @ApiModelProperty("分组id")
    private Integer id;

    @ApiModelProperty("分组名称")
    private String groupName;


    @ApiModelProperty("组长id")
    private Integer leaderId;

    @ApiModelProperty(value = "组员id字符；用,拼接",example = "1,2")
    private String grouMember;
}
