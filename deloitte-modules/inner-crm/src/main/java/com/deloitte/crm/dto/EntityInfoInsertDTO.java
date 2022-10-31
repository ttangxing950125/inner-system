package com.deloitte.crm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;

import javax.validation.constraints.NotNull;

/**
 * @author 正杰
 * @description: EntityInfoInsertDTO
 * @date 2022/10/25
 */
@Data
@Accessors(chain = true)
public class EntityInfoInsertDTO {

    /** 任务id */
    @NotNull(message = "当条任务id不能为空")
    @ApiModelProperty(name="taskId",value="任务id")
    private Integer taskId;

    /** 1-债券 bond_info、2-港股 stock_thk_info、3-股票  stock_cn_info */
    @ApiModelProperty(name="dataSource",value = "信息来源表")
    private Integer dataSource;

    /** 数据来源 code */
    @ApiModelProperty(name="dataCode",value = "来源主表code")
    private String dataCode;

    /** 统一社会信用代码 */
    @Validate(nullable =true)
    @ApiModelProperty(name = "creditCode" , value = "统一社会信用代码")
    private String creditCode;

    /** 企业名 */
    @ApiModelProperty(name="entityName",value="企业名")
    private String entityName;

    /** 若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容：
     1、吊销
     2、注销
     3、非大陆注册机构
     4、其他未知原因
     5、正常 */
    @ApiModelProperty(name="creditErrorType",value="若“统一社会信用代码是否异常”为0，则为5。反之，则为以下内容：1、吊销2、注销3、非大陆注册机构4、其他未知原因5、正常")
    private Integer creditErrorType;

    /** 主体的 内部德勤 code */
    @ApiModelProperty(name ="entityCode",value = "如果不是新增 那么entityCode 应该有值")
    private String entityCode;

}
