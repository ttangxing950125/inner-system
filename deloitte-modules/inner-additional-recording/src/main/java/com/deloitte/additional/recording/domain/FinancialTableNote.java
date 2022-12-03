package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FinancialTableNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 三表一注名称
     */
    private String name;

    /**
     * 三表一注内部编码
     */
    private String code;

    /**
     * 1 三表 2 结构化附注 3.非结构化附注
     */
    private Integer type;

    /**
     * 非结构化附注，附注对应的数据表名称
     */
    private String tableName;

    /**
     * 字典表 reportType
     */
    private String reportType;

    /**
     * 字典表 dataFrequency
     */
    private String dataFrequency;

    /**
     * 数据采集规则
     */
    private String collRule;

    private Date created;

    private Date updated;


}
