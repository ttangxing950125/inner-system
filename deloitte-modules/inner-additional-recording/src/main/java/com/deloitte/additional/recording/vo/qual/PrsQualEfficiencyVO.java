package com.deloitte.additional.recording.vo.qual;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @创建人 tangx
 * @创建时间 2022/11/24
 * @描述 指标-任务响应体
 */
@Data
@ApiModel("指标-任务响应体")
public class PrsQualEfficiencyVO {

    @ApiModelProperty("id")
    private Integer id;
    /**
     * 指标code
     */
    @ApiModelProperty("指标code")
    private String qualCode;

    /**
     * 指标名称
     */
    @ApiModelProperty("指标名称")
    private String qualName;

    /**
     * 主体数量
     */
    @ApiModelProperty(" 主体数量")
    private Integer entityCount;

    /**
     * 风险级别 参考字典表 riskLevel
     * 1	高风险
     * 2	中风险
     * 3	低风险
     * 4	无风险
     */
    @ApiModelProperty("风险级别:1-高风险 2-中风险 3-低风险 4-无风险")
    private Integer riskLevel;
    /**
     * 缺失率
     */
    @ApiModelProperty("缺失率")
    private BigDecimal missingRate;
    /**
     * 预期效率
     */
    @ApiModelProperty("预期效率")
    private Integer expectedEfficiency;
    /**
     * 实际效率
     */
    @ApiModelProperty("实际效率")
    private Integer actualEfficiency;

    /**
     * 通过率
     */
    @ApiModelProperty("通过率")
    private BigDecimal passingRate;
    /**
     * 打回率
     */
    @ApiModelProperty("打回率")
    private BigDecimal returnRate;
    /**
     * 最后一次跑批时间，页面上的更新时间
     */
    @ApiModelProperty("最后一次跑批时间，页面上的更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date runBatchTime;


}
