package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.crm.annotation.Attrs;
import com.deloitte.crm.constants.Common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * (EntityFinancial)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-11 17:11:57
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class EntityFinancial implements Serializable {

    private static final long serialVersionUID = 218444696023577627L;
    @Excel(name = "${column.comment}")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 主体编码
     */
    @Excel(name = "主体编码")
    private String entityCode;

    /**
     * 金融机构细分行业
     */
    @Excel(name = "金融细分领域")
    @Attrs(attrId = 656,attrName = "金融机构细分行业")
    private String mince;

    /**
     * gov_info中的dq_code
     */
    @Excel(name = "gov_info中的dq_code")
    private String region;

    /**
     * 对口监管机构
     */
    @Excel(name = "对口监管机构")
    @Attrs(attrId = 657,attrName = "对口监管机构")
    private String regulators;

    /**
     * 所属地区
     */
    @Excel(name = "所属地区")
    @Attrs(attrId = 983,attrName = "所属地区")
    private String belPlace;

    /**
     * 所属辖区
     */
    @Excel(name = "所属辖区")
    @Attrs(attrId = 658,attrName = "所属辖区")
    private String belJurisdiction;

    /**
     * 对口监管机构
     */
    @Excel(name = "备注")
    private String remarks;

    /** $column.columnComment */
    @Attrs(attrId = 0,attrName = Common.CREATED)
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @Attrs(attrId = 0,attrName = Common.UPDATED)
    private Date updated;

}
