package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (BasEvdValrange)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BasEvdValrange implements Serializable {
    private static final long serialVersionUID = -81451919296422931L;
    @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * evidence id
     */
    @Excel(name = "evidence id")
    private Integer evdId;
    /**
     * evidence值
     */
    @Excel(name = "evidence值")
    private String value;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;


}
