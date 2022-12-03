package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 结构化附注表 
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseStructuredNotes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 附注编码
     */
    private String code;

    /**
     * 附注值
     */
    private String value;

    /**
     * 人工补录的值
     */
    private String recordingValue;

    /**
     * 最终审核的值
     */
    private String checkValue;

    /**
     * sys_unit
     */
    private Integer unit;

    /**
     * 主体编码
     */
    private String entityCode;

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
     * 是否审核完成 0-否 1-是
     */
    private Integer isCheck;

    private Integer isPullBack;


}
