package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (PrsModelQualEvd)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrsModelQualEvd implements Serializable {
    private static final long serialVersionUID = -10131690165827409L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 指标id
     */     @Excel(name = "指标id")
    private String qualCode;
    /**
     * evidence id
     */     @Excel(name = "evidence id")
    private String evdCode;
    /**
     * 状态
     */     @Excel(name = "状态")
    private Boolean status;
         @Excel(name = "${column.comment}")
    private Date created;
         @Excel(name = "${column.comment}")
    private Date updated;


}
