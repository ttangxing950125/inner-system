package com.deloitte.crm.vo;

import com.deloitte.crm.dto.AttrValueMapDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 正杰
 * @description: ModleMasterInfoVo
 * @date 2022/9/29
 */
@Data
@Accessors(chain = true)
public class ModelMasterInfoVo {

    List<AttrValueMapDto> list;

}
