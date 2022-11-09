package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
/**
 * (SysLog)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:27
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SysLog implements Serializable {
    private static final long serialVersionUID = -46807730996434012L;
         @Excel(name = "${column.comment}")
    private Integer id;
         @Excel(name = "${column.comment}")
    private String userId;
         @Excel(name = "${column.comment}")
    private String userIp;
         @Excel(name = "${column.comment}")
    private Date addTime;
         @Excel(name = "${column.comment}")
    private String module;
         @Excel(name = "${column.comment}")
    private String moduleAction;
         @Excel(name = "${column.comment}")
    private String url;
         @Excel(name = "${column.comment}")
    private String remark;
         @Excel(name = "${column.comment}")
    private String remark1;


}
