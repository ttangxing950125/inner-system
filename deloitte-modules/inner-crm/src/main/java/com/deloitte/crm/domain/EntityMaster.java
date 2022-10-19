package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.crm.annotation.Attrs;
import com.deloitte.crm.constants.Common;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
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
     * 主键
     */
    @TableId(type = IdType.AUTO)
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
    @TableField(value = "`update`")
    private Date update;

    /**
     * model_master 的 master_code
     */
    @Excel(name = "wind口径下行业划分")
    @Attrs(attrId = 652,attrName = "wind口径下行业划分")
    private String windMaster;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "申万行业划分")
    @Attrs(attrId = 650,attrName = "申万(2021)行业划分明细")
    private String shenWanMaster;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "是否为城投机构（YY） 1、Y：是YY口径下城投机构 2、N：不是YY口径下城投机构")
    @Attrs(attrId = 644,attrName = "是否为城投机构（YY）")
    private String yyUrban;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "是否为城投机构（中诚信） 1、Y：是中诚信口径下城投机构 2、N：不是中诚信口径下城投机构")
    @Attrs(attrId = 645,attrName = "是否为城投机构（中诚信）")
    private String zhongxinUrban;

    /**
     * entity_info的entity_code
     */
    @Excel(name = "是否为城投机构  1、Y：是IB口径下城投机构 2、N：不是IB口径下城投机构")
    @Attrs(attrId = 642,attrName = "是否为城投机构（IB）")
    private String ibUrban;


    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
