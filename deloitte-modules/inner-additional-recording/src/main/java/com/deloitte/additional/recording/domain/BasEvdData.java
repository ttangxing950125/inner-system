package com.deloitte.additional.recording.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 主体id
     */
    @ExcelProperty(value = "实体代码")
    private String entityCode;
    /**
     * evidence_id
     */
    @ExcelProperty(value = "evidence_id")
    private String evdCode;

    @TableField(exist = false)
    private String evdName;
    /**
     * json格式的evidence
     */
    @Excel(name = "evd的值")
    private String evdVal = "-999999.9999";
    /**
     * 数据时间标识
     */
    @ExcelProperty(value = "数据年份")
    private String timeValue;
    /**
     * 数据针对划档流程是否可用
     */
    @Excel(name = "数据针对划档流程是否可用")
    private Integer valid;

    /**
     * 是否缺失
     */
    private Integer  defect;
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

    private String unit;


    public BasEvdData createBy(String entityCode, String evdCode, String evdVal, String timeValue) {
        Date date = new Date();
        this.entityCode = entityCode;
        this.evdCode = evdCode;
        this.evdVal = evdVal;
        this.timeValue = timeValue;
        this.valid = 0;
        this.created = date;
        this.updated = date;
        return this;
    }

    public void updateBy(String evdVal) {
    }
}
