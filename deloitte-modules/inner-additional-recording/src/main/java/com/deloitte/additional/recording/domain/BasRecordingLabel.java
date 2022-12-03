package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中间补录层标签表
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
public class BasRecordingLabel implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签code
     */
    private String code;

    /**
     * 所属表code
     */
    private String tableCode;

    /**
     * 标签类型 1-标准化表格 2-文本描述型 3-非标准化表格 4-非标准化表格年份
     */
    private Integer type;

    /**
     * 标签描述
     */
    private String descs;

    /**
     * 是否生效 0-失效 1-生效
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 更新时间
     */
    private LocalDateTime updated;


}
