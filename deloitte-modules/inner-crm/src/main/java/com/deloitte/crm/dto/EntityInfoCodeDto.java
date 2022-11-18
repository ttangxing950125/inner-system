package com.deloitte.crm.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.crm.annotation.Attrs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author PenTang
 * @date 2022/10/18 10:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EntityInfoCodeDto {

    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 企业名
     */
    @Excel(name = "企业名")
    private String entityName;

    /**
     * IB+自000001开始排序，每个企业唯一
     */
    @Excel(name = "IB+自000001开始排序，每个企业唯一")
    private String entityCode;

    /**
     * 报告类型
     */
    @Excel(name = "报告类型")
    private String reportType;


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
     * 统一社会信用代码
     */
    @Excel(name = "统一社会信用代码")
    private String creditCode;

    /**
     * 是否上市 0-未上市 1-已上市
     */
    @Excel(name = "是否上市 0-未上市 1-已上市")
    private Integer list;

    /**
     * 是否发债 0-未发债 1-已发债
     */
    @Excel(name = "是否发债 0-未发债 1-已发债")
    private Integer issueBonds;


    /**
     * 是否金融机构 0-否 1-是
     */
    @Excel(name = "是金融机构 0-否 1-是")
    private Integer finance;

    /**
     * 金融机构细分行业
     */
    @Excel(name = "金融细分领域")
    @Attrs(attrId = 656,attrName = "金融机构细分行业")
    private String mince;

    /**
     * 是否城机构
     */
    private String isGov ;

    /**
     * 所属政府名称
     */
    private String govName;

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


}
