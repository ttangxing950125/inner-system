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
 * @date 2022/10/17 10:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductsCustomer implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private  Integer id;

    /**
     * 产品客户编码
     */
    private  String prcCode;
    /**
     * 产品id
     */
    private Integer productsId ;
    /**
     * 客户名称
     */
    private String  customer ;
    /**
     * 启用状态 0-禁用 1-启用
     */
    private Integer status;

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
