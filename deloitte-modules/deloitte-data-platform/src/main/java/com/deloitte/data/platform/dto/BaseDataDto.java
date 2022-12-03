package com.deloitte.data.platform.dto;

import com.deloitte.data.platform.common.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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
public class BaseDataDto extends BasePage {
    @ApiModelProperty(value = "关键字/英文名称/中文名称")
    private String keyWord;

    @ApiModelProperty(value = "主体编码")
    private String entityCode;

    @ApiModelProperty(value = "年份")
    @NotNull(message = "年份不能为空")
    private List<String> years;

    @ApiModelProperty(value = "数据来源(1 wind数据库，2 wind客户端，3 同花顺，4 OCR)")
    private List<String> sources;

    @ApiModelProperty(value = "字段代码")
    @NotEmpty(message = "字段代码不能为空")
    private String code;
}
