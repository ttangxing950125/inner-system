package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (TranspreModelinfo3rd)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TranspreModelinfo3rd implements Serializable {
    private static final long serialVersionUID = -85643658100364648L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 补录平台的版本id
     */     @Excel(name = "补录平台的版本id")
    private Integer useVersion;
    /**
     * 补录平台的敞口id
     */     @Excel(name = "补录平台的敞口id")
    private Integer useModel;
    /**
     * 中心库对应机构的敞口code
     */     @Excel(name = "中心库对应机构的敞口code")
    private String tarMid;
    /**
     * 中心库对应机构的表的前缀
     */     @Excel(name = "中心库对应机构的表的前缀")
    private String prefix;
         @Excel(name = "${column.comment}")
    private String dataYear;
    /**
     * 一般敞口1，政府-2
     */     @Excel(name = "一般敞口1，政府-2")
    private Integer modelType;
         @Excel(name = "${column.comment}")
    private String remark;
         @Excel(name = "${column.comment}")
    private Date optime;


}
