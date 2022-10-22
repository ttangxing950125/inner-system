package com.deloitte.crm.vo;

import com.deloitte.crm.annotation.Attrs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 冉浩岑
 * @date 2022/10/22 10:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SupplyTaskVo {
    /** 唯一识别字段 */
    private String entityCode;

    /** 来源 */
    private String source;
    /** 企业名称 */
    private String entityName;
    /** 统一社会信用代码代码 */
    private String creditCode;
    /** 是否为金融机构 */
    private Integer list;
    /** 任务状态 */
    private Integer state;

    /** 角色3     金融机构细分行业  */
    @Attrs(attrId = 656,attrName = "金融机构细分行业")
    private String finIndustryGroup;

    /** 角色4     城投机构对应地方政府名称  */
    private String govName;

    /** 角色5    是否为城投机构   */
    private String isUi;

}
