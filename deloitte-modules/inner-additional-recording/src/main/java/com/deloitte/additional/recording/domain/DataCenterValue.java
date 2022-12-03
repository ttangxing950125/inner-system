package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (BasEvdData)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:38
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class DataCenterValue implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 敞口id
     */
    private String masterId;
    /**
     * 敞口名
     */
    private String name;
    /**
     * 描述
     */
    private String description;
}
