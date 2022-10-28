package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (CrmTypeInfo)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-28 13:11:01
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CrmTypeInfo implements Serializable {
    private static final long serialVersionUID = -95704573809472487L;
    /**
     * 主键自动增加
     */
    @Excel(name = "主键自动增加")
    private Integer id;
    /**
     * 编码
     */
    @Excel(name = "编码")
    private String code;
    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;
    /**
     * 父code
     */
    @Excel(name = "父code")
    private String parentCode;
    /**
     * 类型 1.wind行业 2.万申行业
     */
    @Excel(name = "类型 1.wind行业 2.万申行业")
    private String type;
    /**
     * 是第几级类型
     */
    @Excel(name = "是第几级类型")
    private Integer level;
    /**
     * 删除标识 0 未删除 1 已删除
     */
    @Excel(name = "删除标识 0 未删除 1 已删除")
    private Boolean isDeleted;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date created;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间")
    private Date updated;

    @TableField(exist = false)
    private List<CrmTypeInfo> children;

}
