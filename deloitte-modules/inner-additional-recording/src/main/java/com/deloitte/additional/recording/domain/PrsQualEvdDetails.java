package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * evd字段比较大，单独拎出来了(PrsQualEvdDetails)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrsQualEvdDetails implements Serializable {
    private static final long serialVersionUID = -42789326292845439L;
    @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * data_qual 表的id
     */
    @Excel(name = "data_qual 表的id")
    private Integer dataId;
    /**
     * 组装好的evidence
     */
    @Excel(name = "组装好的evidence")
    private String evidences;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;


}
