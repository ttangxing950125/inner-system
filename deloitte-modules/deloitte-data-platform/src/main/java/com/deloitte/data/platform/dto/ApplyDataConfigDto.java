package com.deloitte.data.platform.dto;

import com.deloitte.data.platform.common.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础数据字典
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDataConfigDto extends BasePage {
    @ApiModelProperty(value = "关键字/英文名称/中文名称")
    private String keyWord;

    @ApiModelProperty(value = "编码")
    private String code;
}
