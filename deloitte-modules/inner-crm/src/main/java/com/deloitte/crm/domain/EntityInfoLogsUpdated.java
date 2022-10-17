package com.deloitte.crm.domain;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 正杰
 * @description: 内部更新记录表
 * @date 2022/10/17
 *
 * 该表为 内部跟新记录表
 * 操作方式为 =>
 * 修改内部更新记录时 将字段存入该表
 * 提供内部更新记录的 查看，修改 方法
 * 存入时，必须填入当条数据的 主体代码，表名，字段名，属性值；
 *
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EntityInfoLogsUpdated{

    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private Integer id;

    /**
    * 德勤主体代码
    */
    @Size(max= 11,message="编码长度不能超过11")
    @ApiModelProperty("德勤主体代码")
    @NotNull(message="[ 主体代码 ]不能为空")
    private String entityCode;

    /**
    * 证券简称
    */
    @Size(max= 11,message="编码长度不能超过11")
    @ApiModelProperty("证券简称")
    private String stockShortName;

    /**
    * 字段名称
    */
    @Size(max= 11,message="编码长度不能超过11")
    @ApiModelProperty("字段名称")
    private String fieldName;

    /**
    * 已存值
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("已存值")
    private String originalValue;

    /**
    * 已存值录入日期
    */
    @ApiModelProperty("已存值录入日期")
    private Date created;

    /**
    * 修改值
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("修改值")
    @NotNull(message="[ 属性值 ]不能为空")
    private String value;

    /**
    * 是否生效 是否生效  0-失效( 已经撤回 ) 1-生效
    */
    @Size(max= 1,message="编码长度不能超过1")
    @ApiModelProperty("是否生效")
    @NotNull(message="[ 是否生效 ]不能为空")
    private Boolean status;

    /**
    * 修改日期
    */
    @ApiModelProperty("修改日期")
    private Date updated;

    /**
    * 修改人
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("修改人")
    private String userName;

    /**
    * 字段来源表名
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("字段来源表名")
    @NotNull(message="[ 字段来源表名 ]不能为空")
    private String tableName;

    /**
    * 字段来源名称
    */
    @NotNull(message="[ 字段来源名称 ]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("字段来源名称")
    private String tableFieldName;

    /**
    * 类型判断
    */
    @NotNull(message="[ 类型判断 ]不能为空")
    @Size(max= 1,message="编码长度不能超过1")
    @ApiModelProperty("1-企业上市信息 2-地方政府上市信息")
    private Integer tableType;

}
