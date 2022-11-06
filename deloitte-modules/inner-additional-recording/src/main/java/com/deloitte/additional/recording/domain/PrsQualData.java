package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (PrsQualData)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrsQualData implements Serializable {
    private static final long serialVersionUID = 306345855166810635L;
    @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 主体的id
     */
    @Excel(name = "主体的id")
    private String entityCode;
    /**
     * 指标的id
     */
    @Excel(name = "指标的id")
    private String qualCode;
    /**
     * 指标值
     */
    @Excel(name = "指标值")
    private String qualValue;
    /**
     * 数据时间标识
     */
    @Excel(name = "数据时间标识")
    private Integer timeValue;
    /**
     * 数据来源标识
     */
    @Excel(name = "数据来源标识")
    private String dataMark;
    /**
     * 数据备注
     */
    @Excel(name = "数据备注")
    private String remark;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;


}
