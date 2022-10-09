package com.deloitte.crm.vo;

import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.AttrValueMapDto;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author 正杰
 * @description: BondInfoEditVo
 * @date 2022/9/28
 */
@Data
@Accessors(chain = true)
public class BondEntityInfoVo {

    private EntityInfo entityInfo;

    private BondInfo bondInfo;

    private Map<String,AttrValueMapDto> attrs;

}
