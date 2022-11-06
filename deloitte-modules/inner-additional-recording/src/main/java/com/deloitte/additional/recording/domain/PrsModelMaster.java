package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (PrsModelMaster)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrsModelMaster implements Serializable {
    private static final long serialVersionUID = 837763217380687555L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 敞口名称
     */     @Excel(name = "敞口名称")
    private String name;
    /**
     * 敞口编码
     */     @Excel(name = "敞口编码")
    private String modelCode;
    /**
     * 敞口描述
     */     @Excel(name = "敞口描述")
    private String description;
         @Excel(name = "${column.comment}")
    private Date created;
         @Excel(name = "${column.comment}")
    private Date updated;
    /**
     * 状态：1-正常，0-删除
     */     @Excel(name = "状态：1-正常，0-删除")
    private Integer status;


}
