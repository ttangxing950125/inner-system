package com.deloitte.additional.recording.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (TranspreEntitygovrel3rd)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TranspreEntitygovrel3rd implements Serializable {
    private static final long serialVersionUID = 764646775639740541L;
    @Excel(name = "${column.comment}")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 政府code
     */
    @Excel(name = "政府code")
    private String govCode;
    /**
     * 政府名
     */
    @Excel(name = "政府名")
    private String govName;
    /**
     * 主体名code
     */
    @Excel(name = "主体名code")
    private String entityCode;
    /**
     * 数据年份
     */
    @Excel(name = "数据年份")
    private Integer dataYear;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;
    @Excel(name = "${column.comment}")
    private Integer status;


}
