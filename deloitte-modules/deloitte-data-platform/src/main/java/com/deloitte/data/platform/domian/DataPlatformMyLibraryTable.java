package com.deloitte.data.platform.domian;

import java.time.LocalDateTime;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据平台我的库表
 *
 * @author fangliu
 * @date 2022/11/23 17:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="DataPlatformMyLibraryTable对象", description="数据平台我的库表")
public class DataPlatformMyLibraryTable extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户Id")
    private long userId;

    @ApiModelProperty(value = "字典编码")
    private String code;

    @ApiModelProperty(value = "父级字典编码")
    private String parentCode;

    @ApiModelProperty(value = "逻辑删除 0 正常，1 删除")
    private String isDeleted;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新人")
    private String updatedBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedTime;


}
