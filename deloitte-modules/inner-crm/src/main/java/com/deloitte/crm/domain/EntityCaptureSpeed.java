package com.deloitte.crm.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
* 
* @TableName entity_capture_speed
*/
@Data
@Accessors(chain = true)
public class EntityCaptureSpeed implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer id;
    /**
    * 来源
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("来源")
    @Length(max= 255,message="编码长度不能超过255")
    private String source;
    /**
    * 主体名
    */
    @Size(max= 80,message="编码长度不能超过80")
    @ApiModelProperty("主体名")
    @Length(max= 80,message="编码长度不能超过80")
    private String entity_name;
    /**
    * 
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
    @Length(max= 255,message="编码长度不能超过255")
    private String entity_code;
    /**
    * 0.未捕获 1.已捕获
是否已捕获 （导入后，角色6还未新增） 这时候wind_task应该有数据
    */
    @ApiModelProperty("0.未捕获 1.已捕获是否已捕获 （导入后，角色6还未新增） 这时候wind_task应该有数据")
    private Integer capture;
    /**
    * 0-未处理 1-已有主体 2-新增主体 角色6是否新增,不为0就是已处理
    */
    @ApiModelProperty("0-未处理 1-已有主体 2-新增主体 角色6是否新增,不为0就是已处理")
    private Integer added;
    /**
    * 0-未处理 1-已处理 角色2是否已处理
    */
    @ApiModelProperty("0-未处理 1-已处理 角色2是否已处理")
    private Integer divide;
    /**
    * 0-未处理 1-已处理  角色3,4,5任务处理状态 
    */
    @ApiModelProperty("0-未处理 1-已处理  角色3,4,5任务处理状态 ")
    private Integer supplement;
    /**
    * 0-未推送 1-已推送 是否推送补录平台
    */
    @ApiModelProperty("0-未推送 1-已推送 是否推送补录平台")
    private Integer push_meta;
    /**
    * 是否初始化的数据
    */
    @ApiModelProperty("是否初始化的数据")
    private Integer auto;
    /**
    * 捕获时间
    */
    @ApiModelProperty("捕获时间")
    private Date capture_time;
    /**
    * 最后更新的用户，用户名
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("最后更新的用户，用户名")
    @Length(max= 20,message="编码长度不能超过20")
    private String updater;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date created;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private Date updated;

}
