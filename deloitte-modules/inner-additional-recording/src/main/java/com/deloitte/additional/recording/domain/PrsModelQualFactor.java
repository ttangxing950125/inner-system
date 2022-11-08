package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (PrsModelQualFactor)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class PrsModelQualFactor implements Serializable {
    private static final long serialVersionUID = -37216700976588231L;
    @Excel(name = "${column.comment}")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 指标id
     */
    @Excel(name = "指标id")
    private String qualCode;
    /**
     * 档位值
     */
    @Excel(name = "档位值")
    private String factorValue;
    /**
     * 档位描述
     */
    @Excel(name = "档位描述")
    private String factorItem;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;
    /**
     * 档位状态，1-在用，0-禁用
     */
    @Excel(name = "档位状态，1-在用，0-禁用")
    private Integer status;


}
