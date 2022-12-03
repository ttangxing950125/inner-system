package com.deloitte.data.platform.dto;

import com.deloitte.data.platform.common.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 企业实体
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntityInfoDto extends BasePage implements Serializable {

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "年份")
    private List<String> years;

    @ApiModelProperty(value = "当前是否上市 0-未上市 1-已上市")
    private String list;

    @ApiModelProperty(value = "当前是否发债 0-未发债 1-已发债")
    private String issueBonds;
}
