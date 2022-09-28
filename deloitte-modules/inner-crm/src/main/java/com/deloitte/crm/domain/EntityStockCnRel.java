package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (EntityStockCnRel)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:36
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EntityStockCnRel implements Serializable {
    private static final long serialVersionUID = -48854618822931420L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 股票发行人code
     */
    @Excel(name = "股票发行人code")
    private String entityCode;
    /**
     * 股票code
     */
    @Excel(name = "股票code")
    private String stockDqCode;
    /**
     * 关系状态
     */
    @Excel(name = "关系状态")
    private Boolean status;
    /**
     * 新发行人名称
     */
    @Excel(name = "新发行人名称")
    private String newEntityName;
    /**
     * 新发行人code
     */
    @Excel(name = "新发行人code")
    private String newEntityCode;

    private Date created;

    private Date updated;


}
