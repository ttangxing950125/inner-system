package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (TranspreEntitygovrel3rd)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TranspreEntitygovrel3rd implements Serializable {
    private static final long serialVersionUID = 764646775639740541L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 政府code
     */     @Excel(name = "政府code")
    private String govCode;
    /**
     * 政府名
     */     @Excel(name = "政府名")
    private String govName;
    /**
     * 主体名code
     */     @Excel(name = "主体名code")
    private String entityCode;
    /**
     * 数据年份
     */     @Excel(name = "数据年份")
    private Integer dataYear;
         @Excel(name = "${column.comment}")
    private Date created;
         @Excel(name = "${column.comment}")
    private Date updated;
         @Excel(name = "${column.comment}")
    private Integer status;


}
