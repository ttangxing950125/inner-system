package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (PrsVersionMaster)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrsVersionMaster implements Serializable {
    private static final long serialVersionUID = -57806542470018572L;
    @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 版本表，prs_project_version_id
     */
    @Excel(name = "版本表，prs_project_version_id")
    private String prjId;
    /**
     * 敞口表，prs_model_master model_code
     */
    @Excel(name = "敞口表，prs_model_master model_code")
    private String modelCode;
    /**
     * 关联状态
     */
    @Excel(name = "关联状态")
    private Boolean status;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;


}
