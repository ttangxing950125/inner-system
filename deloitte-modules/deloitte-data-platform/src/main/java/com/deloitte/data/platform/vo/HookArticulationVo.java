package com.deloitte.data.platform.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 钩稽关系
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HookArticulationVo {
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "描述")
    private String checkDescribe;

    @ApiModelProperty(value = "报告期")
    private LocalDate reportDate;

    @ApiModelProperty(value = "通过率(%)")
    private BigDecimal passingRatio;

    @ApiModelProperty(value = "是否通过系统质检 0 否，1 是")
    private String isSystemInspection;

    @ApiModelProperty(value = "是否通过人工质检 0 否，1 是")
    private String isArtificialInspection;

    @ApiModelProperty(value = "是否需要人工质检 0 否，1 是")
    private String isNeedArtificialInspection;


}
