package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
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
public class BasRecordingLabelOption implements Serializable {

    /**
     * 选项id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标签code
     */
    private String labelCode;

    /**
     * 选项名称
     */
    private String optionName;

    /**
     * 1 正常 0失效
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
