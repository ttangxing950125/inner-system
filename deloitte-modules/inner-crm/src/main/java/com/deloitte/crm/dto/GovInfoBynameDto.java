package com.deloitte.crm.dto;

import com.deloitte.common.core.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;

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

    /** 德勤主体代码 */
    @Excel(name = "官方行政代码，六位数字，各地方唯一")
    private String dqCode;

    /** 官方行政代码 */
    @Excel(name = "官方行政代码")
    private  String govCode;
    /** 官方行政区划 */
    private String levelName;

    /**
     *返回列数据
     */
    private ArrayList<HashMap<String,String>> result;


}
