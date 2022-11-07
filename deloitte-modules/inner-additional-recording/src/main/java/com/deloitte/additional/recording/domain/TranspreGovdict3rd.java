package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (TranspreGovdict3rd)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TranspreGovdict3rd implements Serializable {
    private static final long serialVersionUID = -64613999974204774L;
    /**
     * id
     */     @Excel(name = "id")
    private Object id;
    /**
     * 政府编码
     */     @Excel(name = "政府编码")
    private String govCode;
    /**
     * 上级政府编码
     */     @Excel(name = "上级政府编码")
    private String pareGovCode;
    /**
     * 政府名称
     */     @Excel(name = "政府名称")
    private String govName;
    /**
     * 政府级别
     */     @Excel(name = "政府级别")
    private String govLevel;
         @Excel(name = "${column.comment}")
    private Integer dataYear;
         @Excel(name = "${column.comment}")
    private Date addTime;


}
