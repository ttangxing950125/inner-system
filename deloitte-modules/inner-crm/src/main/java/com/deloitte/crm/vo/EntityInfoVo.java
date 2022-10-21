package com.deloitte.crm.vo;

import com.deloitte.crm.domain.EntityInfo;
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
    private EntityInfo entityInfo;

    /**
     * 该主体是否为 新增
     */
    private Boolean bo = true;

    /**
     * 查询到的 曾用名相同的主体
     */
    private EntityInfo entityInfoHis;

    /**
     * 详细说明信息
     */
    private String msg;

}
