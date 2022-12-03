package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 基础数据字典
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataUpdateInfoVo implements Serializable {

    @ApiModelProperty(value = "数据更新频率")
    private String updateFrequency;

    @ApiModelProperty(value = "更新时间")
    private String updatedTime;

    @ApiModelProperty(value = "数据更新说明")
    private String remark;
}
