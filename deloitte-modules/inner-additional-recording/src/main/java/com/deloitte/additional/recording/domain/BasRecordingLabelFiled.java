package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中间补录 层表格字段表
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BasRecordingLabelFiled implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属标签code
     */
    private String labelCode;

    /**
     * 字段数据来源 1-evd 2-三表 3-附注 目前只考虑evd
     */
    private Integer sourceType;

    /**
     * 来源数据的 code
     */
    private String sourceCode;

    /**
     * 字段描述
     */
    private String desc;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 更新时间
     */
    private LocalDateTime updated;


}
