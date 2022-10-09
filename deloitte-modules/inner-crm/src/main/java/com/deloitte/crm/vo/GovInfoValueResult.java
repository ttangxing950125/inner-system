package com.deloitte.crm.vo;

import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.GovInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/10/08 15:50
 */
@Data
@Accessors(chain = true)
public class GovInfoValueResult {
    private GovInfo govInfo;
    private List<EntityAttrValue> valueList;
}
