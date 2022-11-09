package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (SysGroup)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SysGroup implements Serializable {
    private static final long serialVersionUID = -84253954998778810L;
         @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 组名
     */     @Excel(name = "组名")
    private String groupName;
         @Excel(name = "${column.comment}")
    private String groupDesc;
    /**
     * 组长
     */     @Excel(name = "组长")
    private Integer groupLeader;
         @Excel(name = "${column.comment}")
    private Date addTime;


}
