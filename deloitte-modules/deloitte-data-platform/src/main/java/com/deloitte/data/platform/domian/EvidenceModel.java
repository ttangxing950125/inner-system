package com.deloitte.data.platform.domian;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Evidence模型配置
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="EvidenceModel对象", description="Evidence模型配置 ")
public class EvidenceModel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Evidence名称")
    private String evidenceName;

    @ApiModelProperty(value = "Evidence编码")
    private String evidenceCode;

    @ApiModelProperty(value = "Evidence公式")
    private String evidenceFormula;

    @ApiModelProperty(value = "Evidence描述")
    private String evidenceDescribe;

    @ApiModelProperty(value = "Evidence公式描述")
    private String evidenceFormulaDescribe;

    @ApiModelProperty(value = "Evidence状态 0 禁用，1 启用")
    private String evidenceStatus;
}
