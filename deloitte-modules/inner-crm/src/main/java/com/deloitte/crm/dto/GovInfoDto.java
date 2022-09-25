package com.deloitte.crm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统计政府信息
 * @author PenTang
 * @date 2022/09/22 23:14
 */
@Data
@ApiModel(value = "GovInfoDto",description = "用于统计政府信息")
public class GovInfoDto {

    /** 省 */
    @ApiModelProperty(value = "省")
   private  Integer province ;
    /** 市 */
    @ApiModelProperty(value = "市")
   private Integer city;
    /** 县*/
    @ApiModelProperty(value = "县")
    private Integer county;
    /** 经开 */
    @ApiModelProperty(value = "经开")
    private Integer open;
    /** 政府总数*/
    @ApiModelProperty(value = "政府总数")
    private Integer govSum;

}
