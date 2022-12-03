package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
@ToString
public class PrsQualData implements Serializable {
    private static final long serialVersionUID = 306345855166810635L;
    @Excel(name = "${column.comment}")
    @TableId(type = IdType.AUTO)
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

    /**
     * 分配用户
     */
    private Integer assignUser;

    /*
     *任务状态：1-未分配 2-已提交 3-已分配
     */
    private Integer taskStatus;

    public PrsQualData createBy(String qualCode, String entityCode, String qualValue, Integer timeValue) {
        Date date = new Date();
        this.created = date;
        this.qualValue = qualValue;
        this.qualCode = qualCode;
        this.entityCode = entityCode;
        this.timeValue = timeValue;
        this.updated = date;
        this.taskStatus = 1;//设置为未分配
        return this;
    }

    public void setProperties(PrsQualData data, String qualValue, Integer taskStatus, Integer assignUser) {
        data.taskStatus = taskStatus;
        data.assignUser = assignUser;
        data.qualValue = qualValue;
    }
}
