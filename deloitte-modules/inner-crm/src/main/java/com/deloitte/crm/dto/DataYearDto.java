package com.deloitte.crm.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author PenTang
 * @date 2022/10/17 11:49
 */
@Data
@ApiModel(value = "DataYearDto",description = "用于返回产品的年份")
public class DataYearDto {
    private  String DataYear;
}
