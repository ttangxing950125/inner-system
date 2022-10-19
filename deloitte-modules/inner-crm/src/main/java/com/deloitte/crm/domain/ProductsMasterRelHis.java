package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PenTang
 * @date 2022/10/18 16:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductsMasterRelHis implements Serializable {
    private  static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 主体历史名称
     */
    private String entityNameHis;


    /**
     * 主体code
     */
    private  String entityCode;

    /**
     * 产品客户id
     */
    private Integer proCustId ;


    /**
     * 产品客户名称
     */
    private  String proCustName;

    /**
     * 敞口名称
     */
    private  String masterName;

    /**
     * 敞口id
     */
    private Integer  masterDictId;

    /**
     * 变更后的敞口名称
     */
    private String masterNameNew;

    /**
     * 掌握dict id新
     */
    private Integer masterDictIdNew;

    /**
     * 添加时间
     */
    private String addHis ;


    /**
     * 创建时间
     */
    private Date created;
    /**
     * 更新时间
     */
    private Date updated;



}
