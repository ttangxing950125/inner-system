package com.deloitte.data.platform.domian;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据校验结果表
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="DataCheckResultItem对象", description="数据校验结果表 ")
public class DataCheckResultItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据校验结果ID")
    private Integer checkResultId;

    @ApiModelProperty(value = "报告期")
    private LocalDate reportDate;

    @ApiModelProperty(value = "主体名称")
    private String entityName;

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "是否通过 0 否，1 是")
    private String isAdopt;

    @ApiModelProperty(value = "校验键值对 用到科目的json格式")
    private String checkKeyValue;
}
