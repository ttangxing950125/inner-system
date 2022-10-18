package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;
import lombok.experimental.Accessors;

/**
 * 撤销ST(摘帽)(UndoStInfo)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-14 17:50:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
@TableName("stock_cn_undo_st_info")
public class StockCnUndoStInfo implements Serializable {
    private static final long serialVersionUID = -29857614044342922L;
    /**
     * 主键 自动曾添加
     */
//    @Excel(name = "主键 自动曾添加")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 导入时间
     */
//    @Excel(name = "导入时间")
    private Date importTime;
    /**
     * 1-新增 2-修改
     */
//    @Excel(name = "1-新增 2-修改")
    private Integer changeType;
    /**
     * crm_wind_task的id
     */
//    @Excel(name = "crm_wind_task的id")
    private Integer taskId;
    /**
     * 序号
     */
    @Excel(name = "序号")
    private Integer number;
    /**
     * 代码
     */
    @Excel(name = "代码")
    private String code;
    /**
     * 公司名称
     */
    @Excel(name = "名称")
    private String name;
    /**
     * 撤销日期
     */
    @Excel(name = "撤销日期")
    private Date undoDate;
    /**
     * 撤销前简称
     */
    @Excel(name = "撤销前简称")
    private String undoBeforeName;
    /**
     * 撤销后简称
     */
    @Excel(name = "撤销后简称")
    private String undoBackName;
    /**
     * 证监会行业
     */
    @Excel(name = "证监会行业")
    private String csrcIndustry;
    /**
     * Wind行业
     */
    @Excel(name = "Wind行业")
    private String windIndustry;
    /**
     * 证券类型
     */
    @Excel(name = "证券类型")
    private String bondType;
    /**
     * 创建时间
     */
//    @Excel(name = "创建时间")
    private Date created;
    /**
     * 更新时间
     */
//    @Excel(name = "更新时间")
    private Date updated;


}
