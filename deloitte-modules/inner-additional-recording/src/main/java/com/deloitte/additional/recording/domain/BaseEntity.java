package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @创建人 tangx
 * @创建时间 2022/11/24
 * @描述
 */
@Data
public abstract  class BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("updated")
    private Date updated;

    @TableField("created")
    private Date created;
}
