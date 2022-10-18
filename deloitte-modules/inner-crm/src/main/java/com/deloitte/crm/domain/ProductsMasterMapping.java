package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PenTang
 * @date 2022/10/17 10:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductsMasterMapping implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private  Integer id;

    /**
     *产品客户id
     */
    private Integer proCustId;
    /**
     *产品敞口code
     */
    private String crmMasterCode;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;
}
