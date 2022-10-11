package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author PenTang
 * @date 2022/10/11 16:58
 */
@Data
public class EntityFinancial  implements Serializable {

    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** IB+自000001开始排序，每个企业唯一 */
    @Excel(name = "IB+自000001开始排序，每个企业唯一")
    private String entityCode;

    /** 金融细分领域 */
    @Excel(name = "金融细分领域")
    private String mince;

    /** gov_info中的dq_code */
    @Excel(name = "gov_info中的dq_code金融细分领域")
    private String region;

    /** 对口监管机构 */
    @Excel(name = "对口监管机构")
    private String regulators;


}
