package com.deloitte.additional.recording.vo.master;

import com.deloitte.common.core.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @创建人 tangx
 * @创建时间 2022/11/16
 * @描述 敞口下拉选择列表响应体
 */

@Data
@ApiModel("敞口下拉选择列表响应体")
public class PrsModelMasterSelectVO implements Serializable {

    @ApiModelProperty(name = "敞口id")
    private Integer id;
    /**
     * 敞口名称
     */
    @ApiModelProperty(name = "敞口名称")
    private String name;

}
