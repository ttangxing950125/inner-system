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
 * (PrsProjectVersions)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrsProjectVersions implements Serializable {
    private static final long serialVersionUID = 361705244178024930L;
    @Excel(name = "${column.comment}")

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 版本名称
     */
    @Excel(name = "版本名称")
    private String name;
    /**
     * 版本代码
     */
    @Excel(name = "版本代码")
    private String prjId;
    /**
     * 版本描述
     */
    @Excel(name = "版本描述")
    private String description;

    /**
     * syn_table的id
     */
    private Integer synId;

    /**
     * 来源机构
     */
    @Excel(name = "来源机构")
    private String srcOrg;
    /**
     * 是否允许一个主体出现在多个敞口下 0-不允许 1-允许
     */
    @Excel(name = "是否允许一个主体出现在多个敞口下updateOldName 0-不允许 1-允许")
    private Boolean entMultiMas;
    /**
     * 数据时间维度，选择项（与项目的时间粒度相关）
     */
    @Excel(name = "数据时间维度，选择项（与项目的时间粒度相关）")
    private String timeValue;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;
    /**
     * 状态：1-正常，0-删除
     */
    @Excel(name = "状态：1-正常，0-删除")
    private Integer status;


}
