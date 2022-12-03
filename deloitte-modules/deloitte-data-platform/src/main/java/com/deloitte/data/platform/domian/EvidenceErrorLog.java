package com.deloitte.data.platform.domian;

import com.baomidou.mybatisplus.annotation.TableField;
import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Evidence执行结果错误日志
 *
 * @author XY
 * @date 2022/11/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "EvidenceErrorLog对象", description = "Evidence执行结果错误日志")
public class EvidenceErrorLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主体编码")
    @TableField(value = "entity_code")
    private String entityCode;

    @ApiModelProperty(value = "报告期")
    @TableField(value = "report_date")
    private LocalDate reportDate;

    @ApiModelProperty(value = "evidence编码")
    @TableField(value = "evidence_code")
    private String evidenceCode;

    @ApiModelProperty(value = "原始公式")
    @TableField(value = "primal_formula")
    private String primalFormula;

    @ApiModelProperty(value = "翻译过后的公式")
    @TableField(value = "translate_formula")
    private String translateFormula;


}
