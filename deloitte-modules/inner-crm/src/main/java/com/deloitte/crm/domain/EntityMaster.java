package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.deloitte.common.core.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 【请填写功能名称】对象 entity_master
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Data
@Accessors(chain = true)
public class EntityMaster implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * model_master的master_code
     */
    @Excel(name = "model_master的master_code")
    private String masterCode;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "entity_info的entity_code")
    private String entityCode;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date update;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "wind行业划分")
    private String windMaster;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "申万行业划分")
    private String shenWanMaster;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "1、Y：是YY口径下城投机构 2、N：不是YY口径下城投机构")
    private String yyUrban;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "1、Y：是YY口径下城投机构 2、N：不是YY口径下城投机构")
    private String zhongxinUrban;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "1、Y：是YY口径下城投机构 2、N：不是YY口径下城投机构")
    private String ibUrban;


    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
