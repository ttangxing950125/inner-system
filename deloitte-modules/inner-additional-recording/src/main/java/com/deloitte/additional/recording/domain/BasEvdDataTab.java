package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * bas_evd_data_tab 表
 *
 * @创建人 tangx
 * @创建时间 2022/11/22
 * @描述
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BasEvdDataTab implements Serializable {

    @Excel(name = "${column.comment}")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 主体id
     */

    private String entityCode;
    /**
     * parentCode
     */

    private String parentCode;

    /**
     * evdCode
     */
    private String evdCode;


    /**
     * 表格子项evidence的数据
     */
    private String evdVal;


    /**
     * 补录时表格子项的数据
     */
    private String recordingVal;

    /**
     * 备注
     */
    private String dataMark;
    /**
     * 表格第几行
     */
    private int row;

    public BasEvdDataTab createBy(String entityCode, String parentCode, String evdCode, String evdVal, Integer row) {
        this.entityCode = entityCode;
        this.parentCode = parentCode;
        this.evdCode = evdCode;
        this.evdVal = evdVal;
        this.row = row;

        return this;
    }
}
