package com.deloitte.crm.dto;

import com.deloitte.common.core.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统计主体信息
 * @author PenTang
 * @date 2022/09/22 22:34
 */
@Data
@ApiModel(value = "EntityInfoDto",description = "用于统计主体信息")
public class EntityInfoDto {
    /** 是否上市 0-未上市 1-已上市 */
    @ApiModelProperty(value = "/** 是否上市 0-未上市 1-已上市 */")
    private Integer list;

    /** 是否发债 0-未发债 1-已发债 */
    @ApiModelProperty(value = "是否发债 0-未发债 1-已发债")
    private Integer issueBonds;

    /** 否金融机构 0-否1-是 是 */
    @ApiModelProperty(value = "否金融机构 0-否1-是 是")
    private Integer finance;

    /** 上市又发债*/
    @ApiModelProperty(value = "上市又发债")
    private Integer BondsAndList;

    /** 非上市又非发债*/
    @ApiModelProperty(value = "非上市又非发债")
    private Integer notBondsAndList;

    /** 企业主体总数*/
    @ApiModelProperty(value = "企业主体总数")
    private Integer entitySum;




}
