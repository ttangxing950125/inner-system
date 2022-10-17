package com.deloitte.crm.vo;

import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/10/08 15:50
 */
@Data
@Accessors(chain = true)
public class EntityInfoValueResult {
    private EntityInfo entityInfo;
    private List<EntityAttrValue> valueList;
    //上市情况
    private String listDetail;
    //发债情况
    private String bondDetail;
    //证券代码
    private String stockCode;
}
