package com.deloitte.crm.domain;

import com.deloitte.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

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
    private Integer id;
    /**
     * 主体编码
     */
    @Excel(name = "主体编码")
    private String entityCode;
    /**
     * 金融细分领域
     */
    @Excel(name = "金融细分领域")
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
    private String regulators;

    /**
     * 对口监管机构
     */
    @Excel(name = "所属地区")
    private String belPlace;

    /**
     * 对口监管机构
     */
    @Excel(name = "所属辖区")
    private String belJurisdiction;

    /**
     * 对口监管机构
     */
    @Excel(name = "备注")
    private String remarks;

}
