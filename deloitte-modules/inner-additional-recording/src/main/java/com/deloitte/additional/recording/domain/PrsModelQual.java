package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (PrsModelQual)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrsModelQual implements Serializable {
    private static final long serialVersionUID = 231702060629310457L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 指标名称
     */     @Excel(name = "指标名称")
    private String qualName;
    /**
     * 指标编码
     */     @Excel(name = "指标编码")
    private String qualCode;
    /**
     * 指标排序
     */     @Excel(name = "指标排序")
    private Integer qualSort;
    /**
     * 指标类别，0-普通，1-定性，2-定量
     */     @Excel(name = "指标类别，0-普通，1-定性，2-定量")
    private Integer qualType;
    /**
     * 指标加工规则
     */     @Excel(name = "指标加工规则")
    private String formula;
    /**
     * 指标描述
     */     @Excel(name = "指标描述")
    private String description;
         @Excel(name = "${column.comment}")
    private Date created;
         @Excel(name = "${column.comment}")
    private Date updated;
    /**
     * 状态：1-正常，0-删除
     */     @Excel(name = "状态：1-正常，0-删除")
    private Integer status;
         @Excel(name = "${column.comment}")
    private String bak;


}
