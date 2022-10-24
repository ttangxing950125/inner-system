package com.deloitte.crm.vo;

import com.deloitte.crm.domain.EntityInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 该类为 EntityInfoService 接口的 Vo 传输对象
 * @author 正杰
 * @description: EntityInfoVo
 * @date 2022/9/22
 */
@Data
@Accessors(chain = true)
public class EntityInfoVo {

    /**
     * 查询到的 主体信息
     */
    @ApiModelProperty(name ="entityInfo",value = "查询到的主体")
    private EntityInfo entityInfo;

    /**
     * 该主体是否为 新增
     */
    private Boolean bo = true;

    /**
     * 校验状态
     * 0=不重复
     * 1=有主体，名称不同
     * 2=有主体，代码不同
     * 3=有主体，全部一致
     */
    @ApiModelProperty(name= "status",value = "0=不重复，可新增，1=有主体，名称不同，2=有主体，代码不同，3=有主体，全部一致")
    private Integer status;

    /**
     * 详细说明信息
     */
    @ApiModelProperty(name="msg",value="详细信息描述")
    private String msg;

}
