package com.deloitte.crm.domain;

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
 * (EntityInfoLogs)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-10 13:55:12
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class EntityInfoLogs implements Serializable {
    private static final long serialVersionUID = -95463303364821198L;
    /**
     * 主键
     */
    @Excel(name = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 股票证券代码 | 债券交易代码
     */
    @Excel(name = "股票证券代码 | 债券交易代码")
    private String code;
    /**
     * 证券简称 | 债券简称
     */
    @Excel(name = "证券简称 | 债券简称")
    private String name;

    /**
     * 自编股票或债券代码
     */
    private String deCode;
    /**
     * 1-a股   2-港股  3-发债
     * 上市： a股 | 港股
     * 发债： 债券
     */
    @Excel(name = "1-a股   2-港股  3-债券")
    private Integer operType;
    /**
     * 德勤主体代码 对应entity_info表代码
     */
    @Excel(name = "德勤主体代码 对应entity_info表代码")
    private String entityCode;
    /**
     * 入库时的主体名
     */
    @Excel(name = "入库时的主体名")
    private String entityName;
    /**
     * 操作来源
     */
    @Excel(name = "操作来源")
    private Integer source;
    /**
     * 操作人
     */
    @Excel(name = "操作人")
    private String operName;
    /**
     * 备注信息
     */
    @Excel(name = "备注信息")
    private String remarks;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date createTime;
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


}
