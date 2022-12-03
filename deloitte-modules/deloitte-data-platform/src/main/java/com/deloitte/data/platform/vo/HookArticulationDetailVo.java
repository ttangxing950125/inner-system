package com.deloitte.data.platform.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 钩稽关系详情
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HookArticulationDetailVo {
    @ApiModelProperty(value = "表头")
    private List<String> headers;
    @ApiModelProperty(value = "分页数据")
    private List<List<String>> records;
    @ApiModelProperty(value = "总条数")
    private long total;
    @ApiModelProperty(value = "每页条数")
    private long size;
    @ApiModelProperty(value = "当前页")
    private long current;
    @ApiModelProperty(value = "总页数")
    private long pages;
}
