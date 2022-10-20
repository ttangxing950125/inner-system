package com.deloitte.crm.dto;
import com.deloitte.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PenTang
 * 用于匹配检查导出结果
 * @date 2022/10/09 17:40
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExportEntityCheckDto implements Serializable {

    /** 主体全称*/
    @Excel(name = "企业名")
    @ApiModelProperty(name="entityName",value="主体全称")
    private String entityName;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    @ApiModelProperty(name="creditCode",value="统一社会信用代码")
    private String creditCode;

    /**根据统一社会信用代码识别结果*/
    @Excel(name = "根据统一社会信用代码识别结果")
    @ApiModelProperty(name="creditCodeByRecord",value="根据统一社会信用代码识别结果")
    private String  creditCodeByRecord;

    /**根据统一社会信用代码识别主体代码*/
    @Excel(name = "根据统一社会信用代码识别主体代码")
    @ApiModelProperty(name="creditCodeByEntityCode",value="根据统一社会信用代码识别主体代码")
    private  String creditCodeByEntityCode;

    /**根据统一社会信用代码识别主体的最新名称*/
    @Excel(name = "根据统一社会信用代码识别主体的最新名称")
    @ApiModelProperty(name="creditCodeByEntityName",value="根据统一社会信用代码识别主体的最新名称")
    private  String creditCodeByEntityName;


    /**根据统一社会信用代码识别主体的根据统一社会信用代码*/
    @Excel(name = "根据统一社会信用代码识别主体的根据统一社会信用代码")
    @ApiModelProperty(name="creditCodeByCreditCode",value="根据统一社会信用代码识别主体的根据统一社会信用代码")
    private String  creditCodeByCreditCode;

    /**根据主体全称识别结果*/
    @Excel(name = "根据主体全称识别结果")
    @ApiModelProperty(name="entityNameByRecord",value="根据主体全称识别结果")
    private String  entityNameByRecord;

    /**根据主体全称识别主体代码*/
    @Excel(name = "根据主体全称识别主体代码")
    @ApiModelProperty(name="entityNameByEntityCode",value="根据主体全称识别主体代码")
    private String  entityNameByEntityCode;

    /**根据主体全称识别主体名称*/
    @Excel(name = "根据主体全称识别主体名称")
    @ApiModelProperty(name="entityNameByEntityName",value="根据主体全称识别主体名称")
    private String entityNameByEntityName;

    /**根据主体全称识别统一社会信用代码*/
    @Excel(name = "根据主体全称识别统一社会信用代码")
    @ApiModelProperty(name="entityNameByEntityName",value="根据主体全称识别统一社会信用代码")
    private String entityNameByCreditCode;

    /**根据统一社会信用代码识别主体全称结果是否一致*/
    @Excel(name = "根据统一社会信用代码识别主体全称结果是否一致")
    @ApiModelProperty(name="CreditCodeIsEntityName",value="根据统一社会信用代码识别主体全称结果是否一致")
    private String  creditCodeIsEntityName;

    /**最终结果*/
    @Excel(name = "最终结果")
    @ApiModelProperty(name="endByResult",value="最终结果")
    private  String endByResult;

    /**最终匹配主体代码*/
    @Excel(name = "最终匹配主体代码结果")
    @ApiModelProperty(name="entityCodeByResult",value="最终匹配主体代码结果")
    private String  entityCodeByResult;

    /**最终匹配主体全称*/
    @Excel(name = "最终匹配主体全称结果")
    @ApiModelProperty(name="entityNameByResult",value="最终匹配主体全称结果")
    private String  entityNameByResult;

    /**最终匹配统一社会信用代码结果*/
    @Excel(name = "最终统一社会信用代码结果")
    @ApiModelProperty(name="entityNameByResult",value="最终统一社会信用代码结果")
    private String  creditCodeByResult;


    private Map<String, String> more = new HashMap<String, String>();




}
