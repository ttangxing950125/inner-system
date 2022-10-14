package com.deloitte.crm.domain.dto;

import com.deloitte.crm.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/10/13 14:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EntityInfoDetails {
    /**
     * 主体基本信息
     */
    private EntityInfo entityInfo;
    /**
     * 主体金融机构信息
     */
    private EntityFinancial entityFinancial;
    /**
     * 主体A股上市详情
     */
    private StockCnInfo stockCnInfo;
    /**
     * 主体港股上市详情
     */
    private StockThkInfo stockThkInfo;
    /**
     * 主体其他一般工商信息
     */
    private EntityBaseBusiInfo entityBaseBusiInfo;
    /**
     * A股上市状态
     */
    private String listTypeA;
    /**
     * 港股上市状态
     */
    private String listTypeG;
    /**
     * 上市汇总
     */
    private String listType;
    /**主体产品覆盖情况*/
    // TODO
    /**
     * /**主体发债情况详情
     */
    private BondInfoDetail bondInfoDetail;


    /**
     * 主体敞口划分
     */
    private List<EntityAttrValue> attrList;
}
