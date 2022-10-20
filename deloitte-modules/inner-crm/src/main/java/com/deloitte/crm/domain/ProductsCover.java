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
 * @date 2022/10/12 15:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductsCover implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private  Integer id;


    /**
     * IB+自000001开始排序，每个企业唯一
     */
    @Excel(name = "IB+自000001开始排序，每个企业唯一")
    private String entityCode;

    /**
     * 产品id
     */
    @Excel(name = "产品id")
     private  Integer  proId;
    /**
     * 产品id
     */
    @Excel(name = "产品id")
    private  String  coverDes;
    /**
     *是否覆盖
     */
    @Excel(name = "是否覆盖 0-否 1-是")
    private String isCover;

    private String isGov;

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
