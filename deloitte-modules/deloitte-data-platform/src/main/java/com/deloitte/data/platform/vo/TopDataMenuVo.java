package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 顶上搜索菜单
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopDataMenuVo implements Serializable {

    @ApiModelProperty(value = "1:基础层、2:中间层、3:指标层、4:主体信息")
    private Integer type;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;
}
