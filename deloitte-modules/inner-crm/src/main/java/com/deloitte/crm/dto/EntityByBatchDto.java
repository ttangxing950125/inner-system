package com.deloitte.crm.dto;

import com.deloitte.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author PenTang
 * 用于读取批量查询的模板数据
 * @date 2022/10/09 15:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EntityByBatchDto implements Serializable {

    /** 主体全称*/
    @Excel(name = "企业名")
    @ApiModelProperty(name="entityName",value="主体全称")
    private String entityName;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    @ApiModelProperty(name="creditCode",value="统一社会信用代码")
    private String creditCode;

}
