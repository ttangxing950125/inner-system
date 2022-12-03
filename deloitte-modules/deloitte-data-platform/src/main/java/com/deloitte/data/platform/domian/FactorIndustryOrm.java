package com.deloitte.data.platform.domian;

import com.deloitte.data.platform.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 指标业务场景、业务线、敞口信息
 *
 * @author XY
 * @date 2022/11/23
 */
@Data
@ApiModel(value = "FactorIndustryOrm对象")
public class FactorIndustryOrm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务场景 来源于data_platform_config_dict表，父类为business_scene")
    private String businessScene;

    @ApiModelProperty(value = "业务线 来源于data_platform_config_dict表，父类为business_line")
    private String businessLine;

    @ApiModelProperty(value = "财报粉饰敞口")
    private String industryWhitewash;

    @ApiModelProperty(value = "指标编码")
    private String factorCode;

}
