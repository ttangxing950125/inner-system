package com.deloitte.crm.dto;

import com.deloitte.crm.domain.EntityAttrValue;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/09/29 16:01
 */
@Data
@Accessors(chain = true)
public class EntityAttrValueDto {
    private String entityCode;
    private List<EntityAttrValue>attrValueList;
}
