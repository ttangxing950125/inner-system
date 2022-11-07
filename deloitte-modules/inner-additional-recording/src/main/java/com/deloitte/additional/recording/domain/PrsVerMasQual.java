package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (PrsVerMasQual)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrsVerMasQual implements Serializable {
    private static final long serialVersionUID = -98565778534830220L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * prs_version_master  表的id
     */     @Excel(name = "prs_version_master  表的id")
    private Integer verMasId;
    /**
     * prs_model_qual 表的qual_code
     */     @Excel(name = "prs_model_qual 表的qual_code")
    private String qualCode;
    /**
     * 状态 1-启用 0-禁用
     */     @Excel(name = "状态 1-启用 0-禁用")
    private Boolean status;
         @Excel(name = "${column.comment}")
    private String remark;
         @Excel(name = "${column.comment}")
    private Date created;
         @Excel(name = "${column.comment}")
    private Date updated;


}
