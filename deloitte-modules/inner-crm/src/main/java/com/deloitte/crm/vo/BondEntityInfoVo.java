package com.deloitte.crm.vo;

import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.AttrValueMapDto;
import com.deloitte.crm.dto.BondInfoDto;
import com.deloitte.crm.dto.EntityInfoDto;
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
    private BondInfo bondInfo;
    private EntityInfo entityInfo;
    /**
     * 是否允许修改
     */
    private Boolean enableEdite;

    /**
     * 具体属性值
     */
    private Map<String,AttrValueMapDto> attrs;

}
