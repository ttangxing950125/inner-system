package com.deloitte.crm.vo;

import com.deloitte.crm.dto.AttrValueMapDto;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 正杰
 * @description: BondInfoEditVo
 * @date 2022/9/28
 */
@Data
@Accessors(chain = true)
public class BondEntityInfoVo {

    List<AttrValueMapDto> list;

}
