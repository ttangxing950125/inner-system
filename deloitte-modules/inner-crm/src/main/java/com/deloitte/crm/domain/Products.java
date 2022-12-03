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
 * @date 2022/10/12 18:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Products implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 产品规则描述
     */
    private String proRemark;

    private String proCode;

    /**
     * 文件名称
     */
    private String filePath;

    /**
     * 产品名称
     */
    private String proName;

    /**
     * 敞口版本
     */
    private String masterVer;

    /**
     * 产品版本
     */
    private  String proVersion;

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
