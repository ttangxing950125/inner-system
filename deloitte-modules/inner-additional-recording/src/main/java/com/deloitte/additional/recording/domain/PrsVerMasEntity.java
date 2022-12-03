package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (PrsVerMasEntity)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrsVerMasEntity implements Serializable {
    private static final long serialVersionUID = -23173052712245531L;
    @Excel(name = "${column.comment}")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * prs_version_master 中的id
     */
    @Excel(name = "prs_version_master 中的id")
    private Integer verMasId;

    /**
     * 版本id
     */
    @TableField(exist = false)
    private Integer versionId;

    /**
     * 敞口code
     */
    @TableField(exist = false)
    private String modelCode;

    /**
     * 主体名的entity_code
     */
    @Excel(name = "主体名的entity_code")
    private String entityCode;


    /**
     * 状态 0-禁用 1-启用
     */
    @Excel(name = "状态 0-禁用 1-启用")
    private Boolean status;
    /**
     * 是否增量推送 0-否 1-是
     */
    @Excel(name = "是否增量推送 0-否 1-是")
    private Boolean incrStatus;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;


}
