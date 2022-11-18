package com.deloitte.crm.domain.dto;

import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.MoreIndex;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/09/25 16:42
 */
@Data
@Accessors(chain = true)
public class EntityInfoResult {

    private EntityInfo entityInfo;

    private List<MoreIndex> more;
    private List<String> header;
    private List<String> values;

}
