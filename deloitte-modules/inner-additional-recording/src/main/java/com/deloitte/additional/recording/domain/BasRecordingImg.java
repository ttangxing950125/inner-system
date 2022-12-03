package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BasRecordingImg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 1-evidence补录 2-三表一注补录 3-中间层标签补录
     */
    private Integer taskType;

    /**
     * 对应的任务表id
     */
    private Integer taskId;

    /**
     * 服务器上的全路径
     */
    private String path;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 更新时间
     */
    private LocalDateTime updated;


}
