package com.deloitte.crm.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 正杰
 * @description: EntityVo
 * @date 2022/9/28
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "EntityVo",description = "模糊匹配查询 主体 返回结果")
public class EntityVo {

    @ApiModelProperty(value="主体id")
    private Integer id;

    @ApiModelProperty(value="德勤主体代码")
    private String entityCode;

    @ApiModelProperty(value="企业名称")
    private String entityName;

    @ApiModelProperty(value="统一社会信用代码")
    private String creditCode;

    @ApiModelProperty(value="债券代码")
    private String bondCode;

}
