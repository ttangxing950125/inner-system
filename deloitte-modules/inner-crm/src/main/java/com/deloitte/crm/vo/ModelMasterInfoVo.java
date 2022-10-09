package com.deloitte.crm.vo;

import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.AttrValueMapDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author 正杰
 * @description: ModleMasterInfoVo
 * @date 2022/9/29
 */
@Data
@Accessors(chain = true)
public class ModelMasterInfoVo {

    private CrmMasTask crmMasTask;

    private EntityInfo entityInfo;

    private Map<String,AttrValueMapDto> attrs;

}
