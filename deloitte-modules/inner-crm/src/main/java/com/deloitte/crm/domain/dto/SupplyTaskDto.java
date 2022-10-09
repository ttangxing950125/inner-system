package com.deloitte.crm.domain.dto;

import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.GovInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/09/29 21:56
 */
@Data
@Accessors(chain = true)
public class SupplyTaskDto {
    private GovInfo govInfo;
    private EntityInfo entityInfo;
    private List<EntityAttrValue> values;
    private CrmSupplyTask crmSupplyTask;
    private String isUi;
}
