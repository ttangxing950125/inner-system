package com.deloitte.additional.recording.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 财务数据配置 
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FinancialDataConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 科目编码
     */
    private String code;

    /**
     * 科目名称
     */
    private String name;

    /**
     * 科目值精度 整数表示精确到几位，默认精确到2位
     */
    private Integer accuracy;

    /**
     * 三表/附注 内部编码
     */
    private String dataTypeCode;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 变动率上限
     */
    private BigDecimal changeRateUpper;

    /**
     * 值阈
     */
    private String thresholdValue;

    /**
     * 币种
     */
    private String currency;

    /**
     * 单位 1 元，2 万元，3 亿元，4 数值，5 文本，6 百分比
     */
    private String unit;

    /**
     * 顺序 科目在页面显示的顺序
     */
    private String seq;

    /**
     * 表类型 1 主表，2 附注
     */
    private String tableType;

    /**
     * 主体类型 一般企业、金融机构等区分
     */
    private String entityType;

    /**
     * 逻辑删除 0 正常，1 删除
     */
    private String isDeleted;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
