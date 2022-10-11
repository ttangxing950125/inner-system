package com.deloitte.crm.dto;

import com.deloitte.common.core.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author PenTang
 * @date 2022/10/10 14:50
 */
@Data
@Accessors(chain = true)
public class GovInfoBynameDto {

    /** 政府主体官方名称 */
    @Excel(name = "政府主体官方名称")
    private String govName;

    /** 官方行政代码，六位数字，各地方唯一 */
    @Excel(name = "官方行政代码，六位数字，各地方唯一")
    private String govCode;

    /** 官方行政名称 */
    @Excel(name = "官方行政名称")
    private  String govCodeName;


}
