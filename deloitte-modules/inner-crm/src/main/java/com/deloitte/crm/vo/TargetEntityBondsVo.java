package com.deloitte.crm.vo;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.EntityAttrDetailDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 正杰
 * @description: TargetEntityBondsVo
 * @date 2022/9/25
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "TargetEntityBondsVo",description = "模糊匹配查询 主体||债券 返回结果")
public class TargetEntityBondsVo {

    /**
     * 主体信息
     */
    @ApiModelProperty(value="主体基本信息")
    private EntityInfo entityInfo;

    /**
     * 主体债券交易代码
     */
    @ApiModelProperty(value="主体债券交易代码")
    private String bondsCode;

    /**
     * EntityAttrDetailDto
     * 债卷具体信息
     */
    @ApiModelProperty(value="债卷具体信息")
    private List<EntityAttrDetailDto> info;

    /**
     * 债券单个信息
     */
    @ApiModelProperty(value="债卷的单个信息")
    private EntityAttrDetailDto singleInfo;

}
