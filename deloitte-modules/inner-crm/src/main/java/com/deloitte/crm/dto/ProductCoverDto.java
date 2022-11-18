package com.deloitte.crm.dto;

import com.deloitte.common.core.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PenTang
 * @date 2022/10/12 18:54
 */
@Data
public class ProductCoverDto {


    /**
     * 企业名
     */
    @Excel(name = "企业名")
    private String entityName;
    /**
     * 统一社会性代码
     */
    private String creditCode;

    /**
     * IB+自000001开始排序，每个企业唯一
     */
    private String entityCode;

    private Integer status;

    /**
     *返回列数据
     */
    private ArrayList<HashMap<String,String>> result;



}
