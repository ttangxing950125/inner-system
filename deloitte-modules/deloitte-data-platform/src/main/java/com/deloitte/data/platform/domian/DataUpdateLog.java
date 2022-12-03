package com.deloitte.data.platform.domian;

import com.baomidou.mybatisplus.annotation.TableName;
import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 基础数据更新日志
 *
 * @author fangliu
 * @date 2022/11/15 11:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_data_update_log")
@ApiModel(value="DataUpdateLog对象", description="基础数据更新日志 ")
public class DataUpdateLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "更新行数")
    private Integer updateRow;

    @ApiModelProperty(value = "更新频率")
    private String updateFrequency;

    @ApiModelProperty(value = "备注")
    private String remark;
}
