package com.deloitte.data.platform.domian;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 财务数据配置
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="FinancialDataConfig对象", description="财务数据配置 ")
public class FinancialDataConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "科目编码")
    private String code;

    @ApiModelProperty(value = "科目名称")
    private String name;

    @ApiModelProperty(value = "科目值精度 整数表示精确到几位，默认精确到2位")
    private Integer accuracy;

    @ApiModelProperty(value = "数据类型")
    private String dataType;

    @ApiModelProperty(value = "变动率上限")
    private BigDecimal changeRateUpper;

    @ApiModelProperty(value = "值域")
    private String thresholdValue;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "顺序 科目在页面显示的顺序")
    private String seq;

    @ApiModelProperty(value = "表类型 1 主表，2 附注")
    private String tableType;

    @ApiModelProperty(value = "主体类型 一般企业、金融机构等区分")
    private String entityType;
}
