package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中间补录层表描述
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
public class BasRecordingTable implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 表名称(中文名称)
     */
    private String name;

    /**
     * 表名对应的编码
     */
    private String code;

    /**
     * 表描述
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
