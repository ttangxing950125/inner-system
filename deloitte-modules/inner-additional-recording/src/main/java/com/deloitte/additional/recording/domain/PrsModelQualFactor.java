package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (PrsModelQualFactor)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrsModelQualFactor implements Serializable {
    private static final long serialVersionUID = -37216700976588231L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 指标id
     */     @Excel(name = "指标id")
    private String qualCode;
    /**
     * 档位值
     */     @Excel(name = "档位值")
    private String factorValue;
    /**
     * 档位描述
     */     @Excel(name = "档位描述")
    private String factorItem;
         @Excel(name = "${column.comment}")
    private Date created;
         @Excel(name = "${column.comment}")
    private Date updated;
    /**
     * 档位状态，1-在用，0-禁用
     */     @Excel(name = "档位状态，1-在用，0-禁用")
    private Integer status;


}
