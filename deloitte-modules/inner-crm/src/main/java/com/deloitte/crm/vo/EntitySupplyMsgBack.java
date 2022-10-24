package com.deloitte.crm.vo;

import com.deloitte.crm.domain.EntityFinancial;
import com.deloitte.crm.domain.EntityGovRel;
import com.deloitte.crm.domain.EntityInfo;
import lombok.Data;

/**
 * 角色3，4，5补充录入查询和回显 实体类
 *
 * @author 冉浩岑
 * @date 2022/10/24 11:35
 */
@Data
public class EntitySupplyMsgBack {
    /** 主体基本信息 */
    private EntityInfo entityInfo;
    /** 金融机机构补充信息 */
    private EntityFinancial entityFinancial;
    /** 城投机构补充信息 */
    private EntityGovRel entityGovRel;
}
