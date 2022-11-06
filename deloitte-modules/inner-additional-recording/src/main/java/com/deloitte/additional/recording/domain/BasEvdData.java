package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (BasEvdData)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:38
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BasEvdData implements Serializable {
    private static final long serialVersionUID = 140174352691823364L;
    @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * 主体id
     */
    @Excel(name = "主体id")
    private String entityCode;
    /**
     * evidence_id
     */
    @Excel(name = "evidence_id")
    private String evdCode;
    /**
     * json格式的evidence
     */
    @Excel(name = "json格式的evidence")
    private String evdVal;
    /**
     * 数据时间标识
     */
    @Excel(name = "数据时间标识")
    private String timeValue;
    /**
     * 数据针对划档流程是否可用
     */
    @Excel(name = "数据针对划档流程是否可用")
    private Integer valid;
    /**
     * 数据来源信息
     */
    @Excel(name = "数据来源信息")
    private Integer srcId;
    /**
     * 数据来源标识
     */
    @Excel(name = "数据来源标识")
    private String dataMark;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;
    /**
     * 是否修改自动化结果
     */
    @Excel(name = "是否修改自动化结果")
    private Integer isUpdate;


}
