package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.ibm.icu.util.LocaleData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author PenTang
 * @date 2022/11/02 11:59
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class BondsListingLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 债券or股票全名
     */
    private String name;
    /**
     * 债券or股票简称
     */
    private String shortName;
    /**
     * 债券or股票code
     */
    private String code;
    /**
     * 上市日期
     */
    private LocalDateTime ipoDate;
    /**
     * 发债日期
     */
    private LocalDateTime issueDate;
    /**
     * 发行人
     */
    private String publisher;
    /**
     * 记录时间
     */
    private Date recordTime;

    /**
     * 1-债券 、2-港股、3-股票
     */
    private Integer sourceType;

    /**
     * $column.columnComment
     */
    private Date created;

    /**
     * $column.columnComment
     */
    private Date updated;
}
