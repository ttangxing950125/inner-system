package com.deloitte.crm.domain.dto;

import com.deloitte.crm.domain.GovInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 冉浩岑
 * @date 2022/09/25 16:42
 */
@Data
public class GovInfoResult {
    private GovInfo govInfo;
    private List<Map<String,Object>> more;
}
