package com.deloitte.crm.domain.dto;

import com.deloitte.crm.domain.EntityInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 冉浩岑
 * @date 2022/09/25 16:42
 */
@Data
public class EntityInfoResult {
    private EntityInfo entityInfo;
    private List<Map<String,Object>> more;
}
