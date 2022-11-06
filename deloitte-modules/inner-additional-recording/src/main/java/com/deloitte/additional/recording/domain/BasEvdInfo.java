package com.deloitte.additional.recording.domain;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (BasEvdInfo)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BasEvdInfo implements Serializable {
    private static final long serialVersionUID = 567385199741115051L;
    @Excel(name = "${column.comment}")
    private Integer id;
    /**
     * evidence编码
     */
    @Excel(name = "evidence编码")
    private String code;
    /**
     * evidence名称
     */
    @Excel(name = "evidence名称")
    private String name;
    /**
     * 上级evidence的id
     */
    @Excel(name = "上级evidence的id")
    private Integer parentId;
    /**
     * 值类型
     */
    @Excel(name = "值类型")
    private String valType;
    /**
     * 值属性
     */
    @Excel(name = "值属性")
    private String valAttr;
    /**
     * 显示类别
     */
    @Excel(name = "显示类别")
    private String dispType;
    /**
     * 显示属性
     */
    @Excel(name = "显示属性")
    private String dispAttr;
    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段顺序
     */
    @Excel(name = "字段顺序")
    private Integer sort;
    /**
     * 数据输入，1-人工，2-wind,3-自动化，4-二次处理
     */
    @Excel(name = "数据输入，1-人工，2-wind,3-自动化，4-二次处理")
    private Integer src;
    /**
     * 真实数据来源，1-人工，2-wind,3-自动化，4-二次处理
     */
    @Excel(name = "真实数据来源，1-人工，2-wind,3-自动化，4-二次处理")
    private Integer realSrc;
    /**
     * 状态，1-可用，0-禁用
     */
    @Excel(name = "状态，1-可用，0-禁用")
    private Integer status;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "${column.comment}")
    private Date created;
    @Excel(name = "${column.comment}")
    private Date updated;
    @Excel(name = "${column.comment}")
    private String oldSrc;


}
