package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (BasEvdDataDict)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BasEvdDataDict implements Serializable {
    private static final long serialVersionUID = 687586966265781339L;
    @Excel(name = "${column.comment}")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 字典分组
     */
    @Excel(name = "字典分组")
    private String dictGroup;
    /**
     * 字典键值
     */
    @Excel(name = "字典键值")
    private String dictKey;
    /**
     * 字典值
     */
    @Excel(name = "字典值")
    private String dictValue;
    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;
    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;
    /**
     * 上级id
     */
    @Excel(name = "上级id")
    private String parentId;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;


}
