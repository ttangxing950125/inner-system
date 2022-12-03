package com.deloitte.additional.recording.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 德勤基础数据表 
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-26
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BaseFinDataRecording implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 财报编码
     */
    private String code;

    /**
     * 主体编码
     */
    private String entityCode;

    /**
     * wind值
     */
    private String windValue;

    /**
     * wind客户端值
     */
    private String windClientValue;

    /**
     * 同花顺值
     */
    private String flushValue;

    /**
     * ocr值
     */
    private String ocrValue;

    /**
     * 推荐值
     */
    private String suggestValue;

    /**
     * 人工补录值
     */
    private String recordingValue;

    /**
     * 审核后的最终值
     */
    private String checkValue;

    /**
     * 异常值备注
     */
    private String errDescription;

    /**
     * 触发的校验规则
     */
    private Integer valiRule;

    /**
     * sys_unit
     */
    private Integer unit;

    /**
     * 推荐值来源  1 wind数据库，2 wind客户端，3 同花顺，4 OCR
     */
    private String suggestSource;

    /**
     * 变动率
     */
    private BigDecimal changeRate;

    /**
     * 是否超过阈值  0 否，1 是
     */
    private String isThresholdExceeded;

    /**
     * 是否通过系统质检  0 否，1 是
     */
    private String isSystemInspection;

    /**
     * 是否通过人工质检  0 否，1 是
     */
    private String isArtificialInspection;

    /**
     * 是否人工补录  0 否，1 是
     */
    private String isArtificialRecording;

    /**
     * 报告期
     */
    private LocalDate reportDate;

    /**
     * 公告日期
     */
    private LocalDate announcementDate;

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

    /**
     * 是否创建过补录任务 0-否 1-是
     */
    private Integer isCreateRecording;

    /**
     * 是否已经已经审核完成 0-否 1-是
     */
    private Integer isCheck;

    /**
     * 数据资产平台是否拉取回去 0-否 1-是
     */
    private Integer isPullBack;


}
